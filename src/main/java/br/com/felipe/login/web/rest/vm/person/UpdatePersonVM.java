package br.com.felipe.login.web.rest.vm.person;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Danilo Silva
 */
@ApiModel(description = "Class representing a person to be updated")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePersonVM implements Serializable {

    private static final long serialVersionUID = -8296966468623056346L;

    @ApiModelProperty(notes = "The person code of the user to be updated", example = "1", dataType = "integer", required = true)
    @NotNull(message = "Code can not be empty")
    private Integer code;

    @ApiModelProperty(notes = "The person firstname of the user to be updated", example = "First", dataType = "string", required = true, position = 1)
    @NotBlank(message = "First name can not be empty")
    private String firstName;

    @ApiModelProperty(notes = "The person lastname of the user to be updated", example = "Last", dataType = "string", required = true, position = 2)
    @NotBlank(message = "Last name can not be empty")
    private String lastName;

    @ApiModelProperty(notes = "The person e-mail of the user to be updated", example = "first_last@gmail.com", dataType = "string", required = true, position = 3)
    @NotBlank(message = "E-mail can not be empty")
    private String email;

    public Integer getCode() {
        return code;
    }

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
        UpdatePersonVM that = (UpdatePersonVM) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
