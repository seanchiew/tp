package seedu.address.testutil;

import seedu.address.model.opportunity.Company;
import seedu.address.model.opportunity.ContactRole;
import seedu.address.model.opportunity.Email;
import seedu.address.model.opportunity.Name;
import seedu.address.model.opportunity.Opportunity;
import seedu.address.model.opportunity.Phone;
import seedu.address.model.opportunity.Role;
import seedu.address.model.opportunity.Status;

/**
 * A utility class to help with building Opportunity objects.
 */
public class OpportunityBuilder {

    public static final String DEFAULT_NAME = "Alice Tan";
    public static final String DEFAULT_EMAIL = "alice@stripe.com";
    public static final String DEFAULT_CONTACT_ROLE = "recruiter";
    public static final String DEFAULT_COMPANY = "Stripe";
    public static final String DEFAULT_ROLE = "SWE Intern";
    public static final String DEFAULT_STATUS = "APPLIED";

    private Name name;
    private Email email;
    private ContactRole contactRole;
    private Company company;
    private Role role;
    private Status status;
    private boolean isArchived;
    private Phone phone;

    /**
     * Creates a {@code OpportunityBuilder} with the default details.
     */
    public OpportunityBuilder() {
        name = new Name(DEFAULT_NAME);
        email = new Email(DEFAULT_EMAIL);
        contactRole = new ContactRole(DEFAULT_CONTACT_ROLE);
        company = new Company(DEFAULT_COMPANY);
        role = new Role(DEFAULT_ROLE);
        status = new Status(DEFAULT_STATUS);
        isArchived = false;
        phone = null;
    }

    /**
     * Initializes the OpportunityBuilder with the data of {@code opportunityToCopy}.
     */
    public OpportunityBuilder(Opportunity opportunityToCopy) {
        name = opportunityToCopy.getName();
        email = opportunityToCopy.getEmail();
        contactRole = opportunityToCopy.getContactRole();
        company = opportunityToCopy.getCompany();
        role = opportunityToCopy.getRole();
        status = opportunityToCopy.getStatus();
        isArchived = opportunityToCopy.isArchived();
        phone = opportunityToCopy.getPhone().orElse(null);
    }

    /**
     * Sets the {@code Name} of the {@code Opportunity} that we are building.
     */
    public OpportunityBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Opportunity} that we are building.
     */
    public OpportunityBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code ContactRole} of the {@code Opportunity} that we are building.
     */
    public OpportunityBuilder withContactRole(String contactRole) {
        this.contactRole = new ContactRole(contactRole);
        return this;
    }

    /**
     * Sets the {@code Company} of the {@code Opportunity} that we are building.
     */
    public OpportunityBuilder withCompany(String company) {
        this.company = new Company(company);
        return this;
    }

    /**
     * Sets the {@code Role} of the {@code Opportunity} that we are building.
     */
    public OpportunityBuilder withRole(String role) {
        this.role = new Role(role);
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code Opportunity} that we are building.
     */
    public OpportunityBuilder withStatus(String status) {
        this.status = new Status(status);
        return this;
    }

    /**
     * Sets the archived status of the {@code Opportunity} that we are building.
     */
    public OpportunityBuilder withArchived(boolean isArchived) {
        this.isArchived = isArchived;
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Opportunity} that we are building.
     */
    public OpportunityBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the phone to absent for the {@code Opportunity} that we are building.
     */
    public OpportunityBuilder withoutPhone() {
        this.phone = null;
        return this;
    }

    public Opportunity build() {
        return new Opportunity(name, email, contactRole, company, role, status, isArchived, phone);
    }

}
