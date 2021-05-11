package fr.esgi.baldwarsapi.exposition.authentication;

public enum RegistrationPropertiesLength {
    USERNAME_MIN_LENGTH(3),
    USERNAME_MAX_LENGTH(30),
    FIRSTNAME_MIN_LENGTH(2),
    FIRSTNAME_MAX_LENGTH(51),
    LASTNAME_MIN_LENGTH(2),
    LASTNAME_MAX_LENGTH(51),
    EMAIL_MIN_LENGTH(3),
    EMAIL_MAX_LENGTH(320),
    EMAIL_NAME_MIN_LENGTH(1),
    EMAIL_NAME_MAX_LENGTH(64),
    EMAIL_DOMAIN_MIN_LENGTH(1),
    EMAIL_DOMAIN_MAX_LENGTH(255),
    PASSWORD_MIN_LENGTH(8),
    PASSWORD_MAX_LENGTH(256);

    public final Integer value;

    RegistrationPropertiesLength(Integer value) {
        this.value = value;
    }
}
