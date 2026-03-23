package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_OPPORTUNITY;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.opportunity.Company;
import seedu.address.model.opportunity.Cycle;
import seedu.address.model.opportunity.Role;

public class ParserUtilTest {
    private static final String INVALID_COMPANY = " "; // Blank strings that violate 1-60 char spec
    private static final String INVALID_ROLE = " "; // Blank strings that violate 1-80 char spec

    private static final String VALID_COMPANY = "Stripe";
    private static final String VALID_ROLE = "SWE Intern";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_OPPORTUNITY, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_OPPORTUNITY, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseCompany_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCompany(null));
    }

    @Test
    public void parseCompany_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseCompany(INVALID_COMPANY));
    }

    @Test
    public void parseCompany_validValueWithoutWhitespace_returnsCompany() throws Exception {
        Company expectedCompany = new Company(VALID_COMPANY);
        assertEquals(expectedCompany, ParserUtil.parseCompany(VALID_COMPANY));
    }

    @Test
    public void parseCompany_validValueWithWhitespace_returnsTrimmedCompany() throws Exception {
        String companyWithWhitespace = WHITESPACE + VALID_COMPANY + WHITESPACE;
        Company expectedCompany = new Company(VALID_COMPANY);
        assertEquals(expectedCompany, ParserUtil.parseCompany(companyWithWhitespace));
    }

    @Test
    public void parseRole_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRole(null));
    }

    @Test
    public void parseRole_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRole(INVALID_ROLE));
    }

    @Test
    public void parseRole_validValueWithoutWhitespace_returnsRole() throws Exception {
        Role expectedRole = new Role(VALID_ROLE);
        assertEquals(expectedRole, ParserUtil.parseRole(VALID_ROLE));
    }

    @Test
    public void parseRole_validValueWithWhitespace_returnsTrimmedRole() throws Exception {
        String roleWithWhitespace = WHITESPACE + VALID_ROLE + WHITESPACE;
        Role expectedRole = new Role(VALID_ROLE);
        assertEquals(expectedRole, ParserUtil.parseRole(roleWithWhitespace));
    }

    @Test
    public void parseCycle_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCycle(null));
    }

    @Test
    public void parseCycle_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseCycle("Autumn 2025"));
    }

    @Test
    public void parseCycle_validValueWithoutWhitespace_returnsCycle() throws Exception {
        Cycle expectedCycle = new Cycle("SUMMER 2025");
        assertEquals(expectedCycle, ParserUtil.parseCycle("SUMMER 2025"));
    }

    @Test
    public void parseCycle_validValueWithWhitespace_returnsTrimmedCycle() throws Exception {
        String cycleWithWhitespace = "  WINTER 2025  ";
        Cycle expectedCycle = new Cycle("WINTER 2025");
        assertEquals(expectedCycle, ParserUtil.parseCycle(cycleWithWhitespace));
    }

    @Test
    public void parseCycle_validAlias_returnsNormalizedCycle() throws Exception {
        Cycle expectedCycle = new Cycle("S2 2025");
        assertEquals(expectedCycle, ParserUtil.parseCycle("sem 2 2025"));
        assertEquals(expectedCycle, ParserUtil.parseCycle("semester 2 2025"));
    }

    @Test
    public void parseCycle_irregularWhitespaceAndAlias_returnsNormalizedCycle() throws Exception {
        Cycle expectedCycle = new Cycle("S2 2025");
        // Tests multiple internal spaces
        assertEquals(expectedCycle, ParserUtil.parseCycle("sem   2    2025"));
        // Tests missing internal space in the alias
        assertEquals(expectedCycle, ParserUtil.parseCycle("semester2 2025"));
    }

}
