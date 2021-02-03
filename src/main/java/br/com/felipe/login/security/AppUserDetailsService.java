package br.com.felipe.login.security;

import br.com.felipe.login.service.UserService;
import com.thesolution.structure.backend.commons.datatransfers.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@AllArgsConstructor
@Service
public class AppUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO user = userService.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Incorrect username and / or password"));
        return new ApplicationUser(user, getAuthorities(user));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(UserDTO user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        if (Objects.nonNull(user.getSetOfApplications()) && !user.getSetOfApplications().isEmpty()) {
            user.getSetOfApplications().forEach(a ->
                a.getAuthorities().forEach(p -> authorities.add(new SimpleGrantedAuthority(p.getName().toUpperCase())))
            );
        }
        return authorities;
    }
}

