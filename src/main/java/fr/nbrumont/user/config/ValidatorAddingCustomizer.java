package fr.nbrumont.user.config;

import jakarta.validation.Validator;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * This component allows the usage of Spring's {@link org.springframework.beans.factory.annotation.Autowired} inside validators
 * in order for Hibernate to use Spring managed constraint validators
 *
 * @see <a href="https://stackoverflow.com/questions/56782292/configuring-spring-validation-and-hibernate-validation>this post talking about it</a>
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
