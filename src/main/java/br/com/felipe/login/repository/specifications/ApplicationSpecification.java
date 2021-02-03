package br.com.felipe.login.repository.specifications;

import br.com.felipe.login.domain.Application;
import com.thesolution.projects.backend.auth.domain.Application_;
import org.springframework.data.jpa.domain.Specification;


public class ApplicationSpecification {

    private ApplicationSpecification() {
    }

    public static Specification<Application> byApplicationName(final String applicatinoName) {
        return (root, criteriaQuery, criteriaBuilder) ->
            criteriaBuilder.equal(
                criteriaBuilder.lower(root.get(Application_.NAME)), applicatinoName.toLowerCase());
    }

    public static Specification<Application> byDifferentCode(final Integer code) {
        return (root, criteriaQuery, criteriaBuilder) ->
            criteriaBuilder.notEqual(root.get(Application_.CODE), code);
    }

}
