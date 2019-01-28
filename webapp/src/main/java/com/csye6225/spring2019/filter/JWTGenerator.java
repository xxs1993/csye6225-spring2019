package com.csye6225.spring2019.filter;

import com.csye6225.spring2019.entity.Account;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

public class JWTGenerator {
    public static String generator( Account account){
        if(account == null) return "";
        Claims claim = Jwts.claims();
        claim.setSubject(account.getEmailAddress());
        claim.setExpiration(new Date(System.currentTimeMillis()+1000*60));
        String jws = Jwts.builder() // (1)
                .setClaims(claim)
                .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS256) )          // (3)
                .compact();             // (4)
        return jws;
    }
}
