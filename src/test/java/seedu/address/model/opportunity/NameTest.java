package seedu.address.model.opportunity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid names
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("peter*")); // contains non-allowed characters
        assertFalse(Name.isValidName("John/Doe")); // contains forward slash
        assertFalse(Name.isValidName("John / Doe")); // contains forward slash with spaces
        assertFalse(Name.isValidName("SWE/ML")); // forward slash (role-like string)
        assertFalse(Name.isValidName("a".repeat(61))); // exceeds max length of 60
        assertFalse(Name.isValidName("@John")); // starts with special character
        assertFalse(Name.isValidName("1John")); // starts with digit
        assertFalse(Name.isValidName(".John")); // starts with period
        assertFalse(Name.isValidName("(John)")); // starts with parenthesis

        // valid names - basic
        assertTrue(Name.isValidName("peter jack")); // alphabets only
        assertTrue(Name.isValidName("Peter")); // single word
        assertTrue(Name.isValidName("a")); // minimal length

        // valid names - with already-supported punctuation
        assertTrue(Name.isValidName("Peter the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr")); // long names
        assertTrue(Name.isValidName("O'Brien")); // with apostrophe
        assertTrue(Name.isValidName("Jean-Paul")); // with hyphen
        assertTrue(Name.isValidName("O'Connor-Smith")); // with apostrophe and hyphen

        // valid names - with newly-added punctuation (periods)
        assertTrue(Name.isValidName("Mr. Smith")); // period in title
        assertTrue(Name.isValidName("Dr. John")); // period in title
        assertTrue(Name.isValidName("John Smith Jr.")); // period in suffix
        assertTrue(Name.isValidName("Ph.D.")); // multiple periods
        assertTrue(Name.isValidName("A.")); // single char with period

        // valid names - with newly-added punctuation (commas)
        assertTrue(Name.isValidName("John Doe, Jr.")); // comma in suffix
        assertTrue(Name.isValidName("Smith, John")); // comma separating parts
        assertTrue(Name.isValidName("Name, Name")); // multiple commas

        // valid names - with newly-added punctuation (parentheses)
        assertTrue(Name.isValidName("Mary (Mei Ling)")); // alternate name in parentheses
        assertTrue(Name.isValidName("John (Jr)")); // suffix in parentheses
        assertTrue(Name.isValidName("李明 (Li Ming)")); // Unicode with parentheses

        // valid names - combined punctuation
        assertTrue(Name.isValidName("Dr. O'Brien-Smith, Jr.")); // combination of all allowed punctuation
        assertTrue(Name.isValidName("Mr. Jean-Paul D'Angelo (PhD)")); // complex real-world example
        assertTrue(Name.isValidName("Dr. Mary-Anne O'Connor, Ph.D.")); // another complex example

        // valid names - edge cases
        assertTrue(Name.isValidName("Jr.")); // title/suffix only
        assertTrue(Name.isValidName("Mary-Anne")); // starts with allowed character
        assertTrue(Name.isValidName("a".repeat(60))); // exactly max length

        // valid names - Unicode support (already supported, confirming still works)
        assertTrue(Name.isValidName("李明")); // Chinese characters
        assertTrue(Name.isValidName("Müller")); // German umlaut
        assertTrue(Name.isValidName("José")); // Spanish accented character
        assertTrue(Name.isValidName("Владимир")); // Cyrillic characters
    }
}
