package com.back.gaon.global.initData;

import com.back.gaon.domain.member.entity.Member;
import com.back.gaon.domain.member.enums.Gender;
import com.back.gaon.domain.member.enums.Grade;
import com.back.gaon.domain.member.enums.MemberStatus;
import com.back.gaon.domain.member.repository.MemberRepository;
import com.back.gaon.domain.schedule.item.entity.ScheduleTemplateItem;
import com.back.gaon.domain.schedule.item.enums.ScheduleBlockType;
import com.back.gaon.domain.schedule.item.repository.ScheduleTemplateItemRepository;
import com.back.gaon.domain.schedule.schedule.repository.ScheduleRepository;
import com.back.gaon.domain.schedule.template.entity.ScheduleTemplate;
import com.back.gaon.domain.schedule.template.enums.TemplateStatus;
import com.back.gaon.domain.schedule.template.repository.ScheduleTemplateRepository;
import com.back.gaon.domain.schedule.version.entity.ScheduleTemplateVersion;
import com.back.gaon.domain.schedule.version.repository.ScheduleTemplateVersionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class InitDataConfig {

    private final MemberRepository memberRepository;
    private final ScheduleTemplateRepository scheduleTemplateRepository;
    private final ScheduleTemplateVersionRepository scheduleTemplateVersionRepository;
    private final ScheduleTemplateItemRepository scheduleTemplateItemRepository;
    private final ScheduleRepository scheduleRepository;

    @Bean
    CommandLineRunner initData() {
        return args -> {
            initMembers();              // 회원 20명 생성
            initScheduleTemplates();
        };
    }

    /** 1) 엑셀(개인카드) 기반 멤버 더미 데이터 */
    private void initMembers() {
        if (memberRepository.count() > 0) return;

        Member m1 = Member.builder()
                .name("강지호")
                .gender(Gender.Male)
                .school("야탑고")
                .grade(Grade.High1)
                .status(MemberStatus.ACTIVE)
                .joinDate(LocalDate.of(2025, 11, 3))
                .build();

        Member m2 = Member.builder()
                .name("권은지")
                .gender(Gender.Female)
                .school("성남외고")
                .grade(Grade.High1)
                .status(MemberStatus.ACTIVE)
                .joinDate(LocalDate.of(2025, 11, 15))
                .build();

        Member m3 = Member.builder()
                .name("김린하")
                .gender(Gender.Female)
                .school("중앙고")
                .grade(Grade.High1)
                .status(MemberStatus.ACTIVE)
                .joinDate(LocalDate.of(2025, 9, 4))
                .build();

        Member m4 = Member.builder()
                .name("김민서")
                // 성별 미기입
                .school("수내고")
                .grade(Grade.High1)
                .status(MemberStatus.ACTIVE)
                .joinDate(LocalDate.of(2025, 10, 20))
                .build();

        Member m5 = Member.builder()
                .name("김예은")
                .gender(Gender.Female)
                .school("수내고")
                .grade(Grade.High2)
                .parentPhone("010-4191-0406")
                .phone("010-6773-0310")
                .status(MemberStatus.ACTIVE)
                .joinDate(LocalDate.of(2025, 3, 15))
                .build();

        Member m6 = Member.builder()
                .name("김윤아")
                .gender(Gender.Female)
                .school("태원고")
                .grade(Grade.High3)
                .status(MemberStatus.ACTIVE)
                .joinDate(LocalDate.of(2025, 11, 15))
                .build();

        Member m7 = Member.builder()
                .name("김지효")
                .gender(Gender.Female)
                .school("N수")
                .grade(Grade.High3) // 재수생
                .status(MemberStatus.ACTIVE)
                .joinDate(LocalDate.of(2025, 9, 8))
                .build();

        Member m8 = Member.builder()
                .name("문지호")
                .gender(Gender.Male)
                .school("수내고")
                .grade(Grade.High1)
                .status(MemberStatus.ACTIVE)
                .joinDate(LocalDate.of(2025, 8, 2))
                .build();

        Member m9 = Member.builder()
                .name("박예진")
                .gender(Gender.Female)
                .school("영덕여고")
                .grade(Grade.High2)
                .status(MemberStatus.ACTIVE)
                .joinDate(LocalDate.of(2025, 8, 5))
                .build();

        Member m10 = Member.builder()
                .name("심현아")
                .gender(Gender.Female)
                .school("N수")
                .grade(Grade.High3) // 재수생
                .phone("010-7339-8169")
                .status(MemberStatus.ACTIVE)
                .joinDate(LocalDate.of(2025, 4, 21))
                .build();

        Member m11 = Member.builder()
                .name("오소영")
                .gender(Gender.Female)
                .school("중앙고")
                .grade(Grade.High1)
                .status(MemberStatus.ACTIVE)
                .joinDate(LocalDate.of(2025, 7, 14))
                .build();

        Member m12 = Member.builder()
                .name("윤지상")
                .gender(Gender.Male)
                .school("수내고")
                .grade(Grade.High3)
                .status(MemberStatus.ACTIVE)
                .joinDate(LocalDate.of(2025, 9, 21))
                .build();

        Member m13 = Member.builder()
                .name("이서빈")
                .gender(Gender.Female)
                .school("한솔고")
                .grade(Grade.High1)
                .parentPhone("010-5548-3663")
                .phone("010-5623-5546")
                .status(MemberStatus.ACTIVE)
                .joinDate(LocalDate.of(2025, 5, 14))
                .build();

        Member m14 = Member.builder()
                .name("이예린")
                .gender(Gender.Female)
                .school("대진고")
                .grade(Grade.High2)
                .parentPhone("010-6508-7714")
                .phone("010-8682-9141")
                .status(MemberStatus.ACTIVE)
                .joinDate(LocalDate.of(2025, 4, 5))
                .build();

        Member m15 = Member.builder()
                .name("이지윤")
                .gender(Gender.Female)
                .school("대진고")
                .grade(Grade.High2)
                .status(MemberStatus.ACTIVE)
                .joinDate(LocalDate.of(2025, 7, 15))
                .build();

        Member m16 = Member.builder()
                .name("정서윤")
                .gender(Gender.Female)
                .school("성남외고")
                .grade(Grade.High1)
                .status(MemberStatus.ACTIVE)
                .joinDate(LocalDate.of(2025, 7, 23))
                .build();

        Member m17 = Member.builder()
                .name("정연우")
                .gender(Gender.Male)
                .school("대진고")
                .grade(Grade.High1)
                .status(MemberStatus.ACTIVE)
                .joinDate(LocalDate.of(2025, 10, 25))
                .build();

        Member m18 = Member.builder()
                .name("조하은")
                .gender(Gender.Female)
                .school("수내고")
                .grade(Grade.High2)
                .parentPhone("010-3905-4512")
                .phone("010-8191-4512")
                .status(MemberStatus.ACTIVE)
                .joinDate(LocalDate.of(2025, 6, 10))
                .build();

        Member m19 = Member.builder()
                .name("주시은")
                // 성별 미기입
                .school("대진고")
                .grade(Grade.High1)
                .status(MemberStatus.ACTIVE)
                .joinDate(LocalDate.of(2025, 10, 17))
                .build();

        Member m20 = Member.builder()
                .name("최윤후")
                // 성별 미기입
                .school("수내고등학교")
                .grade(Grade.High1)
                .status(MemberStatus.ACTIVE)
                .joinDate(LocalDate.of(2025, 10, 14))
                .build();

        Member m21 = Member.builder()
                .name("최지안")
                .gender(Gender.Female)
                .school("수내고")
                .grade(Grade.High1)
                .status(MemberStatus.ACTIVE)
                .joinDate(LocalDate.of(2025, 7, 21))
                .build();

        memberRepository.saveAll(List.of(
                m1, m2, m3, m4, m5,
                m6, m7, m8, m9, m10,
                m11, m12, m13, m14, m15,
                m16, m17, m18, m19, m20, m21
        ));
    }

    // GAON InitDataConfig - 엑셀 기반 스케줄 템플릿/버전/아이템 생성
// InitDataConfig 안에 넣어서 사용하면 됩니다.

    private void initScheduleTemplates() {
        if (scheduleTemplateRepository.count() > 0) return;

        // 이름 -> Member 캐시
        Map<String, Member> memberMap = memberRepository.findAll().stream()
                .collect(Collectors.toMap(Member::getName, m -> m));

        // 1. 김예은
        // 1. 김예은
        Member member김예은 = memberMap.get("김예은");
        if (member김예은 == null) throw new IllegalStateException("[김예은] 회원을 찾을 수 없습니다.");
        ScheduleTemplate tpl1 = ScheduleTemplate.builder()
                .member(member김예은)
                .status(TemplateStatus.APPROVED)
                .name("김예은 주간 기본 템플릿")
                .description("엑셀 초기 데이터")
                .build();
        scheduleTemplateRepository.save(tpl1);

        ScheduleTemplateVersion ver1 = ScheduleTemplateVersion.builder()
                .template(tpl1)
                .versionNo(1)
                .status(TemplateStatus.APPROVED)
                .build();
        scheduleTemplateVersionRepository.save(ver1);

        // 1. 김예은 요일별 아이템
        createItem(ver1, DayOfWeek.MONDAY, 23, 0, 23, 59);
        createItem(ver1, DayOfWeek.TUESDAY, 23, 0, 23, 59);
        createItem(ver1, DayOfWeek.WEDNESDAY, 23, 0, 23, 59);
        createItem(ver1, DayOfWeek.THURSDAY, 23, 0, 23, 59);
        createItem(ver1, DayOfWeek.FRIDAY, 22, 30, 23, 59);
        createItem(ver1, DayOfWeek.SATURDAY, 21, 0, 23, 59);
        createItem(ver1, DayOfWeek.SUNDAY, 19, 0, 23, 59);


        // 2. 심현아
        Member member심현아 = memberMap.get("심현아");
        if (member심현아 == null) throw new IllegalStateException("[심현아] 회원을 찾을 수 없습니다.");
        ScheduleTemplate tpl2 = ScheduleTemplate.builder()
                .member(member심현아)
                .status(TemplateStatus.APPROVED)
                .name("심현아 주간 기본 템플릿")
                .description("엑셀 초기 데이터")
                .build();
        scheduleTemplateRepository.save(tpl2);

        ScheduleTemplateVersion ver2 = ScheduleTemplateVersion.builder()
                .template(tpl2)
                .versionNo(1)
                .status(TemplateStatus.APPROVED)
                .build();
        scheduleTemplateVersionRepository.save(ver2);

        // 2. 심현아 요일별 아이템
        createItem(ver2, DayOfWeek.MONDAY, 9, 0, 23, 0);
        createItem(ver2, DayOfWeek.TUESDAY, 9, 0, 23, 0);
        createItem(ver2, DayOfWeek.WEDNESDAY, 9, 0, 23, 0);
        createItem(ver2, DayOfWeek.THURSDAY, 9, 0, 23, 0);
        createItem(ver2, DayOfWeek.FRIDAY, 9, 0, 23, 0);
        createItem(ver2, DayOfWeek.SATURDAY, 9, 0, 23, 0);
        createItem(ver2, DayOfWeek.SUNDAY, 9, 0, 11, 30);


        // 3. 이서빈
        Member member이서빈 = memberMap.get("이서빈");
        if (member이서빈 == null) throw new IllegalStateException("[이서빈] 회원을 찾을 수 없습니다.");
        ScheduleTemplate tpl3 = ScheduleTemplate.builder()
                .member(member이서빈)
                .status(TemplateStatus.APPROVED)
                .name("이서빈 주간 기본 템플릿")
                .description("엑셀 초기 데이터")
                .build();
        scheduleTemplateRepository.save(tpl3);

        ScheduleTemplateVersion ver3 = ScheduleTemplateVersion.builder()
                .template(tpl3)
                .versionNo(1)
                .status(TemplateStatus.APPROVED)
                .build();
        scheduleTemplateVersionRepository.save(ver3);

        // 3. 이서빈 요일별 아이템
        createItem(ver3, DayOfWeek.MONDAY, 19, 0, 23, 0);
        createItem(ver3, DayOfWeek.TUESDAY, 19, 0, 23, 0);
        createItem(ver3, DayOfWeek.WEDNESDAY, 22, 30, 23, 59);
        createItem(ver3, DayOfWeek.THURSDAY, 22, 30, 23, 59);
        createItem(ver3, DayOfWeek.FRIDAY, 19, 0, 23, 0);
        createItem(ver3, DayOfWeek.SATURDAY, 22, 30, 23, 59);
        createItem(ver3, DayOfWeek.SUNDAY, 14, 30, 22, 0);


        // 4. 조하은
        Member member조하은 = memberMap.get("조하은");
        if (member조하은 == null) throw new IllegalStateException("[조하은] 회원을 찾을 수 없습니다.");
        ScheduleTemplate tpl4 = ScheduleTemplate.builder()
                .member(member조하은)
                .status(TemplateStatus.APPROVED)
                .name("조하은 주간 기본 템플릿")
                .description("엑셀 초기 데이터")
                .build();
        scheduleTemplateRepository.save(tpl4);

        ScheduleTemplateVersion ver4 = ScheduleTemplateVersion.builder()
                .template(tpl4)
                .versionNo(1)
                .status(TemplateStatus.APPROVED)
                .build();
        scheduleTemplateVersionRepository.save(ver4);

        // 4. 조하은 요일별 아이템
        createItem(ver4, DayOfWeek.MONDAY, 21, 30, 23, 0);
        createItem(ver4, DayOfWeek.TUESDAY, 20, 0, 23, 0);
        createItem(ver4, DayOfWeek.WEDNESDAY, 21, 30, 23, 0);
        createItem(ver4, DayOfWeek.THURSDAY, 18, 0, 23, 0);
        createItem(ver4, DayOfWeek.FRIDAY, 22, 20, 23, 0);
        createItem(ver4, DayOfWeek.SATURDAY, 15, 10, 21, 0);
        createItem(ver4, DayOfWeek.SUNDAY, 13, 0, 21, 0);


        // 5. 이지윤
        Member member이지윤 = memberMap.get("이지윤");
        if (member이지윤 == null) throw new IllegalStateException("[이지윤] 회원을 찾을 수 없습니다.");
        ScheduleTemplate tpl5 = ScheduleTemplate.builder()
                .member(member이지윤)
                .status(TemplateStatus.APPROVED)
                .name("이지윤 주간 기본 템플릿")
                .description("엑셀 초기 데이터")
                .build();
        scheduleTemplateRepository.save(tpl5);

        ScheduleTemplateVersion ver5 = ScheduleTemplateVersion.builder()
                .template(tpl5)
                .versionNo(1)
                .status(TemplateStatus.APPROVED)
                .build();
        scheduleTemplateVersionRepository.save(ver5);

        // 5. 이지윤 요일별 아이템
        createItem(ver5, DayOfWeek.MONDAY, 19, 0, 23, 59); // 25:00 → 23:59
        createItem(ver5, DayOfWeek.FRIDAY, 19, 0, 23, 59);
        createItem(ver5, DayOfWeek.SATURDAY, 14, 30, 18, 0);
        createItem(ver5, DayOfWeek.SUNDAY, 20, 30, 23, 59);


        // 6. 오소영
        Member member오소영 = memberMap.get("오소영");
        if (member오소영 == null) throw new IllegalStateException("[오소영] 회원을 찾을 수 없습니다.");
        ScheduleTemplate tpl6 = ScheduleTemplate.builder()
                .member(member오소영)
                .status(TemplateStatus.APPROVED)
                .name("오소영 주간 기본 템플릿")
                .description("엑셀 초기 데이터")
                .build();
        scheduleTemplateRepository.save(tpl6);

        ScheduleTemplateVersion ver6 = ScheduleTemplateVersion.builder()
                .template(tpl6)
                .versionNo(1)
                .status(TemplateStatus.APPROVED)
                .build();
        scheduleTemplateVersionRepository.save(ver6);

        // 6. 오소영 요일별 아이템
        createItem(ver6, DayOfWeek.MONDAY, 18, 15, 23, 59);
        createItem(ver6, DayOfWeek.TUESDAY, 18, 15, 23, 59);
        createItem(ver6, DayOfWeek.FRIDAY, 18, 15, 23, 59);
        createItem(ver6, DayOfWeek.SATURDAY, 9, 0, 17, 0);
        createItem(ver6, DayOfWeek.SUNDAY, 9, 0, 12, 0);


        // 7. 정서윤
        Member member정서윤 = memberMap.get("정서윤");
        if (member정서윤 == null) throw new IllegalStateException("[정서윤] 회원을 찾을 수 없습니다.");
        ScheduleTemplate tpl7 = ScheduleTemplate.builder()
                .member(member정서윤)
                .status(TemplateStatus.APPROVED)
                .name("정서윤 주간 기본 템플릿")
                .description("엑셀 초기 데이터")
                .build();
        scheduleTemplateRepository.save(tpl7);

        ScheduleTemplateVersion ver7 = ScheduleTemplateVersion.builder()
                .template(tpl7)
                .versionNo(1)
                .status(TemplateStatus.APPROVED)
                .build();
        scheduleTemplateVersionRepository.save(ver7);

        // 7. 정서윤 요일별 아이템
        createItem(ver7, DayOfWeek.FRIDAY, 22, 45, 23, 59);


        // 8. 최지안
        Member member최지안 = memberMap.get("최지안");
        if (member최지안 == null) throw new IllegalStateException("[최지안] 회원을 찾을 수 없습니다.");
        ScheduleTemplate tpl8 = ScheduleTemplate.builder()
                .member(member최지안)
                .status(TemplateStatus.APPROVED)
                .name("최지안 주간 기본 템플릿")
                .description("엑셀 초기 데이터")
                .build();
        scheduleTemplateRepository.save(tpl8);

        ScheduleTemplateVersion ver8 = ScheduleTemplateVersion.builder()
                .template(tpl8)
                .versionNo(1)
                .status(TemplateStatus.APPROVED)
                .build();
        scheduleTemplateVersionRepository.save(ver8);

        // 8. 최지안 요일별 아이템
        createItem(ver8, DayOfWeek.MONDAY, 16, 30, 23, 30);
        createItem(ver8, DayOfWeek.TUESDAY, 15, 30, 23, 30);
        createItem(ver8, DayOfWeek.WEDNESDAY, 15, 30, 23, 30);
        createItem(ver8, DayOfWeek.THURSDAY, 16, 30, 23, 30);
        createItem(ver8, DayOfWeek.FRIDAY, 16, 30, 23, 30);
        createItem(ver8, DayOfWeek.SATURDAY, 14, 30, 23, 30);
        createItem(ver8, DayOfWeek.SUNDAY, 14, 0, 23, 30);


        // 9. 문지호
        Member member문지호 = memberMap.get("문지호");
        if (member문지호 == null) throw new IllegalStateException("[문지호] 회원을 찾을 수 없습니다.");
        ScheduleTemplate tpl9 = ScheduleTemplate.builder()
                .member(member문지호)
                .status(TemplateStatus.APPROVED)
                .name("문지호 주간 기본 템플릿")
                .description("엑셀 초기 데이터")
                .build();
        scheduleTemplateRepository.save(tpl9);

        ScheduleTemplateVersion ver9 = ScheduleTemplateVersion.builder()
                .template(tpl9)
                .versionNo(1)
                .status(TemplateStatus.APPROVED)
                .build();
        scheduleTemplateVersionRepository.save(ver9);

        // 9. 문지호 요일별 아이템
        createItem(ver9, DayOfWeek.MONDAY, 22, 30, 23, 30);
        createItem(ver9, DayOfWeek.TUESDAY, 22, 30, 23, 30);
        createItem(ver9, DayOfWeek.WEDNESDAY, 21, 0, 23, 0);
        createItem(ver9, DayOfWeek.THURSDAY, 21, 0, 23, 0);
        createItem(ver9, DayOfWeek.FRIDAY, 22, 30, 23, 30);
        createItem(ver9, DayOfWeek.SATURDAY, 19, 0, 23, 0);
        createItem(ver9, DayOfWeek.SUNDAY, 16, 0, 18, 0);


        // 10. 박예진
        Member member박예진 = memberMap.get("박예진");
        if (member박예진 == null) throw new IllegalStateException("[박예진] 회원을 찾을 수 없습니다.");
        ScheduleTemplate tpl10 = ScheduleTemplate.builder()
                .member(member박예진)
                .status(TemplateStatus.APPROVED)
                .name("박예진 주간 기본 템플릿")
                .description("엑셀 초기 데이터")
                .build();
        scheduleTemplateRepository.save(tpl10);

        ScheduleTemplateVersion ver10 = ScheduleTemplateVersion.builder()
                .template(tpl10)
                .versionNo(1)
                .status(TemplateStatus.APPROVED)
                .build();
        scheduleTemplateVersionRepository.save(ver10);

        // 10. 박예진 요일별 아이템
        createItem(ver10, DayOfWeek.TUESDAY, 18, 0, 23, 59);
        createItem(ver10, DayOfWeek.WEDNESDAY, 18, 0, 23, 59);
        createItem(ver10, DayOfWeek.FRIDAY, 22, 30, 23, 59);
        createItem(ver10, DayOfWeek.SATURDAY, 9, 0, 23, 59);
        createItem(ver10, DayOfWeek.SUNDAY, 9, 0, 23, 59);


        // 11. 이예린
        Member member이예린 = memberMap.get("이예린");
        if (member이예린 == null) throw new IllegalStateException("[이예린] 회원을 찾을 수 없습니다.");
        ScheduleTemplate tpl11 = ScheduleTemplate.builder()
                .member(member이예린)
                .status(TemplateStatus.APPROVED)
                .name("이예린 주간 기본 템플릿")
                .description("엑셀 초기 데이터")
                .build();
        scheduleTemplateRepository.save(tpl11);

        ScheduleTemplateVersion ver11 = ScheduleTemplateVersion.builder()
                .template(tpl11)
                .versionNo(1)
                .status(TemplateStatus.APPROVED)
                .build();
        scheduleTemplateVersionRepository.save(ver11);

        // 11. 이예린 요일별 아이템
        createItem(ver11, DayOfWeek.MONDAY, 18, 30, 23, 30);
        createItem(ver11, DayOfWeek.TUESDAY, 18, 30, 23, 30);
        createItem(ver11, DayOfWeek.WEDNESDAY, 18, 30, 23, 30);
        createItem(ver11, DayOfWeek.THURSDAY, 18, 30, 23, 30);
        createItem(ver11, DayOfWeek.FRIDAY, 18, 30, 23, 30);
        createItem(ver11, DayOfWeek.SATURDAY, 15, 0, 22, 0);
        createItem(ver11, DayOfWeek.SUNDAY, 13, 0, 23, 0);


        // 12. 김린하
        Member member김린하 = memberMap.get("김린하");
        if (member김린하 == null) throw new IllegalStateException("[김린하] 회원을 찾을 수 없습니다.");
        ScheduleTemplate tpl12 = ScheduleTemplate.builder()
                .member(member김린하)
                .status(TemplateStatus.APPROVED)
                .name("김린하 주간 기본 템플릿")
                .description("엑셀 초기 데이터")
                .build();
        scheduleTemplateRepository.save(tpl12);

        ScheduleTemplateVersion ver12 = ScheduleTemplateVersion.builder()
                .template(tpl12)
                .versionNo(1)
                .status(TemplateStatus.APPROVED)
                .build();
        scheduleTemplateVersionRepository.save(ver12);

        // 12. 김린하 요일별 아이템
        createItem(ver12, DayOfWeek.MONDAY, 22, 0, 23, 59);
        createItem(ver12, DayOfWeek.TUESDAY, 19, 0, 23, 59);
        createItem(ver12, DayOfWeek.WEDNESDAY, 19, 0, 23, 0);
        createItem(ver12, DayOfWeek.THURSDAY, 19, 0, 23, 0);
        createItem(ver12, DayOfWeek.FRIDAY, 22, 0, 23, 59);
        createItem(ver12, DayOfWeek.SUNDAY, 19, 0, 23, 0);


        // 13. 김지효
        Member member김지효 = memberMap.get("김지효");
        if (member김지효 == null) throw new IllegalStateException("[김지효] 회원을 찾을 수 없습니다.");
        ScheduleTemplate tpl13 = ScheduleTemplate.builder()
                .member(member김지효)
                .status(TemplateStatus.APPROVED)
                .name("김지효 주간 기본 템플릿")
                .description("엑셀 초기 데이터")
                .build();
        scheduleTemplateRepository.save(tpl13);

        ScheduleTemplateVersion ver13 = ScheduleTemplateVersion.builder()
                .template(tpl13)
                .versionNo(1)
                .status(TemplateStatus.APPROVED)
                .build();
        scheduleTemplateVersionRepository.save(ver13);

        // 13. 김지효 요일별 아이템
        createItem(ver13, DayOfWeek.MONDAY, 9, 0, 18, 0);
        createItem(ver13, DayOfWeek.TUESDAY, 9, 0, 18, 0);
        createItem(ver13, DayOfWeek.WEDNESDAY, 9, 0, 18, 0);
        createItem(ver13, DayOfWeek.THURSDAY, 9, 0, 18, 0);
        createItem(ver13, DayOfWeek.FRIDAY, 9, 0, 18, 0);
        createItem(ver13, DayOfWeek.SATURDAY, 9, 0, 18, 0);


        // 14. 윤지상
        Member member윤지상 = memberMap.get("윤지상");
        if (member윤지상 == null) throw new IllegalStateException("[윤지상] 회원을 찾을 수 없습니다.");
        ScheduleTemplate tpl14 = ScheduleTemplate.builder()
                .member(member윤지상)
                .status(TemplateStatus.APPROVED)
                .name("윤지상 주간 기본 템플릿")
                .description("엑셀 초기 데이터")
                .build();
        scheduleTemplateRepository.save(tpl14);

        ScheduleTemplateVersion ver14 = ScheduleTemplateVersion.builder()
                .template(tpl14)
                .versionNo(1)
                .status(TemplateStatus.APPROVED)
                .build();
        scheduleTemplateVersionRepository.save(ver14);

        // 14. 윤지상 요일별 아이템
        createItem(ver14, DayOfWeek.MONDAY, 19, 0, 23, 59);
        createItem(ver14, DayOfWeek.TUESDAY, 19, 0, 23, 59);
        createItem(ver14, DayOfWeek.WEDNESDAY, 19, 0, 23, 59);
        createItem(ver14, DayOfWeek.THURSDAY, 19, 0, 23, 59);
        createItem(ver14, DayOfWeek.FRIDAY, 19, 0, 23, 59);
        createItem(ver14, DayOfWeek.SATURDAY, 9, 0, 23, 59);
        createItem(ver14, DayOfWeek.SUNDAY, 9, 0, 23, 59);


        // 15. 최윤후
        Member member최윤후 = memberMap.get("최윤후");
        if (member최윤후 == null) throw new IllegalStateException("[최윤후] 회원을 찾을 수 없습니다.");
        ScheduleTemplate tpl15 = ScheduleTemplate.builder()
                .member(member최윤후)
                .status(TemplateStatus.APPROVED)
                .name("최윤후 주간 기본 템플릿")
                .description("엑셀 초기 데이터")
                .build();
        scheduleTemplateRepository.save(tpl15);

        ScheduleTemplateVersion ver15 = ScheduleTemplateVersion.builder()
                .template(tpl15)
                .versionNo(1)
                .status(TemplateStatus.APPROVED)
                .build();
        scheduleTemplateVersionRepository.save(ver15);

        // 15. 최윤후 요일별 아이템
        createItem(ver15, DayOfWeek.MONDAY, 20, 0, 23, 59);
        createItem(ver15, DayOfWeek.TUESDAY, 16, 30, 23, 59);
        createItem(ver15, DayOfWeek.WEDNESDAY, 15, 30, 23, 59);
        createItem(ver15, DayOfWeek.THURSDAY, 22, 30, 23, 59);
        createItem(ver15, DayOfWeek.FRIDAY, 16, 30, 23, 59);
        createItem(ver15, DayOfWeek.SATURDAY, 9, 0, 23, 59);
        createItem(ver15, DayOfWeek.SUNDAY, 10, 30, 23, 59);


        // 16. 주시은
        Member member주시은 = memberMap.get("주시은");
        if (member주시은 == null) throw new IllegalStateException("[주시은] 회원을 찾을 수 없습니다.");
        ScheduleTemplate tpl16 = ScheduleTemplate.builder()
                .member(member주시은)
                .status(TemplateStatus.APPROVED)
                .name("주시은 주간 기본 템플릿")
                .description("엑셀 초기 데이터")
                .build();
        scheduleTemplateRepository.save(tpl16);

        ScheduleTemplateVersion ver16 = ScheduleTemplateVersion.builder()
                .template(tpl16)
                .versionNo(1)
                .status(TemplateStatus.APPROVED)
                .build();
        scheduleTemplateVersionRepository.save(ver16);

        // 16. 주시은 요일별 아이템
        createItem(ver16, DayOfWeek.MONDAY, 22, 30, 23, 59);
        createItem(ver16, DayOfWeek.TUESDAY, 18, 30, 23, 59);
        createItem(ver16, DayOfWeek.WEDNESDAY, 22, 30, 23, 59);
        createItem(ver16, DayOfWeek.THURSDAY, 18, 30, 23, 59);
        createItem(ver16, DayOfWeek.FRIDAY, 18, 30, 23, 59);
        createItem(ver16, DayOfWeek.SATURDAY, 9, 0, 23, 59);
        createItem(ver16, DayOfWeek.SUNDAY, 9, 0, 23, 59);


        // 17. 김민서
        Member member김민서 = memberMap.get("김민서");
        if (member김민서 == null) throw new IllegalStateException("[김민서] 회원을 찾을 수 없습니다.");
        ScheduleTemplate tpl17 = ScheduleTemplate.builder()
                .member(member김민서)
                .status(TemplateStatus.APPROVED)
                .name("김민서 주간 기본 템플릿")
                .description("엑셀 초기 데이터")
                .build();
        scheduleTemplateRepository.save(tpl17);

        ScheduleTemplateVersion ver17 = ScheduleTemplateVersion.builder()
                .template(tpl17)
                .versionNo(1)
                .status(TemplateStatus.APPROVED)
                .build();
        scheduleTemplateVersionRepository.save(ver17);

        // 17. 김민서 요일별 아이템
        createItem(ver17, DayOfWeek.MONDAY, 22, 30, 23, 30);
        createItem(ver17, DayOfWeek.TUESDAY, 19, 30, 23, 30);
        createItem(ver17, DayOfWeek.WEDNESDAY, 22, 30, 23, 30);
        createItem(ver17, DayOfWeek.THURSDAY, 18, 30, 23, 30);
        createItem(ver17, DayOfWeek.FRIDAY, 21, 30, 23, 59);
        createItem(ver17, DayOfWeek.SATURDAY, 14, 30, 23, 59);
        createItem(ver17, DayOfWeek.SUNDAY, 10, 30, 18, 0);


        // 18. 정연우
        Member member정연우 = memberMap.get("정연우");
        if (member정연우 == null) throw new IllegalStateException("[정연우] 회원을 찾을 수 없습니다.");
        ScheduleTemplate tpl18 = ScheduleTemplate.builder()
                .member(member정연우)
                .status(TemplateStatus.APPROVED)
                .name("정연우 주간 기본 템플릿")
                .description("엑셀 초기 데이터")
                .build();
        scheduleTemplateRepository.save(tpl18);

        ScheduleTemplateVersion ver18 = ScheduleTemplateVersion.builder()
                .template(tpl18)
                .versionNo(1)
                .status(TemplateStatus.APPROVED)
                .build();
        scheduleTemplateVersionRepository.save(ver18);

        // 18. 정연우 요일별 아이템
        createItem(ver18, DayOfWeek.MONDAY, 18, 0, 23, 0);
        createItem(ver18, DayOfWeek.WEDNESDAY, 18, 0, 23, 0);
        createItem(ver18, DayOfWeek.FRIDAY, 22, 30, 23, 59);
        createItem(ver18, DayOfWeek.SATURDAY, 14, 30, 23, 59);
        createItem(ver18, DayOfWeek.SUNDAY, 18, 0, 23, 0);


        // 19. 강지호
        Member member강지호 = memberMap.get("강지호");
        if (member강지호 == null) throw new IllegalStateException("[강지호] 회원을 찾을 수 없습니다.");
        ScheduleTemplate tpl19 = ScheduleTemplate.builder()
                .member(member강지호)
                .status(TemplateStatus.APPROVED)
                .name("강지호 주간 기본 템플릿")
                .description("엑셀 초기 데이터")
                .build();
        scheduleTemplateRepository.save(tpl19);

        ScheduleTemplateVersion ver19 = ScheduleTemplateVersion.builder()
                .template(tpl19)
                .versionNo(1)
                .status(TemplateStatus.APPROVED)
                .build();
        scheduleTemplateVersionRepository.save(ver19);

        // 19. 강지호 요일별 아이템
        createItem(ver19, DayOfWeek.MONDAY, 18, 0, 22, 0);
        createItem(ver19, DayOfWeek.TUESDAY, 18, 0, 22, 0);
        createItem(ver19, DayOfWeek.THURSDAY, 18, 0, 22, 0);
        createItem(ver19, DayOfWeek.SATURDAY, 10, 40, 10, 40);
        createItem(ver19, DayOfWeek.SUNDAY, 14, 50, 22, 0);


        // 20. 김윤아
        Member member김윤아 = memberMap.get("김윤아");
        if (member김윤아 == null) throw new IllegalStateException("[김윤아] 회원을 찾을 수 없습니다.");
        ScheduleTemplate tpl20 = ScheduleTemplate.builder()
                .member(member김윤아)
                .status(TemplateStatus.APPROVED)
                .name("김윤아 주간 기본 템플릿")
                .description("엑셀 초기 데이터")
                .build();
        scheduleTemplateRepository.save(tpl20);

        ScheduleTemplateVersion ver20 = ScheduleTemplateVersion.builder()
                .template(tpl20)
                .versionNo(1)
                .status(TemplateStatus.APPROVED)
                .build();
        scheduleTemplateVersionRepository.save(ver20);

        // 20. 김윤아 요일별 아이템
        createItem(ver20, DayOfWeek.MONDAY, 22, 30, 23, 30);
        createItem(ver20, DayOfWeek.TUESDAY, 21, 30, 23, 30);
        createItem(ver20, DayOfWeek.WEDNESDAY, 19, 30, 23, 30);
        createItem(ver20, DayOfWeek.THURSDAY, 21, 30, 23, 30);
        createItem(ver20, DayOfWeek.FRIDAY, 22, 30, 23, 30);
        createItem(ver20, DayOfWeek.SATURDAY, 9, 0, 23, 30);
        createItem(ver20, DayOfWeek.SUNDAY, 13, 0, 23, 30);

    }

    private void createItem(ScheduleTemplateVersion version, DayOfWeek dayOfWeek,
                            int sh, int sm, int eh, int em) {
        ScheduleTemplateItem item = ScheduleTemplateItem.builder()
                .version(version)
                .dayOfWeek(dayOfWeek)
                .type(ScheduleBlockType.STUDY)
                .startTime(LocalTime.of(sh, sm))
                .endTime(LocalTime.of(eh, em))
                .description(null)
                .build();
        scheduleTemplateItemRepository.save(item);
    }
}