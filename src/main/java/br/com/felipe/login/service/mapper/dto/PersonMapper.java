package br.com.felipe.login.service.mapper.dto;

import br.com.felipe.login.domain.Person;
import com.thesolution.structure.backend.common.abstracts.mapper.AbstractMapper;
import com.thesolution.structure.backend.commons.datatransfers.dto.PersonDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class PersonMapper implements AbstractMapper<Person, PersonDTO> {

    @Override
    public PersonDTO primaryToSecondary(Person person) {
        return Objects.isNull(person) ? null :
            PersonDTO.builder()
                .code(person.getCode())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .email(person.getEmail())
                .build();
    }

    @Override
    public Person secondaryToPrimary(PersonDTO personDTO) {
        return Objects.isNull(personDTO) ? null :
            Person.builder()
                .code(personDTO.getCode())
                .firstName(personDTO.getFirstName())
                .lastName(personDTO.getLastName())
                .email(personDTO.getEmail())
                .build();
    }
}
