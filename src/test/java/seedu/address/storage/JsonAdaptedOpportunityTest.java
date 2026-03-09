package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedOpportunity.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalOpportunities.BENSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.opportunity.Company;
import seedu.address.model.opportunity.Role;

public class JsonAdaptedOpportunityTest {
    private static final String INVALID_COMPANY = " "; // Blank company
    private static final String INVALID_ROLE = " "; // Blank role

    private static final String VALID_COMPANY = BENSON.getCompany().toString();
    private static final String VALID_ROLE = BENSON.getRole().toString();

    @Test
    public void toModelType_validOpportunityDetails_returnsOpportunity() throws Exception {
        JsonAdaptedOpportunity opportunity = new JsonAdaptedOpportunity(BENSON);
        assertEquals(BENSON, opportunity.toModelType());
    }

    @Test
    public void toModelType_invalidCompany_throwsIllegalValueException() {
        JsonAdaptedOpportunity opportunity =
                new JsonAdaptedOpportunity(INVALID_COMPANY, VALID_ROLE);
        String expectedMessage = Company.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, opportunity::toModelType);
    }

    @Test
    public void toModelType_nullCompany_throwsIllegalValueException() {
        JsonAdaptedOpportunity opportunity = new JsonAdaptedOpportunity(null, VALID_ROLE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Company.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, opportunity::toModelType);
    }

    @Test
    public void toModelType_invalidRole_throwsIllegalValueException() {
        JsonAdaptedOpportunity opportunity = new JsonAdaptedOpportunity(VALID_COMPANY, INVALID_ROLE);
        String expectedMessage = Role.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, opportunity::toModelType);
    }

    @Test
    public void toModelType_nullRole_throwsIllegalValueException() {
        JsonAdaptedOpportunity opportunity = new JsonAdaptedOpportunity(VALID_COMPANY, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Role.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, opportunity::toModelType);
    }
}
