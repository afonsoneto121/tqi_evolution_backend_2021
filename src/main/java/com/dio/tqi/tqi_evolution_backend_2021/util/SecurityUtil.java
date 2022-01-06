package com.dio.tqi.tqi_evolution_backend_2021.util;

import com.dio.tqi.tqi_evolution_backend_2021.exception.NotAuthorizedException;
import com.dio.tqi.tqi_evolution_backend_2021.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class SecurityUtil {
    private final TokenService tokenService;

    public void authorizedUser(HttpServletRequest request, String idUser) throws NotAuthorizedException {
        String token = getTokenFromHeader(request);
        if (!verifyIfUserIsAuthorized(token, idUser)) {
            throw new NotAuthorizedException("User not authorized");
        }
    }

    private String getTokenFromHeader(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }

        return token.substring(7, token.length());
    }

    private boolean verifyIfUserIsAuthorized(String token, String idUser) {
        return tokenService.getTokenSubject(token).equals(idUser);
    }
}
