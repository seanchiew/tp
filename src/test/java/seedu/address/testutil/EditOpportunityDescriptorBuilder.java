package seedu.address.testutil;

import seedu.address.logic.commands.EditCommand.EditOpportunityDescriptor;
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
        descriptor.setName(opportunity.getName());
        descriptor.setEmail(opportunity.getEmail());
        descriptor.setContactRole(opportunity.getContactRole());
        descriptor.setCompany(opportunity.getCompany());
        descriptor.setRole(opportunity.getRole());
        descriptor.setStatus(opportunity.getStatus());
        descriptor.setCycle(opportunity.getCycle());
        opportunity.getPhone().ifPresent(descriptor::setPhone);
    }

    /**
     * Sets the {@code Name} of the {@code EditOpportunityDescriptor} that we are building.
     */
    public EditOpportunityDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditOpportunityDescriptor} that we are building.
     */
    public EditOpportunityDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code ContactRole} of the {@code EditOpportunityDescriptor} that we are building.
     */
    public EditOpportunityDescriptorBuilder withContactRole(String contactRole) {
        descriptor.setContactRole(new ContactRole(contactRole));
        return this;
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

    /**
     * Sets the {@code Status} of the {@code EditOpportunityDescriptor} that we are building.
     */
    public EditOpportunityDescriptorBuilder withStatus(String status) {
        descriptor.setStatus(new Status(status));
        return this;
    }

    /**
     * Sets the {@code Cycle} of the {@code EditOpportunityDescriptor} that we are building.
     */
    public EditOpportunityDescriptorBuilder withCycle(String cycle) {
        descriptor.setCycle(new Cycle(cycle));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditOpportunityDescriptor} that we are building.
     */
    public EditOpportunityDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the clearPhone flag of the {@code EditOpportunityDescriptor} that we are building,
     * indicating that the phone field should be cleared on edit.
     */
    public EditOpportunityDescriptorBuilder withClearPhone() {
        descriptor.setClearPhone(true);
        return this;
    }

    public EditOpportunityDescriptor build() {
        return descriptor;
    }
}
