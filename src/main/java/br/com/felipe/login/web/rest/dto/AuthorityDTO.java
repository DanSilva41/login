package br.com.felipe.login.web.rest.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Danilo Silva
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityDTO implements Serializable {

    private static final long serialVersionUID = 890746060325165950L;

    private String name;
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
        AuthorityDTO that = (AuthorityDTO) o;
        return name.equalsIgnoreCase(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
