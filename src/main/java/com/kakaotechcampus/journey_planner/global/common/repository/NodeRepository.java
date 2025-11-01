package com.kakaotechcampus.journey_planner.global.common.repository;

import com.kakaotechcampus.journey_planner.domain.node.Node;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NodeRepository<T extends Node> extends JpaRepository<T, Long> {
}
