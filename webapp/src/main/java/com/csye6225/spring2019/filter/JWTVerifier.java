package com.csye6225.spring2019.filter;

import com.csye6225.spring2019.constant.TokenErrorCodeConstants;
import com.csye6225.spring2019.exception.TokenException;
import com.csye6225.spring2019.constant.TokenErrorCodeConstants;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.log4j.Log4j;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log4j
public class JWTVerifier  {
    public static boolean isVerified(HttpServletRequest request, HttpServletResponse response) throws  TokenException {
        String authHeader = request.getHeader("Authorization");
        String basicString = "Bearer";
        String message = "";
        if (StringUtils.isEmpty(authHeader) || !authHeader.contains(basicString)) {
            message = "Missing or invalid Authorization header";
            log.info(message);
            throw new TokenException(message, TokenErrorCodeConstants.MISSING_TOKEN);
        } else {
            String token = authHeader.trim().substring(basicString.length()).trim();
            try {
                Jwts.parser().setSigningKey(Keys.secretKeyFor(SignatureAlgorithm.HS256)).parseClaimsJws(token).getBody();
            } catch (IncorrectClaimException e) {
                message = "Incorrect authorization";
                log.info(message);
                throw new TokenException(message,TokenErrorCodeConstants.INCOORECT_TOKEN);
            } catch (ExpiredJwtException e) {
                throw new TokenException(message,TokenErrorCodeConstants.EXPIRE_TOKEN);
            } catch (Exception e) {
                throw new TokenException(message,TokenErrorCodeConstants.UNEXPECTED_ERROR);
            }
        }
        return true;
    }


}
