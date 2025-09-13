package com.kakaotechcampus.journey_planner.domain.member;

import jakarta.persistence.*;

@Entity
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;
    private String contact;
    private String email;
    private String password;
    private MbtiType mbtiType;

    protected Member() {
    }

    public Member(String name, String contact, String email, String password, String mbti) {
        this.name = name;
        this.contact = contact;
        this.email = email;
        this.password = password;
        this.mbtiType = MbtiType.valueOf(mbti.toUpperCase());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getMbtiType() {
        return mbtiType.toString();
    }

    public boolean verifyPassword(String password) {
        return this.password.equals(password);
    }
}

