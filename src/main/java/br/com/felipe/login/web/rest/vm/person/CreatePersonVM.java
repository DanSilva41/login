package br.com.felipe.login.web.rest.vm.person;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Danilo Silva
 */
@ApiModel(description = "Class representing a person to be created")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePersonVM implements Serializable {

    private static final long serialVersionUID = -6497430785991857855L;

    @ApiModelProperty(notes = "The firstname for the person of the new user", example = "First", dataType = "string", required = true)
    @NotBlank(message = "First name can not be empty")
    private String firstName;

    @ApiModelProperty(notes = "The lastname for the person of the new user", example = "Last", dataType = "string", required = true, position = 1)
    @NotBlank(message = "Last name can not be empty")
    private String lastName;

    @ApiModelProperty(notes = "The e-mail for the person of the new user", example = "first_last@gmail.com", dataType = "string", required = true, position = 2)
    @NotBlank(message = "E-mail can not be empty")
    private String email;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreatePersonVM that = (CreatePersonVM) o;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
