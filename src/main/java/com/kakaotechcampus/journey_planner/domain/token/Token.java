package com.kakaotechcampus.journey_planner.domain.token;

import javax.crypto.SecretKey;
import java.util.Date;

public interface Token{
    TokenType getType();

    SecretKey getSecretKey();

    Date getNowDate();

    Date getExpirationDate();
}
