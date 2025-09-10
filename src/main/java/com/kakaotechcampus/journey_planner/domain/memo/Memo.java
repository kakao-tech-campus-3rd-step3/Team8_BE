package com.kakaotechcampus.journey_planner.domain.memo;

import com.kakaotechcampus.journey_planner.domain.plan.Plan;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private Float xPosition;
    private Float yPosition;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    protected Memo() {}

    public Memo(String title, String content, float xPosition, float yPosition) {
        this.title = title;
        this.content = content;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public void assignToPlan(Plan plan) {this.plan = plan;}

    public void update(String title, String content, float xPosition, float yPosition) {
        this.title = title;
        this.content = content;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }
}
