package br.com.felipe.login.web.rest.vm.application;

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
@ApiModel(description = "Class representing a application to be updated")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateApplicationVM implements Serializable {

    private static final long serialVersionUID = 1579513086699932225L;

    @ApiModelProperty(notes = "The application code to be updated", example = "1", dataType = "integer", required = true)
    @NotNull(message = "Application code can not be empty")
    private Integer code;

    @ApiModelProperty(notes = "The application name to be updated", example = "application01", dataType = "string", required = true, position = 1)
    @NotBlank(message = "Application name can not be empty")
    private String name;

    @ApiModelProperty(notes = "The application description to be updated", example = "some thing", dataType = "string", required = true, position = 2)
    @NotBlank(message = "Application description can not be empty")
    private String description;

    @ApiModelProperty(notes = "The status of the application to be updated", example = "true", dataType = "boolean", position = 3)
    private boolean actived;

    public Integer getCode() {
        return code;
    }

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

    public boolean isActived() {
        return actived;
    }

    public void setActived(boolean actived) {
        this.actived = actived;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateApplicationVM that = (UpdateApplicationVM) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
