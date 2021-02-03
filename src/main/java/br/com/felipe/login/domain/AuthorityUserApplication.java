package br.com.felipe.login.domain;

import com.thesolution.structure.backend.common.abstracts.entity.AbstractAuditingEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@AssociationOverrides({
    @AssociationOverride(name = "id.authority", joinColumns = @JoinColumn(name = "authority_name", insertable = false, updatable = false)),
    @AssociationOverride(name = "id.user", joinColumns = @JoinColumn(name = "user_code", insertable = false, updatable = false)),
    @AssociationOverride(name = "id.application", joinColumns = @JoinColumn(name = "application_code", insertable = false, updatable = false))})
@Entity(name = "security.AuthorityUserApplication")
@Table(name = "authority_user_application", schema = "security")
public final class AuthorityUserApplication extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 5556537872241297803L;

    @EmbeddedId
    private AuthorityUserApplicationID id = new AuthorityUserApplicationID();

    @Transient
    public Application getApplication() {
        return getId().getApplication();
    }

    @Transient
    public Authority getAuthority() {
        return getId().getAuthority();
    }

    public AuthorityUserApplicationID getId() {
        return id;
    }

    public void setId(AuthorityUserApplicationID id) {
        this.id = id;
    }
}
