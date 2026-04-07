package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.opportunity.Company;
import seedu.address.model.opportunity.ContactRole;
import seedu.address.model.opportunity.Cycle;
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
    private final String cycle;
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
                                  @JsonProperty("cycle") String cycle,
                                  @JsonProperty("isArchived") Boolean isArchived,
                                  @JsonProperty("phone") String phone) {
        this.name = name;
        this.email = email;
        this.contactRole = contactRole;
        this.company = company;
        this.role = role;
        this.status = status;
        this.cycle = cycle;
        this.isArchived = isArchived;
        this.phone = phone;
    }

    /**
     * Converts a given {@code Opportunity} into this class for Jackson use.
     */
    public JsonAdaptedOpportunity(Opportunity source) {
        name = source.getName().getFullName();
        email = source.getEmail().getValue();
        contactRole = source.getContactRole().getContactRoleName();
        company = source.getCompany().getCompanyName();
        role = source.getRole().getRoleName();
        status = source.getStatus().getStatusName();
        cycle = source.getCycle().getValue();
        isArchived = source.isArchived();
        phone = source.getPhone().map(p -> p.getValue()).orElse(null);
    }

    /**
     * Converts this Jackson-friendly adapted opportunity object into the model's {@code Opportunity} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted opportunity.
     */
    public Opportunity toModelType() throws IllegalValueException {
        final Name modelName = toModelName();
        final Email modelEmail = toModelEmail();
        final ContactRole modelContactRole = toModelContactRole();
        final Company modelCompany = toModelCompany();
        final Role modelRole = toModelRole();
        final Status modelStatus = toModelStatus();
        final Cycle modelCycle = toModelCycle();
        final boolean modelIsArchived = (isArchived != null) ? isArchived : false;
        final Phone modelPhone = toModelPhone();

        return new Opportunity(modelName, modelEmail, modelContactRole,
                modelCompany, modelRole, modelStatus, modelCycle, modelIsArchived, modelPhone);
    }

    private Name toModelName() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(name);
    }

    private Email toModelEmail() throws IllegalValueException {
        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(email);
    }

    private ContactRole toModelContactRole() throws IllegalValueException {
        if (contactRole == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, ContactRole.class.getSimpleName()));
        }
        if (!ContactRole.isValidContactRole(contactRole)) {
            throw new IllegalValueException(ContactRole.MESSAGE_CONSTRAINTS);
        }
        return new ContactRole(contactRole);
    }

    private Company toModelCompany() throws IllegalValueException {
        if (company == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Company.class.getSimpleName()));
        }
        if (!Company.isValidCompany(company)) {
            throw new IllegalValueException(Company.MESSAGE_CONSTRAINTS);
        }
        return new Company(company);
    }

    private Role toModelRole() throws IllegalValueException {
        if (role == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Role.class.getSimpleName()));
        }
        if (!Role.isValidRole(role)) {
            throw new IllegalValueException(Role.MESSAGE_CONSTRAINTS);
        }
        return new Role(role);
    }

    private Status toModelStatus() throws IllegalValueException {
        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName()));
        }
        if (!Status.isValidStatus(status)) {
            throw new IllegalValueException(Status.MESSAGE_CONSTRAINTS);
        }
        return new Status(status);
    }

    private Cycle toModelCycle() throws IllegalValueException {
        if (cycle == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Cycle.class.getSimpleName()));
        }
        if (!Cycle.isValidCycle(cycle)) {
            throw new IllegalValueException(Cycle.MESSAGE_CONSTRAINTS);
        }
        return new Cycle(cycle);
    }

    private Phone toModelPhone() throws IllegalValueException {
        if (phone == null) {
            return null;
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(phone);
    }

}
