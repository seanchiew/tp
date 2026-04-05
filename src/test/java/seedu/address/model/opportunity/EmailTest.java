package seedu.address.model.opportunity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class EmailTest {

    @Test
    public void isValidEmail_validEmails_returnsTrue() {
        assertTrue(Email.isValidEmail("a@b.com"));
        assertTrue(Email.isValidEmail("user@example.com"));
        assertTrue(Email.isValidEmail("user.name@example.com"));
        assertTrue(Email.isValidEmail("user+tag@example.com"));
        assertTrue(Email.isValidEmail("user-name@example.com"));
        assertTrue(Email.isValidEmail("user_name@example.com"));
        assertTrue(Email.isValidEmail("u@domain.co.uk"));
    }

    @Test
    public void isValidEmail_localPartEndingWithSpecialChar_returnsFalse() {
        assertFalse(Email.isValidEmail("user.@example.com"));
        assertFalse(Email.isValidEmail("user+@example.com"));
        assertFalse(Email.isValidEmail("user-@example.com"));
    }

    @Test
    public void isValidEmail_localPartStartingWithSpecialChar_returnsFalse() {
        assertFalse(Email.isValidEmail(".user@example.com"));
        assertFalse(Email.isValidEmail("+user@example.com"));
        assertFalse(Email.isValidEmail("-user@example.com"));
        assertFalse(Email.isValidEmail("_user@example.com"));
    }

    @Test
    public void isValidEmail_localPartEndingWithUnderscore_returnsFalse() {
        assertFalse(Email.isValidEmail("user_@example.com"));
        assertFalse(Email.isValidEmail("jane_@gmail.com"));
    }

    @Test
    public void isValidEmail_underscoreInMiddleOfLocalPart_returnsTrue() {
        assertTrue(Email.isValidEmail("user_name@example.com"));
        assertTrue(Email.isValidEmail("a_b@example.com"));
    }

    @Test
    public void isValidEmail_missingAtSign_returnsFalse() {
        assertFalse(Email.isValidEmail("userexample.com"));
    }

    @Test
    public void isValidEmail_missingDomain_returnsFalse() {
        assertFalse(Email.isValidEmail("user@"));
        assertFalse(Email.isValidEmail("user@com"));
    }

    @Test
    public void isValidEmail_emptyString_returnsFalse() {
        assertFalse(Email.isValidEmail(""));
    }
}
