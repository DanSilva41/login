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
@ApiModel(description = "Class representing a application")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDTO implements Serializable {

    private static final long serialVersionUID = -7705679367126948789L;

    @ApiModelProperty(notes = "The application code", example = "1", dataType = "integer")
    private Integer code;
    @ApiModelProperty(notes = "The application name", example = "application01", dataType = "string", position = 1)
    private String name;
    @ApiModelProperty(notes = "The application description", example = "1", dataType = "string", position = 2)
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
        ApplicationDTO that = (ApplicationDTO) o;
        return code.equals(that.code) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name);
    }
}
