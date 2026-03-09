package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_OPPORTUNITIES;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.opportunity.Company;
import seedu.address.model.opportunity.Opportunity;
import seedu.address.model.opportunity.Role;

/**
 * Edits the details of an existing opportunity in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the opportunity identified "
            + "by the index number used in the displayed opportunity list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

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
        model.updateFilteredOpportunityList(PREDICATE_SHOW_ALL_OPPORTUNITIES);
        return new CommandResult(String.format(MESSAGE_EDIT_OPPORTUNITY_SUCCESS, Messages.format(editedOpportunity)));
    }

    /**
     * Creates and returns a {@code Opportunity} with the details of {@code opportunityToEdit}
     * edited with {@code editOpportunityDescriptor}.
     */
    private static Opportunity createEditedOpportunity(Opportunity opportunityToEdit,
            EditOpportunityDescriptor editOpportunityDescriptor) {
        assert opportunityToEdit != null;

        Company updatedCompany = editOpportunityDescriptor.getCompany().orElse(opportunityToEdit.getCompany());
        Role updatedRole = editOpportunityDescriptor.getRole().orElse(opportunityToEdit.getRole());

        return new Opportunity(updatedCompany, updatedRole);
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
        private Company company;
        private Role role;

        public EditOpportunityDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditOpportunityDescriptor(EditOpportunityDescriptor toCopy) {
            setCompany(toCopy.company);
            setRole(toCopy.role);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(company, role);
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
            return getCompany().equals(otherEditOpportunityDescriptor.getCompany())
                && getRole().equals(otherEditOpportunityDescriptor.getRole());
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("company", company)
                    .add("role", role)
                    .toString();
        }
    }
}
