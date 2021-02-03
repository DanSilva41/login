package br.com.felipe.login.service.mapper.dto;

import br.com.felipe.login.domain.Authority;
import com.thesolution.structure.backend.common.abstracts.mapper.AbstractMapper;
import com.thesolution.structure.backend.commons.datatransfers.dto.AuthorityDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class AuthorityMapper implements AbstractMapper<Authority, AuthorityDTO> {

    @Override
    public AuthorityDTO primaryToSecondary(Authority authority) {
        return Objects.isNull(authority) ? null :
            AuthorityDTO.builder()
                .name(authority.getName())
                .description(authority.getDescription())
                .build();
    }

    @Override
    public Authority secondaryToPrimary(AuthorityDTO authorityDTO) {
        return Objects.isNull(authorityDTO) ? null :
            Authority.builder()
                .name(authorityDTO.getName())
                .description(authorityDTO.getDescription())
                .build();
    }
}
