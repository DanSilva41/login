package br.com.felipe.login.security.token;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Optional;


@ControllerAdvice
public class RefreshTokenProcessor implements ResponseBodyAdvice<OAuth2AccessToken> {

    @Override
    public boolean supports(MethodParameter returnType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return Objects.nonNull(returnType.getMethod()) && returnType.getMethod().getName().equals(TokenConstantes.POST_ACCESS_TOKEN);
    }

    @Override
    public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken body, @NonNull MethodParameter returnType,
                                             @NonNull MediaType selectedContentType,
                                             @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                             @NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response) {

        HttpServletRequest req = ((ServletServerHttpRequest) request).getServletRequest();
        HttpServletResponse resp = ((ServletServerHttpResponse) response).getServletResponse();


        Optional.ofNullable(body).ifPresent(oAuth2AccessToken -> {
            DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) oAuth2AccessToken;

            Optional.ofNullable(body.getRefreshToken()).ifPresent(oAuth2RefreshToken -> {
                String refreshToken = oAuth2RefreshToken.getValue();
                adicionarRefreshToken(refreshToken, req, resp);
                removerRefreshTokenDoBody(token);
            });
        });

        return body;
    }

    private void adicionarRefreshToken(String refreshToken, HttpServletRequest req, HttpServletResponse resp) {
        Cookie refreshTokenCookie = new Cookie(TokenConstantes.REFRESH_TOKEN_COOKIE_NAME, refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(false); // TODO: Migrar para true em producao
        refreshTokenCookie.setPath(req.getContextPath() + TokenConstantes.URI_OAUTH_TOKEN);
        refreshTokenCookie.setMaxAge(2592000);

        resp.addCookie(refreshTokenCookie);
    }

    private void removerRefreshTokenDoBody(DefaultOAuth2AccessToken token) {
        token.setRefreshToken(null);
    }


}

