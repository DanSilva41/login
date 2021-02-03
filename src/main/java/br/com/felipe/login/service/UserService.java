package br.com.felipe.login.service;

import br.com.felipe.login.web.rest.dto.UserDTO;
import br.com.felipe.login.web.rest.vm.user.CreateUserVM;
import br.com.felipe.login.web.rest.vm.user.UpdateUserVM;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;


public interface UserService {

    @Transactional(readOnly = true)
    Collection<UserDTO> findAll();

    @Transactional(readOnly = true)
    Optional<UserDTO> findByUsername(final String username);

    @Transactional
    UserDTO create(@Valid CreateUserVM newUser);

    @Transactional
    UserDTO update(@Valid UpdateUserVM existingUser);

    @Transactional(readOnly = true)
    void validateIfExistingUser(final String username, final String email);

    @Transactional(readOnly = true)
    void validateIfExistingUserWithDifferentCode(final String username, final String email, final Integer code);

    @Transactional
    void activeOrDeactive(final Integer code);
}
