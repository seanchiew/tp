package seedu.address.testutil;

import seedu.address.model.opportunity.Company;
import seedu.address.model.opportunity.Opportunity;
import seedu.address.model.opportunity.Role;

/**
 * A utility class to help with building Opportunity objects.
 */
public class OpportunityBuilder {

    public static final String DEFAULT_COMPANY = "Stripe";
    public static final String DEFAULT_ROLE = "SWE Intern";

    private Company company;
    private Role role;

    /**
     * Creates a {@code OpportunityBuilder} with the default details.
     */
    public OpportunityBuilder() {
        company = new Company(DEFAULT_COMPANY);
        role = new Role(DEFAULT_ROLE);
    }

    /**
     * Initializes the OpportunityBuilder with the data of {@code opportunityToCopy}.
     */
    public OpportunityBuilder(Opportunity opportunityToCopy) {
        company = opportunityToCopy.getCompany();
        role = opportunityToCopy.getRole();
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

    public Opportunity build() {
        return new Opportunity(company, role);
    }

}
