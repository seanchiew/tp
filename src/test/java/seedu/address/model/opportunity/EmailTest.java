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
    }

    @Test
    public void isValidEmail_underscoreAtBoundaries_returnsTrue() {
        assertTrue(Email.isValidEmail("_user@example.com"));
        assertTrue(Email.isValidEmail("user_@example.com"));
        assertTrue(Email.isValidEmail("user_name@example.com"));
    }

    @Test
    public void isValidEmail_consecutiveDots_returnsFalse() {
        assertFalse(Email.isValidEmail("user..name@example.com"));
        assertFalse(Email.isValidEmail("a..b@example.com"));
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

    @Test
    public void isValidEmail_domainWithUnderscore_returnsFalse() {
        assertFalse(Email.isValidEmail("john@foo_bar.com"));
        assertFalse(Email.isValidEmail("john@_foo.com"));
        assertFalse(Email.isValidEmail("john@foo_.com"));
    }

    @Test
    public void isValidEmail_domainWithLeadingOrTrailingHyphen_returnsFalse() {
        assertFalse(Email.isValidEmail("john@-foo.com"));
        assertFalse(Email.isValidEmail("john@foo-.com"));
        assertFalse(Email.isValidEmail("john@foo.-bar.com"));
        assertFalse(Email.isValidEmail("john@foo.bar-.com"));
    }

    @Test
    public void isValidEmail_domainWithHyphenInMiddle_returnsTrue() {
        assertTrue(Email.isValidEmail("john@foo-bar.com"));
        assertTrue(Email.isValidEmail("john@my-company.co.uk"));
    }

    @Test
    public void isValidEmail_exceedsMaxLength_returnsFalse() {
        // 255-character email: local part padded to push total over 254
        String longLocal = "a".repeat(243);
        assertFalse(Email.isValidEmail(longLocal + "@example.com")); // 255 chars total
    }

    @Test
    public void isValidEmail_exactlyMaxLength_returnsTrue() {
        // 254-character email
        String longLocal = "a".repeat(242);
        assertTrue(Email.isValidEmail(longLocal + "@example.com")); // 254 chars total
    }
}
