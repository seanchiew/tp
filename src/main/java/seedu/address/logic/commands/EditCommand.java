package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.model.Model.PREDICATE_SHOW_UNARCHIVED_OPPORTUNITIES;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.opportunity.Company;
import seedu.address.model.opportunity.ContactRole;
import seedu.address.model.opportunity.Email;
import seedu.address.model.opportunity.Name;
import seedu.address.model.opportunity.Opportunity;
import seedu.address.model.opportunity.Phone;
import seedu.address.model.opportunity.Role;
import seedu.address.model.opportunity.Status;

/**
 * Edits the details of an existing opportunity in the tracker.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the opportunity identified "
            + "by the index number used in the displayed opportunity list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_CONTACT_ROLE + "CONTACT_ROLE] "
            + "[" + PREFIX_COMPANY + "COMPANY] "
            + "[" + PREFIX_ROLE + "ROLE] "
            + "[" + PREFIX_STATUS + "STATUS] "
            + "[" + PREFIX_PHONE + "PHONE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_STATUS + "INTERVIEW "
            + PREFIX_EMAIL + "new@example.com";

    public static final String MESSAGE_EDIT_OPPORTUNITY_SUCCESS = "Edited Opportunity: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_OPPORTUNITY = "This opportunity already exists in the tracker.";

    private final Index index;
    private final EditOpportunityDescriptor editOpportunityDescriptor;

    /**
     * @param index of the opportunity in the filtered opportunity list to edit
     * @param editOpportunityDescriptor details to edit the opportunity with
     */
    public EditCommand(Index index, EditOpportunityDescriptor editOpportunityDescriptor) {
        requireNonNull(index);
        requireNonNull(editOpportunityDescriptor);

        this.index = index;
        this.editOpportunityDescriptor = new EditOpportunityDescriptor(editOpportunityDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Opportunity> lastShownList = model.getFilteredOpportunityList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_OPPORTUNITY_DISPLAYED_INDEX);
        }

        Opportunity opportunityToEdit = lastShownList.get(index.getZeroBased());
        Opportunity editedOpportunity = createEditedOpportunity(opportunityToEdit, editOpportunityDescriptor);

        if (!opportunityToEdit.isSameOpportunity(editedOpportunity) && model.hasOpportunity(editedOpportunity)) {
            throw new CommandException(MESSAGE_DUPLICATE_OPPORTUNITY);
        }

        model.setOpportunity(opportunityToEdit, editedOpportunity);
        model.updateFilteredOpportunityList(PREDICATE_SHOW_UNARCHIVED_OPPORTUNITIES);
        return new CommandResult(String.format(MESSAGE_EDIT_OPPORTUNITY_SUCCESS, Messages.format(editedOpportunity)));
    }

    /**
     * Creates and returns a {@code Opportunity} with the details of {@code opportunityToEdit}
     * edited with {@code editOpportunityDescriptor}.
     */
    private static Opportunity createEditedOpportunity(Opportunity opportunityToEdit,
            EditOpportunityDescriptor editOpportunityDescriptor) {
        assert opportunityToEdit != null;

        Name updatedName = editOpportunityDescriptor.getName().orElse(opportunityToEdit.getName());
        Email updatedEmail = editOpportunityDescriptor.getEmail().orElse(opportunityToEdit.getEmail());
        ContactRole updatedContactRole =
                editOpportunityDescriptor.getContactRole().orElse(opportunityToEdit.getContactRole());
        Company updatedCompany = editOpportunityDescriptor.getCompany().orElse(opportunityToEdit.getCompany());
        Role updatedRole = editOpportunityDescriptor.getRole().orElse(opportunityToEdit.getRole());
        Status updatedStatus = editOpportunityDescriptor.getStatus().orElse(opportunityToEdit.getStatus());
        Phone updatedPhone = editOpportunityDescriptor.getPhone().orElse(opportunityToEdit.getPhone().orElse(null));

        return new Opportunity(updatedName, updatedEmail, updatedContactRole,
                updatedCompany, updatedRole, updatedStatus,
                opportunityToEdit.isArchived(), updatedPhone);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return index.equals(otherEditCommand.index)
                && editOpportunityDescriptor.equals(otherEditCommand.editOpportunityDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editOpportunityDescriptor", editOpportunityDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the opportunity with. Each non-empty field value will replace the
     * corresponding field value of the opportunity.
     */
    public static class EditOpportunityDescriptor {
        private Name name;
        private Email email;
        private ContactRole contactRole;
        private Company company;
        private Role role;
        private Status status;
        private Phone phone;

        public EditOpportunityDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditOpportunityDescriptor(EditOpportunityDescriptor toCopy) {
            setName(toCopy.name);
            setEmail(toCopy.email);
            setContactRole(toCopy.contactRole);
            setCompany(toCopy.company);
            setRole(toCopy.role);
            setStatus(toCopy.status);
            setPhone(toCopy.phone);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, email, contactRole, company, role, status, phone);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setContactRole(ContactRole contactRole) {
            this.contactRole = contactRole;
        }

        public Optional<ContactRole> getContactRole() {
            return Optional.ofNullable(contactRole);
        }

        public void setCompany(Company company) {
            this.company = company;
        }

        public Optional<Company> getCompany() {
            return Optional.ofNullable(company);
        }

        public void setRole(Role role) {
            this.role = role;
        }

        public Optional<Role> getRole() {
            return Optional.ofNullable(role);
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public Optional<Status> getStatus() {
            return Optional.ofNullable(status);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditOpportunityDescriptor)) {
                return false;
            }

            EditOpportunityDescriptor otherEditOpportunityDescriptor = (EditOpportunityDescriptor) other;
            return getName().equals(otherEditOpportunityDescriptor.getName())
                && getEmail().equals(otherEditOpportunityDescriptor.getEmail())
                && getContactRole().equals(otherEditOpportunityDescriptor.getContactRole())
                && getCompany().equals(otherEditOpportunityDescriptor.getCompany())
                && getRole().equals(otherEditOpportunityDescriptor.getRole())
                && getStatus().equals(otherEditOpportunityDescriptor.getStatus())
                && getPhone().equals(otherEditOpportunityDescriptor.getPhone());
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("email", email)
                    .add("contactRole", contactRole)
                    .add("company", company)
                    .add("role", role)
                    .add("status", status)
                    .add("phone", phone)
                    .toString();
        }
    }
}
