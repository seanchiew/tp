package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.opportunity.Company;
import seedu.address.model.opportunity.Opportunity;
import seedu.address.model.opportunity.Role;

/**
 * Jackson-friendly version of {@link Opportunity}.
 */
class JsonAdaptedOpportunity {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Opportunity's %s field is missing!";

    private final String company;
    private final String role;

    /**
     * Constructs a {@code JsonAdaptedOpportunity} with the given opportunity details.
     */
    @JsonCreator
    public JsonAdaptedOpportunity(@JsonProperty("company") String company, @JsonProperty("role") String role) {
        this.company = company;
        this.role = role;
    }

    /**
     * Converts a given {@code Opportunity} into this class for Jackson use.
     */
    public JsonAdaptedOpportunity(Opportunity source) {
        company = source.getCompany().companyName;
        role = source.getRole().roleName;
    }

    /**
     * Converts this Jackson-friendly adapted opportunity object into the model's {@code Opportunity} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted opportunity.
     */
    public Opportunity toModelType() throws IllegalValueException {
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

        return new Opportunity(modelCompany, modelRole);
    }

}
