package br.com.felipe.login.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thesolution.structure.backend.common.abstracts.entity.AbstractAuditingEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@DynamicUpdate
@Entity(name = "security.User")
@Table(schema = "security", name = "user")
public class User extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 7256255256895139381L;

    @Id
    @SequenceGenerator(schema = "security", name = "user_code_seq", sequenceName = "user_code_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_code_seq")
    @Column(name = "code", unique = true, nullable = false)
    private Integer code;

    @NotBlank
    @Size(min = 5, max = 20)
    @Column(name = "username", length = 20, unique = true, nullable = false)
    private String username;

    @JsonIgnore
    @NotBlank
    @Size(min = 6, max = 60)
    @Column(name = "password", length = 60, nullable = false)
    private String password;

    @NotNull
    @Column(name = "actived", nullable = false)
    private boolean actived;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id.user")
    private Set<AuthorityUserApplication> aplicacoesPerfis;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "person_code", nullable = false, unique = true)
    private Person person;

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

    public Set<AuthorityUserApplication> getAplicacoesPerfis() {
        return aplicacoesPerfis;
    }

    public Person getPerson() {
        return person;
    }
}

