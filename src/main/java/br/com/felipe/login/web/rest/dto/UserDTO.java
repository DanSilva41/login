package br.com.felipe.login.web.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * @author Danilo Silva
 */
@ApiModel(description = "Class representing a user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

    private static final long serialVersionUID = -1954438790770869050L;

    @ApiModelProperty(notes = "The user code", example = "1", dataType = "integer", required = true)
    private Integer code;

    @ApiModelProperty(notes = "The user username", example = "example01", dataType = "string", required = true, position = 1)
    private String username;

    @ApiModelProperty(notes = "The user password", example = "p@ssw0rd", dataType = "string", required = true, position = 2)
    @JsonIgnore
    private String password;

    @ApiModelProperty(notes = "The status of the user", example = "true", dataType = "boolean", position = 3)
    private boolean actived;

    @ApiModelProperty(notes = "The user created date", example = "2020-12-08T20:19:50.389Z", dataType = "string", required = true, position = 4)
    private LocalDateTime createdDate;

    @ApiModelProperty(notes = "The user last modified date", example = "2020-12-08T20:19:50.389Z", dataType = "string", required = true, position = 5)
    private LocalDateTime lastModifiedDate;

    @ApiModelProperty(notes = "The personal data of the user", required = true, position = 6)
    private PersonDTO person;

    @ApiModelProperty(notes = "The set of applications of the user", required = true, position = 7)
    @JsonIgnore
    private Set<ApplicationsAuthoritiesDTO> setOfApplications;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public PersonDTO getPerson() {
        return person;
    }

    public void setPerson(PersonDTO person) {
        this.person = person;
    }

    public Set<ApplicationsAuthoritiesDTO> getSetOfApplications() {
        return setOfApplications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO that = (UserDTO) o;
        return code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
