package br.com.felipe.login.web.rest.vm.user;

import com.thesolution.structure.backend.commons.datatransfers.vm.person.UpdatePersonVM;
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
@ApiModel(description = "Class representing a user to be updated")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserVM implements Serializable {

    private static final long serialVersionUID = 5554688863225772179L;

    @ApiModelProperty(notes = "The user code to be updated", example = "1", dataType = "integer", required = true)
    @NotNull(message = "Code can not be empty")
    private Integer code;

    @ApiModelProperty(notes = "The user username to be updated", example = "example01", dataType = "string", required = true, position = 1)
    @NotBlank(message = "Username can not be empty")
    private String username;

    @ApiModelProperty(notes = "The user password to be updated", example = "p@ssw0rd", dataType = "string", required = true, position = 2)
    @NotBlank(message = "Password can not be empty")
    private String password;

    @ApiModelProperty(notes = "The status of the user to be updated", example = "true", dataType = "boolean", position = 4)
    private boolean actived;

    @ApiModelProperty(notes = "The personal data of the user to be updated", required = true, position = 5)
    @Valid
    @NotNull(message = "Personal data can not be empty")
    private UpdatePersonVM person;

    public Integer getCode() {
        return code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActived() {
        return actived;
    }

    public void setActived(boolean actived) {
        this.actived = actived;
    }

    public UpdatePersonVM getPerson() {
        return person;
    }

    public void setPerson(UpdatePersonVM person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateUserVM that = (UpdateUserVM) o;
        return code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
