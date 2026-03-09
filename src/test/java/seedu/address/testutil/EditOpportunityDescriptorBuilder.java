package seedu.address.testutil;

import seedu.address.logic.commands.EditCommand.EditOpportunityDescriptor;
import seedu.address.model.opportunity.Company;
import seedu.address.model.opportunity.Opportunity;
import seedu.address.model.opportunity.Role;

/**
 * A utility class to help with building EditOpportunityDescriptor objects.
 */
public class EditOpportunityDescriptorBuilder {

    private EditOpportunityDescriptor descriptor;

    public EditOpportunityDescriptorBuilder() {
        descriptor = new EditOpportunityDescriptor();
    }

    public EditOpportunityDescriptorBuilder(EditOpportunityDescriptor descriptor) {
        this.descriptor = new EditOpportunityDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditOpportunityDescriptor} with fields containing {@code opportunity}'s details
     */
    public EditOpportunityDescriptorBuilder(Opportunity opportunity) {
        descriptor = new EditOpportunityDescriptor();
        descriptor.setCompany(opportunity.getCompany());
        descriptor.setRole((opportunity.getRole()));
    }

    /**
     * Sets the {@code Company} of the {@code EditOpportunityDescriptor} that we are building.
     */
    public EditOpportunityDescriptorBuilder withCompany(String company) {
        descriptor.setCompany(new Company(company));
        return this;
    }

    /**
     * Sets the {@code Role} of the {@code EditOpportunityDescriptor} that we are building.
     */
    public EditOpportunityDescriptorBuilder withRole(String role) {
        descriptor.setRole(new Role(role));
        return this;
    }

    public EditOpportunityDescriptor build() {
        return descriptor;
    }
}
