package com.back.gaon.domain.member.service;

import com.back.gaon.domain.member.dto.ParentCreateRequest;
import com.back.gaon.domain.member.dto.StudentCreateRequest;
import com.back.gaon.domain.member.dto.MemberResponse;
import com.back.gaon.domain.member.dto.StudentCreateResponse;
import com.back.gaon.domain.member.entity.Member;
import com.back.gaon.domain.member.entity.StudentDetail;
import com.back.gaon.domain.member.enums.MemberStatus;
import com.back.gaon.domain.member.enums.Role;
import com.back.gaon.domain.member.repository.MemberRepository;
import com.back.gaon.domain.member.repository.StudentDetailRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final MemberRepository memberRepository;
    private final StudentDetailRepository studentDetailRepository;
    // private final SmsService smsService;  // TODO: SMS 발송 서비스 (나중에 추가)

    private static final int MAX_CHILDREN_PER_PARENT = 3;  // 학부모당 최대 자녀 수

    /**
     * 🔥 학생 등록 (학부모 자동 생성 + 연결)
     *
     * 처리 순서:
     * 1. 학생 전화번호 중복 체크
     * 2. 학부모 찾기 또는 생성
     * 3. 학부모 자녀 수 체크 (최대 3명)
     * 4. 좌석 번호 중복 체크
     * 5. Student Member 생성
     * 6. StudentDetail 생성 (학부모 연결)
     * 7. 학부모에게 SMS 발송 (비밀번호 설정 링크)
     */
    public StudentCreateResponse createStudent(StudentCreateRequest request) {
        // 1) 학생 전화번호 중복 체크
        if (memberRepository.existsByPhone(request.getPhone())) {
            throw new IllegalArgumentException("이미 등록된 학생 전화번호입니다: " + request.getPhone());
        }

        // 2) 학부모 찾기 또는 생성
        Member parent = memberRepository.findByPhone(request.getParentPhone())
                .orElseGet(() -> createParentMember(request));

        // 3) 학부모 자녀 수 체크 (최대 3명)
        long currentChildCount = studentDetailRepository.countByParentId(parent.getId());
        if (currentChildCount >= MAX_CHILDREN_PER_PARENT) {
            throw new IllegalArgumentException(
                    String.format("학부모는 최대 %d명의 자녀만 등록할 수 있습니다.", MAX_CHILDREN_PER_PARENT)
            );
        }

        // 4) 좌석 번호 중복 체크 (좌석이 지정된 경우)
        if (request.getSeatNumber() != null && !request.getSeatNumber().isBlank()) {
            if (studentDetailRepository.existsBySeatNumber(request.getSeatNumber())) {
                throw new IllegalArgumentException("이미 사용 중인 좌석 번호입니다: " + request.getSeatNumber());
            }
        }

        // 5) Student Member 생성
        Member student = Member.builder()
                .name(request.getName())
                .role(Role.STUDENT)
                .gender(request.getGender())
                .phone(request.getPhone())
                .joinDate(request.getRegistrationDate())
                .status(MemberStatus.ACTIVE)
                .build();

        Member savedStudent = memberRepository.save(student);

        // 6) StudentDetail 생성 (학부모 연결)
        StudentDetail studentDetail = StudentDetail.builder()
                .member(savedStudent)
                .parent(parent)
                .school(request.getSchool())
                .grade(request.getGrade())
                .seatNumber(request.getSeatNumber())
                .registrationDate(request.getRegistrationDate())
                .emergencyContact(request.getEmergencyContact())
                .memo(request.getMemo())
                .build();

        studentDetailRepository.save(studentDetail);

        // 7) 학부모에게 SMS 발송 (비밀번호 미설정 시)
        if (Boolean.FALSE.equals(parent.getPasswordTemp()) && parent.getPassword() == null) {
            sendParentSetupSms(parent);
        }

        return StudentCreateResponse.builder()
                .studentId(savedStudent.getId())
                .name(savedStudent.getName())
                .phone(savedStudent.getPhone())
                .gender(savedStudent.getGender())
                .school(studentDetail.getSchool())
                .grade(studentDetail.getGrade())
                .seatNumber(studentDetail.getSeatNumber())
                .registrationDate(studentDetail.getRegistrationDate())
                .parentId(parent.getId())
                .parentPhone(parent.getPhone())
                .parentCreated(parent.getPassword() == null)
                .build();
    }

    /**
     * 학부모 Member 생성 (학생 등록 시 자동 생성)
     */
    private Member createParentMember(StudentCreateRequest request) {
        // 학부모 전화번호 중복 체크
        if (memberRepository.existsByPhone(request.getParentPhone())) {
            throw new IllegalArgumentException("이미 등록된 학부모 전화번호입니다: " + request.getParentPhone());
        }

        // setupToken 생성 (비밀번호 설정용)
        String setupToken = UUID.randomUUID().toString();
        LocalDateTime tokenExpiry = LocalDateTime.now().plusDays(7);  // 7일 유효

        Member parent = Member.builder()
                .name(request.getName())
                .role(Role.PARENT)
                .phone(request.getParentPhone())
                .status(MemberStatus.ACTIVE)
                .passwordTemp(false)
                .setupToken(setupToken)
                .setupTokenExpiredAt(tokenExpiry)
                .build();

        return memberRepository.save(parent);
    }

    /**
     * 학부모 단독 등록 (드물게 사용)
     */
    public MemberResponse createParent(ParentCreateRequest request) {
        if (memberRepository.existsByPhone(request.getPhone())) {
            throw new IllegalArgumentException("이미 등록된 전화번호입니다: " + request.getPhone());
        }

        String setupToken = UUID.randomUUID().toString();
        LocalDateTime tokenExpiry = LocalDateTime.now().plusDays(7);

        Member parent = Member.builder()
                .name(request.getName())
                .role(Role.PARENT)
                .phone(request.getPhone())
                .status(MemberStatus.ACTIVE)
                .setupToken(setupToken)
                .setupTokenExpiredAt(tokenExpiry)
                .build();

        Member saved = memberRepository.save(parent);

        // SMS 발송
        sendParentSetupSms(saved);

        return MemberResponse.builder()
                .id(saved.getId())
                .name(saved.getName())
                .phone(saved.getPhone())
                .role(saved.getRole())
                .gender(saved.getGender())
                .joinDate(saved.getJoinDate())
                .status(saved.getStatus())
                .build();
    }

    /**
     * 학부모-자녀 수동 연결
     */
    public void linkParentChild(Long parentId, Long studentId) {
        // 학부모 검증
        Member parent = memberRepository.findById(parentId)
                .orElseThrow(() -> new EntityNotFoundException("학부모를 찾을 수 없습니다: " + parentId));

        if (parent.getRole() != Role.PARENT) {
            throw new IllegalArgumentException("학부모 권한이 아닙니다.");
        }

        // 학생 검증
        Member student = memberRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("학생을 찾을 수 없습니다: " + studentId));

        if (student.getRole() != Role.STUDENT) {
            throw new IllegalArgumentException("학생 권한이 아닙니다.");
        }

        // 학부모 자녀 수 체크
        long currentChildCount = studentDetailRepository.countByParentId(parentId);
        if (currentChildCount >= MAX_CHILDREN_PER_PARENT) {
            throw new IllegalArgumentException(
                    String.format("학부모는 최대 %d명의 자녀만 등록할 수 있습니다.", MAX_CHILDREN_PER_PARENT)
            );
        }

        // StudentDetail 조회 및 학부모 연결
        StudentDetail studentDetail = studentDetailRepository.findByMemberId(studentId)
                .orElseThrow(() -> new EntityNotFoundException("학생 상세 정보를 찾을 수 없습니다: " + studentId));

        if (studentDetail.getParent() != null) {
            throw new IllegalArgumentException("이미 학부모가 연결된 학생입니다.");
        }

        studentDetail.setParent(parent);
        studentDetailRepository.save(studentDetail);
    }

    /**
     * 학부모에게 비밀번호 설정 SMS 발송
     * TODO: 실제 SMS 서비스 연동 필요
     */
    private void sendParentSetupSms(Member parent) {
        String setupUrl = "https://gaon.com/parent/setup?token=" + parent.getSetupToken();
        String message = String.format(
                "[가온독서실] %s님, 자녀가 등록되었습니다. " +
                        "아래 링크에서 비밀번호를 설정해주세요.\n%s\n" +
                        "(7일 이내 설정 필요)",
                parent.getName(),
                setupUrl
        );

        // TODO: smsService.send(parent.getPhone(), message);
        System.out.println("📱 SMS 발송: " + parent.getPhone());
        System.out.println(message);
    }
}