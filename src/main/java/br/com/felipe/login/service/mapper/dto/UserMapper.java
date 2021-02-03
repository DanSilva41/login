package br.com.felipe.login.service.mapper.dto;

import br.com.felipe.login.domain.Application;
import br.com.felipe.login.domain.AuthorityUserApplication;
import br.com.felipe.login.domain.User;
import com.thesolution.structure.backend.common.abstracts.mapper.AbstractMapper;
import com.thesolution.structure.backend.commons.datatransfers.dto.ApplicationsAuthoritiesDTO;
import com.thesolution.structure.backend.commons.datatransfers.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;


@AllArgsConstructor
@Component
public class UserMapper implements AbstractMapper<User, UserDTO> {

    private final PersonMapper personMapper;
    private final ApplicationMapper applicationMapper;
    private final AuthorityMapper authorityMapper;

    @Override
    public UserDTO primaryToSecondary(User user) {
        return Objects.isNull(user) ? null :
            UserDTO.builder()
                .code(user.getCode())
                .username(user.getUsername())
                .password(user.getPassword())
                .actived(user.isActived())
                .createdDate(LocalDateTime.ofInstant(user.getCreatedDate(), ZoneOffset.UTC))
                .lastModifiedDate(LocalDateTime.ofInstant(user.getLast_modified_date(), ZoneOffset.UTC))
                .setOfApplications(this.agruparPerfisPorAplicacao(user.getAplicacoesPerfis()))
                .person(personMapper.primaryToSecondary(user.getPerson()))
                .build();
    }

    @Override
    public User secondaryToPrimary(UserDTO userDTO) {
        return Objects.isNull(userDTO) ? null :
            User.builder()
                .code(userDTO.getCode())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .person(personMapper.secondaryToPrimary(userDTO.getPerson()))
                .build();
    }

    private Set<ApplicationsAuthoritiesDTO> agruparPerfisPorAplicacao(Set<AuthorityUserApplication> authoritiesApplicationUser) {
        if (Objects.nonNull(authoritiesApplicationUser) && !authoritiesApplicationUser.isEmpty()) {
            Map<Application, List<AuthorityUserApplication>> authoritiesByApplication = authoritiesApplicationUser.stream().collect(Collectors.groupingBy(AuthorityUserApplication::getApplication));

            return authoritiesByApplication.entrySet().stream().map((applicationKey) ->
                ApplicationsAuthoritiesDTO.builder()
                    .application(applicationMapper.primaryToSecondary(applicationKey.getKey()))
                    .authorities(authorityMapper.primariesToSecondaries(applicationKey.getValue().stream().map(AuthorityUserApplication::getAuthority).collect(Collectors.toSet())))
                    .build()
            ).collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }
}
