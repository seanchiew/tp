package seedu.address.model.opportunity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ContactRoleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ContactRole(null));
    }

    @Test
    public void constructor_invalidContactRole_throwsIllegalArgumentException() {
        String invalidContactRole = "";
        assertThrows(IllegalArgumentException.class, () -> new ContactRole(invalidContactRole));
    }

    @Test
    public void isValidContactRole() {
        // null contact role
        assertThrows(NullPointerException.class, () -> ContactRole.isValidContactRole(null));

        // invalid contact roles
        assertFalse(ContactRole.isValidContactRole("")); // empty string
        assertFalse(ContactRole.isValidContactRole(" ")); // spaces only
        assertFalse(ContactRole.isValidContactRole("^")); // only non-alphanumeric characters
        assertFalse(ContactRole.isValidContactRole("recruiter*")); // contains non-allowed characters
        assertFalse(ContactRole.isValidContactRole("Tech/HR")); // contains forward slash
        assertFalse(ContactRole.isValidContactRole("HR / Recruiting")); // contains forward slash with spaces
        assertFalse(ContactRole.isValidContactRole("a".repeat(51))); // exceeds max length of 50
        assertFalse(ContactRole.isValidContactRole("@recruiter")); // starts with special character
        assertFalse(ContactRole.isValidContactRole(".recruiter")); // starts with period
        assertFalse(ContactRole.isValidContactRole("&recruiter")); // starts with ampersand

        // valid contact roles - basic
        assertTrue(ContactRole.isValidContactRole("recruiter")); // single word
        assertTrue(ContactRole.isValidContactRole("hiring manager")); // multiple words
        assertTrue(ContactRole.isValidContactRole("a")); // minimal length
        assertTrue(ContactRole.isValidContactRole("HR")); // abbreviation

        // valid contact roles - with already-supported punctuation
        assertTrue(ContactRole.isValidContactRole("Co-Founder")); // with hyphen
        assertTrue(ContactRole.isValidContactRole("HR-Manager")); // hyphenated role

        // valid contact roles - with newly-added punctuation (periods)
        assertTrue(ContactRole.isValidContactRole("Sr. Recruiter")); // period in title
        assertTrue(ContactRole.isValidContactRole("Jr. Manager")); // period in title
        assertTrue(ContactRole.isValidContactRole("V.P.")); // multiple periods (Vice President)
        assertTrue(ContactRole.isValidContactRole("Ph.D. Supervisor")); // period in qualification

        // valid contact roles - with newly-added punctuation (commas)
        assertTrue(ContactRole.isValidContactRole("VP, Engineering")); // comma separator
        assertTrue(ContactRole.isValidContactRole("Manager, HR")); // comma separator
        assertTrue(ContactRole.isValidContactRole("Director, R&D")); // comma with abbreviation

        // valid contact roles - with newly-added punctuation (parentheses)
        assertTrue(ContactRole.isValidContactRole("Hiring Manager (Tech)")); // specialization in parentheses
        assertTrue(ContactRole.isValidContactRole("Recruiter (Finance)")); // department in parentheses
        assertTrue(ContactRole.isValidContactRole("Manager (HR & Recruiting)")); // complex specialization

        // valid contact roles - with newly-added punctuation (ampersands)
        assertTrue(ContactRole.isValidContactRole("HR & Recruiting")); // compound role with ampersand
        assertTrue(ContactRole.isValidContactRole("Research & Development")); // R&D expanded
        assertTrue(ContactRole.isValidContactRole("Talent & Operations")); // compound departments

        // valid contact roles - with newly-added punctuation (apostrophes)
        assertTrue(ContactRole.isValidContactRole("Director's Assistant")); // possessive form
        assertTrue(ContactRole.isValidContactRole("CEO's Office")); // possessive with office
        assertTrue(ContactRole.isValidContactRole("Manager's Deputy")); // possessive deputy

        // valid contact roles - combined punctuation
        assertTrue(ContactRole.isValidContactRole("Sr. HR & Recruiting Manager")); // period and ampersand
        assertTrue(ContactRole.isValidContactRole("VP, Engineering & Operations")); // comma and ampersand
        assertTrue(ContactRole.isValidContactRole("Director, R&D (Tech)")); // comma, ampersand, parentheses
        assertTrue(ContactRole.isValidContactRole("Sr. Manager's Assistant (HR)")); // period, apostrophe, parentheses

        // valid contact roles - real-world examples
        assertTrue(ContactRole.isValidContactRole("Sr. HR & Talent Acquisition Manager")); // comprehensive example
        assertTrue(ContactRole.isValidContactRole("VP, Engineering (Mobile & Web)")); // complex structure
        assertTrue(ContactRole.isValidContactRole("Director's Office, R&D (AI)")); // very complex

        // valid contact roles - edge cases
        assertTrue(ContactRole.isValidContactRole("Sr.")); // title only
        assertTrue(ContactRole.isValidContactRole("R&D")); // abbreviation with ampersand
        assertTrue(ContactRole.isValidContactRole("a".repeat(50))); // exactly max length

        // valid contact roles - with numbers
        assertTrue(ContactRole.isValidContactRole("L5 Manager")); // level indicator
        assertTrue(ContactRole.isValidContactRole("Recruiter 2")); // numbered role
    }
}
