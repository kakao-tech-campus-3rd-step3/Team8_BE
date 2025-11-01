package com.kakaotechcampus.journey_planner.domain.memo.repository;

import com.kakaotechcampus.journey_planner.domain.memo.Memo;
import com.kakaotechcampus.journey_planner.domain.node.Node;
import com.kakaotechcampus.journey_planner.global.common.repository.NodeRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemoRepository extends NodeRepository<Node> {
    @Query("SELECT m FROM Memo m WHERE m.planId=:planId")
    List<Memo> findAllAndPlanId(@Param("planId") Long planId);

    @Query("SELECT m FROM Memo m WHERE m.id=:memoId AND m.planId=:planId")
    Optional<Memo> findByIdAndPlanId(@Param("memoId") Long memoId, @Param("planId") Long planId);
}
