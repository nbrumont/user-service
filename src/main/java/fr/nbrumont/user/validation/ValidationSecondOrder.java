package fr.nbrumont.user.validation;

/**
 * Interface created as a way to control the order of validation on different beans
 * so that we can first send error messages regarding standard technical validation such as "not null" or "not empty"
 * and then send fonctional error messages
 */
public interface ValidationSecondOrder {
}

