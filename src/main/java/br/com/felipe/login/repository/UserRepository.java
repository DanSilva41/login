package br.com.felipe.login.repository;

import br.com.felipe.login.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository(value = "UserRepository")
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

}
