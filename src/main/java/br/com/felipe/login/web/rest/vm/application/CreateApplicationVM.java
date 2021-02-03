package br.com.felipe.login.web.rest.vm.application;

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
@ApiModel(description = "Class representing a application to be created")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateApplicationVM implements Serializable {

    private static final long serialVersionUID = 3423512887642422576L;

    @ApiModelProperty(notes = "The name for new application", example = "application01", dataType = "string", required = true)
    @NotBlank(message = "Application name can not be empty")
    private String name;

    @ApiModelProperty(notes = "The description for new application", example = "some thing", dataType = "string", required = true, position = 1)
    @NotBlank(message = "Application description can not be empty")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateApplicationVM that = (CreateApplicationVM) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
