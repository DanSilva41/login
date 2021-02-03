package br.com.felipe.login.web.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Danilo Silva
 */
@ApiModel(description = "Class representing a person")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO implements Serializable {

    private static final long serialVersionUID = -1954438790770869050L;

    @ApiModelProperty(notes = "The person code", example = "1", dataType = "integer")
    private Integer code;
    @ApiModelProperty(notes = "The person name", example = "First", dataType = "string", position = 1)
    private String firstName;
    @ApiModelProperty(notes = "The last name", example = "Last", dataType = "string", position = 2)
    private String lastName;
    @ApiModelProperty(notes = "The e-mail name", example = "first_last@gmail.com", dataType = "string", position = 3)
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
        PersonDTO that = (PersonDTO) o;
        return code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
