
package com.back.gaon.domain.member.repository;

import com.back.gaon.domain.member.entity.AdminDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminDetailRepository extends JpaRepository<AdminDetail, Long> {

    /**
     * Member ID로 관리자 상세 정보 조회
     */
    Optional<AdminDetail> findByMemberId(Long memberId);
}