package com.kakaotechcampus.journey_planner.application.auth.utils;

import com.kakaotechcampus.journey_planner.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordEncodeUtil {
    private final PasswordEncoder bCryptPasswordEncoder;

    public void passwordEncoding(Member member) {
        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
    }
}
