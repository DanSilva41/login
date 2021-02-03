package br.com.felipe.login.web.rest;

import br.com.felipe.login.security.permissions.AdminRead;
import br.com.felipe.login.security.permissions.AdminWrite;
import br.com.felipe.login.service.UserService;
import br.com.felipe.login.support.annotation.Documentation;
import br.com.felipe.login.support.enums.Author;
import br.com.felipe.login.web.rest.dto.UserDTO;
import br.com.felipe.login.web.rest.vm.user.CreateUserVM;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Validated
@Api(value = "user-resource", tags = {"user"})
@SwaggerDefinition(tags = {
    @Tag(name = "user", description = "REST Resource to manage users")
})
@RestController
@RequestMapping("/user")
public class UserResource {

    private final UserService userService;
    private final Environment env;

    /**
     * GET  /user : search all users
     *
     * @return status 200 (OK) and the list of all users
     */
    @Documentation(author = Author.danilosilvap, date = "2020-12-23")
    @ApiOperation(value = "${api.user-resource.findAll}", tags = {"user"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Users found", response = Collection.class),
        @ApiResponse(code = 404, message = "No users found"),
    })
    @AdminRead
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<UserDTO>> findAll() {
        log.info("{}", env.getProperty("api.user-resource.findAll"));
        Collection<UserDTO> allUsers = this.userService.findAll();
        log.error("{} users were found", allUsers.size());

        if (allUsers.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(allUsers);
    }

    /**
     * GET  /user/{username} : search user by username
     *
     * @param username : the username to be search
     * @return status 200 (OK) and returns the found user
     */
    @Documentation(author = Author.danilosilvap, date = "2020-12-23")
    @ApiOperation(value = "GET request to search a user by username", tags = {"user"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "User found", response = Collection.class),
        @ApiResponse(code = 404, message = "No users found with this username"),
    })
    @AdminRead
    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> findByUsername(@ApiParam(name = "username", value = "The username of user to be found",
        example = "example01", type = "string") @PathVariable("username") @Size(min = 5, max = 20) String username) {
        log.info("GET request to search a user by username: {}", username);
        Optional<UserDTO> userReturned = this.userService.findByUsername(username);

        return userReturned.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * POST  /user : creates a new user
     *
     * @param newUser : the user to be created
     * @return ResponseEntity with status 200 (ok) and with the body of the new user
     */
    @Documentation(author = Author.danilosilvap, date = "2020-12-23")
    @ApiOperation(value = "POST request to create new user", tags = {"user"},
        consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Created user", response = UserDTO.class)
    })
    @AdminWrite
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> create(@ApiParam(name = "new user", value = "New user to be created")
                                          @RequestBody @Valid CreateUserVM newUser) {
        log.info("POST request to create new user: {}", newUser);
        UserDTO userSaved = this.userService.create(newUser);
        return ResponseEntity.ok(userSaved);
    }

    /**
     * PUT  /user : update a user
     *
     * @param existingUser : the user to be updated
     * @return ResponseEntity with status 200 (ok) and with the body of the user
     */
    @ApiOperation(value = "PUT request to update a user", tags = {"user"},
        consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Updated user", response = UserDTO.class)
    })
    @AdminWrite
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> update(@ApiParam(name = "existing user", value = "The user to be updated") @RequestBody @Valid UpdateUserVM existingUser) {
        log.info("PUT request to update a user: {}", existingUser.getCode());
        UserDTO userSaved = this.userService.update(existingUser);
        return ResponseEntity.ok(userSaved);
    }


    /**
     * PUT  /user/{code} : active or deactive a user
     *
     * @param code : the code of user to be actived
     * @return ResponseEntity with status 200 (ok)
     */
    @Documentation(author = Author.danilosilvap, date = "2020-12-23")
    @ApiOperation(value = "PUT request active or deactive a user", tags = {"user"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Updated user")
    })
    @AdminWrite
    @PutMapping(value = "/{code}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> activeOrDeactive(@ApiParam(name = "code", value = "The code of user to be actived or deactived",
        example = "1", type = "integer") @PathVariable("code") Integer code) {
        log.info("PUT request to active or deactive a user: {}", code);
        this.userService.activeOrDeactive(code);
        return ResponseEntity.ok().build();
    }
}
