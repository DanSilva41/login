package br.com.felipe.login.security.token;

import br.com.felipe.login.service.UserService;
import com.thesolution.structure.backend.commons.datatransfers.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@AllArgsConstructor
@Component
public class CustomJwtTokenConverter extends JwtAccessTokenConverter {

    private final UserService userService;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        String login = authentication.getName().split(";")[0];
        Optional<UserDTO> usuarioRetornado = userService.findByUsername(login);

        usuarioRetornado.ifPresent(u -> {
            Map<String, Object> additionalInfo = new HashMap<>();
            additionalInfo.put("user", u);
            additionalInfo.put("setOfApplications", u.getSetOfApplications());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        });
        return super.enhance(accessToken, authentication);
    }

}

