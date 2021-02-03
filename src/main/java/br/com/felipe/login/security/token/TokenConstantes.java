package br.com.felipe.login.security.token;


public final class TokenConstantes {

    public static final String URI_OAUTH_TOKEN = "/oauth/token";
    public static final String REFRESH_TOKEN_COOKIE_NAME = "refreshToken";
    static final String GRANT_TYPE = "grant_type";
    static final String REFRESH_TOKEN_GRANT_TYPE = "refresh_token";
    static final String POST_ACCESS_TOKEN = "postAccessToken";

    public static final String HEADER_STRING = "Authorization";
    private TokenConstantes() {
    }
}
