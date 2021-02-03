package br.com.felipe.login.web.rest.vm.user;

import com.thesolution.structure.backend.commons.datatransfers.vm.person.CreatePersonVM;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Danilo Silva
 */
@ApiModel(description = "Class representing a user to be created")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserVM implements Serializable {

    private static final long serialVersionUID = 5554688863225772179L;

    @ApiModelProperty(notes = "The username for new user", example = "example01", dataType = "string", required = true)
    @NotBlank(message = "Username can not be empty")
    private String username;

    @ApiModelProperty(notes = "The personal data for new user", required = true, position = 1)
    @Valid
    @NotNull(message = "Personal data can not be empty")
    private CreatePersonVM person;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public CreatePersonVM getPerson() {
        return person;
    }

    public void setPerson(CreatePersonVM person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateUserVM that = (CreateUserVM) o;
        return username.equals(that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
