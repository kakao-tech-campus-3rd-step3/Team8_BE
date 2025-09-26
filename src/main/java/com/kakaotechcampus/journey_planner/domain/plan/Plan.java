package com.kakaotechcampus.journey_planner.domain.plan;

import com.kakaotechcampus.journey_planner.domain.member.Member;
import com.kakaotechcampus.journey_planner.domain.memberplan.MemberPlan;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "제목은 비어 있을 수 없습니다.")
    private String title;

    private String description;
    @NotNull(message = "시작일은 필수 값입니다.")
    private LocalDate startDate;

    @NotNull(message = "종료일은 필수 값입니다.")
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id")
    private Member organizer;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<MemberPlan> memberPlans = new ArrayList<>();

    public Plan(String title, String description, LocalDate startDate, LocalDate endDate, Member organizer) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.organizer = organizer;
    }

    public void update(String title, String description, LocalDate startDate, LocalDate endDate) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void addMemberPlan(MemberPlan memberPlan) {
        memberPlans.add(memberPlan);
    }

    public boolean hasMember(Member member) {
        return this.memberPlans.stream()
                .map(MemberPlan::getMember)
                .anyMatch(planMember -> planMember.equals(member));
    }

    public boolean isOrganizer(Member member) {
        return this.organizer.equals(member);
    }
}
