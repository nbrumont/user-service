package fr.nbrumont.user.config;

import jakarta.validation.Validator;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Had to use this to be able to inject Clock into UserIsAdultValidator
 * in order for Hibernate to use Spring managed constraint validators
 * <p>
 * see <a href="https://stackoverflow.com/questions/56782292/configuring-spring-validation-and-hibernate-validation">...</a>
 */
@Component
public class ValidatorAddingCustomizer implements HibernatePropertiesCustomizer {

    private final ObjectProvider<Validator> provider;

    public ValidatorAddingCustomizer(ObjectProvider<Validator> provider) {
        this.provider = provider;
    }

    public void customize(Map<String, Object> hibernateProperties) {
        Validator validator = provider.getIfUnique();
        if (validator != null) {
            hibernateProperties.put("javax.persistence.validation.factory", validator);
        }
    }
}
