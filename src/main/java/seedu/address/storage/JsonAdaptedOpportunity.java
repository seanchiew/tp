package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.opportunity.Company;
import seedu.address.model.opportunity.ContactRole;
import seedu.address.model.opportunity.Email;
import seedu.address.model.opportunity.Name;
import seedu.address.model.opportunity.Opportunity;
import seedu.address.model.opportunity.Phone;
import seedu.address.model.opportunity.Role;
import seedu.address.model.opportunity.Status;

/**
 * Jackson-friendly version of {@link Opportunity}.
 */
class JsonAdaptedOpportunity {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Opportunity's %s field is missing!";

    private final String name;
    private final String email;
    private final String contactRole;
    private final String company;
    private final String role;
    private final String status;
    private final Boolean isArchived;
    private final String phone;

    /**
     * Constructs a {@code JsonAdaptedOpportunity} with the given opportunity details.
     */
    @JsonCreator
    public JsonAdaptedOpportunity(@JsonProperty("name") String name,
                                  @JsonProperty("email") String email,
                                  @JsonProperty("contactRole") String contactRole,
                                  @JsonProperty("company") String company,
                                  @JsonProperty("role") String role,
                                  @JsonProperty("status") String status,
                                  @JsonProperty("isArchived") Boolean isArchived,
                                  @JsonProperty("phone") String phone) {
        this.name = name;
        this.email = email;
        this.contactRole = contactRole;
        this.company = company;
        this.role = role;
        this.status = status;
        this.isArchived = isArchived;
        this.phone = phone;
    }

    /**
     * Converts a given {@code Opportunity} into this class for Jackson use.
     */
    public JsonAdaptedOpportunity(Opportunity source) {
        name = source.getName().fullName;
        email = source.getEmail().value;
        contactRole = source.getContactRole().contactRoleName;
        company = source.getCompany().companyName;
        role = source.getRole().roleName;
        status = source.getStatus().statusName;
        isArchived = source.isArchived();
        phone = source.getPhone().map(p -> p.value).orElse(null);
    }

    /**
     * Converts this Jackson-friendly adapted opportunity object into the model's {@code Opportunity} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted opportunity.
     */
    public Opportunity toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (contactRole == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, ContactRole.class.getSimpleName()));
        }
        if (!ContactRole.isValidContactRole(contactRole)) {
            throw new IllegalValueException(ContactRole.MESSAGE_CONSTRAINTS);
        }
        final ContactRole modelContactRole = new ContactRole(contactRole);

        if (company == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Company.class.getSimpleName()));
        }
        if (!Company.isValidCompany(company)) {
            throw new IllegalValueException(Company.MESSAGE_CONSTRAINTS);
        }
        final Company modelCompany = new Company(company);

        if (role == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Role.class.getSimpleName()));
        }
        if (!Role.isValidRole(role)) {
            throw new IllegalValueException(Role.MESSAGE_CONSTRAINTS);
        }
        final Role modelRole = new Role(role);

        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName()));
        }
        if (!Status.isValidStatus(status)) {
            throw new IllegalValueException(Status.MESSAGE_CONSTRAINTS);
        }
        final Status modelStatus = new Status(status);

        final boolean modelIsArchived = (isArchived != null) ? isArchived : false;

        Phone modelPhone = null;
        if (phone != null) {
            if (!Phone.isValidPhone(phone)) {
                throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
            }
            modelPhone = new Phone(phone);
        }

        return new Opportunity(modelName, modelEmail, modelContactRole,
                modelCompany, modelRole, modelStatus, modelIsArchived, modelPhone);
    }

}
