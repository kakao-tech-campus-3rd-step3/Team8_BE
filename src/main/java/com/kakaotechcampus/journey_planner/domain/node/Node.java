package com.kakaotechcampus.journey_planner.domain.node;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@MappedSuperclass
public class Node {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Setter
    @Column(name = "plan_id", insertable = false, updatable = false)
    private Long planId;

    @Version
    private Long version = 0L;

    @Column(name = "uuid", unique = true, nullable = false)
    private String uuid;

    @Enumerated(EnumType.STRING)
    private NodeStatus status;

    @Setter
    @Enumerated(EnumType.STRING)
    private NodeSort destination;

    public Node() {
        this.uuid = UUID.randomUUID().toString();
        this.status = NodeStatus.CREATE;
        this.destination = NodeSort.MEMO;
    }

    public void increaseVersion(){
        this.version++;
    }
}
