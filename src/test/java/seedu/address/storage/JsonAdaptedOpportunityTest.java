package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedOpportunity.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalOpportunities.BENSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.opportunity.Company;
import seedu.address.model.opportunity.ContactRole;
import seedu.address.model.opportunity.Cycle;
import seedu.address.model.opportunity.Email;
import seedu.address.model.opportunity.Name;
import seedu.address.model.opportunity.Role;
import seedu.address.model.opportunity.Status;

public class JsonAdaptedOpportunityTest {
    private static final String INVALID_NAME = " "; // Blank name
    private static final String INVALID_EMAIL = "notanemail"; // Not a valid email
    private static final String INVALID_CONTACT_ROLE = " "; // Blank
    private static final String INVALID_COMPANY = " "; // Blank company
    private static final String INVALID_ROLE = " "; // Blank role
    private static final String INVALID_STATUS = "UNKNOWN"; // Not a valid status
    private static final String INVALID_CYCLE = "Autumn 2025"; // Invalid Cycle

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_CONTACT_ROLE = BENSON.getContactRole().toString();
    private static final String VALID_COMPANY = BENSON.getCompany().toString();
    private static final String VALID_ROLE = BENSON.getRole().toString();
    private static final String VALID_STATUS = BENSON.getStatus().toString();
    private static final String VALID_CYCLE = BENSON.getCycle().toString();
    private static final String VALID_PHONE = BENSON.getPhone().map(Object::toString).orElse(null);

    @Test
    public void toModelType_validOpportunityDetails_returnsOpportunity() throws Exception {
        JsonAdaptedOpportunity opportunity = new JsonAdaptedOpportunity(BENSON);
        assertEquals(BENSON, opportunity.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedOpportunity opportunity = new JsonAdaptedOpportunity(
                INVALID_NAME, VALID_EMAIL, VALID_CONTACT_ROLE, VALID_COMPANY, VALID_ROLE,
                VALID_STATUS, VALID_CYCLE, false, VALID_PHONE);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, opportunity::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedOpportunity opportunity = new JsonAdaptedOpportunity(
                null, VALID_EMAIL, VALID_CONTACT_ROLE, VALID_COMPANY, VALID_ROLE,
                VALID_STATUS, VALID_CYCLE, false, VALID_PHONE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, opportunity::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedOpportunity opportunity = new JsonAdaptedOpportunity(
                VALID_NAME, INVALID_EMAIL, VALID_CONTACT_ROLE, VALID_COMPANY, VALID_ROLE,
                VALID_STATUS, VALID_CYCLE, false, VALID_PHONE);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, opportunity::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedOpportunity opportunity = new JsonAdaptedOpportunity(
                VALID_NAME, null, VALID_CONTACT_ROLE, VALID_COMPANY, VALID_ROLE,
                VALID_STATUS, VALID_CYCLE, false, VALID_PHONE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, opportunity::toModelType);
    }

    @Test
    public void toModelType_invalidContactRole_throwsIllegalValueException() {
        JsonAdaptedOpportunity opportunity = new JsonAdaptedOpportunity(
                VALID_NAME, VALID_EMAIL, INVALID_CONTACT_ROLE, VALID_COMPANY, VALID_ROLE,
                VALID_STATUS, VALID_CYCLE, false, VALID_PHONE);
        String expectedMessage = ContactRole.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, opportunity::toModelType);
    }

    @Test
    public void toModelType_nullContactRole_throwsIllegalValueException() {
        JsonAdaptedOpportunity opportunity = new JsonAdaptedOpportunity(
                VALID_NAME, VALID_EMAIL, null, VALID_COMPANY, VALID_ROLE,
                VALID_STATUS, VALID_CYCLE, false, VALID_PHONE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ContactRole.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, opportunity::toModelType);
    }

    @Test
    public void toModelType_invalidCompany_throwsIllegalValueException() {
        JsonAdaptedOpportunity opportunity = new JsonAdaptedOpportunity(
                VALID_NAME, VALID_EMAIL, VALID_CONTACT_ROLE, INVALID_COMPANY, VALID_ROLE,
                VALID_STATUS, VALID_CYCLE, false, VALID_PHONE);
        String expectedMessage = Company.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, opportunity::toModelType);
    }

    @Test
    public void toModelType_nullCompany_throwsIllegalValueException() {
        JsonAdaptedOpportunity opportunity = new JsonAdaptedOpportunity(
                VALID_NAME, VALID_EMAIL, VALID_CONTACT_ROLE, null, VALID_ROLE,
                VALID_STATUS, VALID_CYCLE, false, VALID_PHONE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Company.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, opportunity::toModelType);
    }

    @Test
    public void toModelType_invalidRole_throwsIllegalValueException() {
        JsonAdaptedOpportunity opportunity = new JsonAdaptedOpportunity(
                VALID_NAME, VALID_EMAIL, VALID_CONTACT_ROLE, VALID_COMPANY, INVALID_ROLE,
                VALID_STATUS, VALID_CYCLE, false, VALID_PHONE);
        String expectedMessage = Role.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, opportunity::toModelType);
    }

    @Test
    public void toModelType_nullRole_throwsIllegalValueException() {
        JsonAdaptedOpportunity opportunity = new JsonAdaptedOpportunity(
                VALID_NAME, VALID_EMAIL, VALID_CONTACT_ROLE, VALID_COMPANY, null,
                VALID_STATUS, VALID_CYCLE, false, VALID_PHONE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Role.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, opportunity::toModelType);
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        JsonAdaptedOpportunity opportunity = new JsonAdaptedOpportunity(
                VALID_NAME, VALID_EMAIL, VALID_CONTACT_ROLE, VALID_COMPANY, VALID_ROLE,
                INVALID_STATUS, VALID_CYCLE, false, VALID_PHONE);
        String expectedMessage = Status.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, opportunity::toModelType);
    }

    @Test
    public void toModelType_nullStatus_throwsIllegalValueException() {
        JsonAdaptedOpportunity opportunity = new JsonAdaptedOpportunity(
                VALID_NAME, VALID_EMAIL, VALID_CONTACT_ROLE, VALID_COMPANY, VALID_ROLE,
                null, VALID_CYCLE, false, VALID_PHONE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, opportunity::toModelType);
    }

    @Test
    public void toModelType_invalidCycle_throwsIllegalValueException() {
        JsonAdaptedOpportunity opportunity = new JsonAdaptedOpportunity(
                VALID_NAME, VALID_EMAIL, VALID_CONTACT_ROLE, VALID_COMPANY, VALID_ROLE,
                VALID_STATUS, INVALID_CYCLE, false, VALID_PHONE);
        String expectedMessage = Cycle.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, opportunity::toModelType);
    }

    @Test
    public void toModelType_nullCycle_throwsIllegalValueException() {
        JsonAdaptedOpportunity opportunity = new JsonAdaptedOpportunity(
                VALID_NAME, VALID_EMAIL, VALID_CONTACT_ROLE, VALID_COMPANY, VALID_ROLE,
                VALID_STATUS, null, false, VALID_PHONE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Cycle.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, opportunity::toModelType);
    }


    @Test
    public void toModelType_noPhone_returnsOpportunityWithoutPhone() throws Exception {
        JsonAdaptedOpportunity opportunity = new JsonAdaptedOpportunity(
                VALID_NAME, VALID_EMAIL, VALID_CONTACT_ROLE, VALID_COMPANY, VALID_ROLE,
                VALID_STATUS, VALID_CYCLE, false, null);
        assertEquals(false, opportunity.toModelType().getPhone().isPresent());
    }
}
