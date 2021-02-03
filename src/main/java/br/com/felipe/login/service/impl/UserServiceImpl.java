package br.com.felipe.login.service.impl;

import br.com.felipe.login.service.UserService;
import br.com.felipe.login.support.Constants;
import br.com.felipe.login.domain.Person;
import br.com.felipe.login.domain.User;
import br.com.felipe.login.repository.UserRepository;
import br.com.felipe.login.service.mapper.dto.UserMapper;
import br.com.felipe.login.support.exception.BadRequestException;
import br.com.felipe.login.web.rest.dto.UserDTO;
import br.com.felipe.login.web.rest.vm.user.CreateUserVM;
import br.com.felipe.login.web.rest.vm.user.UpdateUserVM;
import lombok.AllArgsConstructor;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

import static br.com.felipe.login.repository.specifications.UserSpecification.*;
import static br.com.felipe.login.support.enums.ApplicationMessageCode.*;


@AllArgsConstructor
@Validated
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Collection<UserDTO> findAll() {
        return userMapper.primariesToSecondaries(userRepository.findAll(Sort.by(Sort.Direction.ASC, User_.CODE)));
    }

    @Override
    public Optional<UserDTO> findByUsername(String username) {
        return userRepository.findOne(Specification.where(byUsername(username)))
            .map(userMapper::primaryToSecondary);
    }

    @Override
    public UserDTO create(@Valid CreateUserVM newUser) {
        this.validateIfExistingUser(newUser.getUsername(), newUser.getPerson().getEmail());

        User userToSave = User.builder()
            .username(newUser.getUsername())
            .person(Person.builder()
                .firstName(newUser.getPerson().getFirstName())
                .lastName(newUser.getPerson().getLastName())
                .email(newUser.getPerson().getEmail())
                .build())
            .password(new BCryptPasswordEncoder().encode(Constants.DEFAULT_PASSWORD))
            .build();

        return userMapper.primaryToSecondary(userRepository.save(userToSave));
    }

    @Override
    public UserDTO update(@Valid UpdateUserVM existingUser) {

        this.validateIfExistingUserWithDifferentCode(existingUser.getUsername(),
            existingUser.getPerson().getEmail(), existingUser.getCode());

        Optional<User> userToUpdate = userRepository.findById(existingUser.getCode()).map(foundUser -> {
            foundUser.setUsername(existingUser.getUsername());
            foundUser.setPassword(new BCryptPasswordEncoder().encode(existingUser.getPassword()));
            foundUser.setActived(existingUser.isActived());

            Person userPerson = foundUser.getPerson();
            UpdatePersonVM existingUserPerson = existingUser.getPerson();
            userPerson.setFirstName(existingUserPerson.getFirstName());
            userPerson.setLastName(existingUserPerson.getLastName());
            userPerson.setEmail(existingUserPerson.getEmail());
            return foundUser;
        });
        return userToUpdate
            .map(userRepository::save)
            .map(userMapper::primaryToSecondary)
            .orElseThrow(RuntimeException::new);
    }

    @Override
    public void validateIfExistingUser(final String username, final String email) {
        Specification<User> conditions = Specification.where(byUsernameAndEmail(username, email));
        this.findAndthrowForExistingUser(conditions, username, email);
    }

    @Override
    public void validateIfExistingUserWithDifferentCode(final String username, final String email, final Integer code) {
        Specification<User> conditions = Specification
            .where(byUsernameAndEmail(username, email)
                .and(byDifferentCode(code)));
        this.findAndthrowForExistingUser(conditions, username, email);
    }

    private void findAndthrowForExistingUser(Specification<User> conditions, final String username, final String email) {
        userRepository.findOne(conditions).ifPresent(existingUser -> {
            if (username.equalsIgnoreCase(existingUser.getUsername()))
                throw new BadRequestException(USERNAME_ALREADY_IN_USE);
            else if (email.equalsIgnoreCase(existingUser.getPerson().getEmail()))
                throw new BadRequestException(EMAIL_ALREADY_IN_USE);
        });
    }

    @Override
    public void activeOrDeactive(final Integer code) {
        User existingUser = userRepository.findById(code)
            .orElseThrow(() -> new BadRequestException(USER_NOT_FOUND));
        existingUser.setActived(!existingUser.isActived());
        userRepository.save(existingUser);
    }
}
