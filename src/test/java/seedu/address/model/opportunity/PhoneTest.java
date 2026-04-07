package seedu.address.model.opportunity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PhoneTest {

    @Test
    public void isValidPhone_validPhones_returnsTrue() {
        assertTrue(Phone.isValidPhone("91234567"));
        assertTrue(Phone.isValidPhone("+6591234567"));
        assertTrue(Phone.isValidPhone("+65 9123 4567"));
        assertTrue(Phone.isValidPhone("+1-800-555-0100"));
        assertTrue(Phone.isValidPhone("+1 (212) 555-0199"));
        assertTrue(Phone.isValidPhone("123"));
    }

    @Test
    public void isValidPhone_tooFewDigits_returnsFalse() {
        assertFalse(Phone.isValidPhone("12"));
        assertFalse(Phone.isValidPhone("+1"));
    }

    @Test
    public void isValidPhone_tooManyDigits_returnsFalse() {
        assertFalse(Phone.isValidPhone("1234567890123456")); // 16 digits
    }

    @Test
    public void isValidPhone_containsLetters_returnsFalse() {
        assertFalse(Phone.isValidPhone("+65 abc 1234"));
        assertFalse(Phone.isValidPhone("9abc1234"));
    }

    @Test
    public void constructor_separatorsPreserved() {
        assertEquals("+65 9123 4567", new Phone("+65 9123 4567").value);
        assertEquals("+1-800-555-0100", new Phone("+1-800-555-0100").value);
        assertEquals("+1 (212) 555-0199", new Phone("+1 (212) 555-0199").value);
    }

    @Test
    public void constructor_plainDigitsPreserved() {
        assertEquals("91234567", new Phone("91234567").value);
        assertEquals("+6591234567", new Phone("+6591234567").value);
    }
}
