package br.com.felipe.login.domain;

import com.thesolution.structure.backend.common.abstracts.entity.AbstractAuditingEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Entity(name = "Application")
@Table(name = "application")
public final class Application implements Serializable {

    private static final long serialVersionUID = 8164104332571210803L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code", unique = true, nullable = false)
    private Integer code;

    @NotBlank
    @Size(min = 2, max = 60)
    @Column(name = "name", length = 60, unique = true, nullable = false)
    private String name;

    @NotBlank
    @Size(min = 5, max = 150)
    @Column(name = "description", length = 150, nullable = false)
    private String description;

    @NotNull
    @Column(name = "actived", nullable = false)
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
        Application that = (Application) o;
        return code.equals(that.code) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name);
    }
}

