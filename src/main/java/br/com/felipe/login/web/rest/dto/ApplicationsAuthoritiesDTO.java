package br.com.felipe.login.web.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * @author Danilo Silva
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationsAuthoritiesDTO implements Serializable {

    private static final long serialVersionUID = 2590151375886915801L;

    private ApplicationDTO application;
    private Set<AuthorityDTO> authorities;

    public ApplicationDTO getApplication() {
        return application;
    }

    public void setApplication(ApplicationDTO application) {
        this.application = application;
    }

    public Set<AuthorityDTO> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<AuthorityDTO> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationsAuthoritiesDTO that = (ApplicationsAuthoritiesDTO) o;
        return application.equals(that.application);
    }

    @Override
    public int hashCode() {
        return Objects.hash(application);
    }
}
