package br.com.felipe.login.repository.specifications;

import com.thesolution.projects.backend.auth.domain.Person_;
import br.com.felipe.login.domain.User;
import com.thesolution.projects.backend.auth.domain.User_;
import org.springframework.data.jpa.domain.Specification;


public class UserSpecification {

    private UserSpecification() {
    }

    public static Specification<User> byUsername(final String username) {
        return (root, criteriaQuery, criteriaBuilder) ->
            criteriaBuilder.equal(criteriaBuilder.lower(root.get(User_.USERNAME)), username.toLowerCase());
    }

    public static Specification<User> byUsernameAndEmail(final String username, final String email) {
        return (root, criteriaQuery, criteriaBuilder) ->
            criteriaBuilder.or(
                criteriaBuilder.equal(criteriaBuilder.lower(root.get(User_.USERNAME)), username.toLowerCase()),
                criteriaBuilder.equal(criteriaBuilder.lower(root.get(User_.PERSON).get(Person_.EMAIL)), email.toLowerCase())
            );
    }

    public static Specification<User> byDifferentCode(final Integer code) {
        return (root, criteriaQuery, criteriaBuilder) ->
            criteriaBuilder.notEqual(root.get(User_.CODE), code);
    }

}
