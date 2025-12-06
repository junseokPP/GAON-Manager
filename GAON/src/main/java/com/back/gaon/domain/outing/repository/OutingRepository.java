// com.back.gaon.domain.outing.repository.OutingRepository

package com.back.gaon.domain.outing.repository;

import com.back.gaon.domain.outing.entity.Outing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OutingRepository extends JpaRepository<Outing, Long> {

    // 최근 외출 1개 (member + schedule 기준)
    Optional<Outing> findTop1ByMemberIdAndScheduleIdOrderByOutAtDesc(Long memberId, Long scheduleId);

    // 히스토리용 (나중에 history 페이지에서 활용 가능)
    List<Outing> findByMemberIdAndScheduleIdOrderByOutAtAsc(Long memberId, Long scheduleId);
}