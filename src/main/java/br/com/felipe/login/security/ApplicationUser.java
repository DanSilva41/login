package br.com.felipe.login.security;

import com.thesolution.structure.backend.commons.datatransfers.dto.UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;


public class ApplicationUser extends User {

    private static final long serialVersionUID = 1L;

    private final UserDTO user;

    public ApplicationUser(UserDTO user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getUsername(), user.getPassword(), authorities);
        this.user = user;
    }

    public UserDTO getUser() {
        return user;

    }
}
