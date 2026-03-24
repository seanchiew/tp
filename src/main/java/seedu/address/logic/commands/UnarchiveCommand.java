package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ARCHIVED_OPPORTUNITIES;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.opportunity.Opportunity;

/**
 * Unarchives one or more opportunities identified using their displayed indices from the tracker.
 */
public class UnarchiveCommand extends Command {

    public static final String COMMAND_WORD = "unarchive";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unarchives the opportunity identified by the index number used in the displayed opportunity list.\n"
            + "Parameters: INDEX [MORE_INDICES]... (must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1 2 3";

    public static final String MESSAGE_UNARCHIVE_OPPORTUNITY_SUCCESS = "Unarchived Opportunity:%1$s";

    public static final String MESSAGE_NOT_IN_ARCHIVE_VIEW =
            "You are not viewing the archive. Use 'list archive' first to see archived opportunities.";

    private final List<Index> targetIndices;

    public UnarchiveCommand(List<Index> targetIndices) {
        this.targetIndices = targetIndices;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        boolean isViewingArchive = model.getFilteredOpportunityList().stream()
                .anyMatch(Opportunity::isArchived);
        if (!isViewingArchive) {
            throw new CommandException(MESSAGE_NOT_IN_ARCHIVE_VIEW);
        }

        List<Opportunity> fullList = model.getAddressBook().getOpportunityList();
        List<Opportunity> archivedOpportunitiesList = new ArrayList<>();

        for (Opportunity opportunity : fullList) {
            if (opportunity.isArchived()) {
                archivedOpportunitiesList.add(opportunity);
            }
        }

        // Use LinkedHashSet to remove duplicates
        Set<Index> uniqueIndices = new LinkedHashSet<>(targetIndices);

        for (Index targetIndex : uniqueIndices) {
            if (targetIndex.getZeroBased() >= archivedOpportunitiesList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_OPPORTUNITY_DISPLAYED_INDEX);
            }
        }

        // Sort indices in descending order to prevent index mismatch
        // due to shifting when unarchiving multiple opportunities
        List<Index> sortedIndices = new ArrayList<>(uniqueIndices);
        sortedIndices.sort((index1, index2) -> index2.getZeroBased() - index1.getZeroBased());

        StringBuilder unarchivedOpportunities = new StringBuilder();

        for (Index targetIndex : sortedIndices) {
            Opportunity opportunityToUnarchive = archivedOpportunitiesList.get(targetIndex.getZeroBased());
            Opportunity unarchivedOpportunity = createUnarchivedOpportunity(opportunityToUnarchive);

            model.setOpportunity(opportunityToUnarchive, unarchivedOpportunity);
            unarchivedOpportunities.append(String.format("\n%1$s", Messages.format(unarchivedOpportunity)));
        }

        // Update the filtered list to show only archived opportunities after unarchiving
        model.updateFilteredOpportunityList(PREDICATE_SHOW_ARCHIVED_OPPORTUNITIES);

        return new CommandResult(
                String.format(MESSAGE_UNARCHIVE_OPPORTUNITY_SUCCESS, unarchivedOpportunities.toString()));
    }

    /**
     * Creates and returns an unarchived {@code Opportunity} based on {@code opportunityToUnarchive}.
     * Only {@code isArchived} is changed to {@code false}.
     */
    private static Opportunity createUnarchivedOpportunity(Opportunity opportunityToUnarchive) {
        assert opportunityToUnarchive != null;

        return new Opportunity(
                opportunityToUnarchive.getName(),
                opportunityToUnarchive.getEmail(),
                opportunityToUnarchive.getContactRole(),
                opportunityToUnarchive.getCompany(),
                opportunityToUnarchive.getRole(),
                opportunityToUnarchive.getStatus(),
                opportunityToUnarchive.getCycle(),
                false,
                opportunityToUnarchive.getPhone().orElse(null)
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnarchiveCommand)) {
            return false;
        }

        UnarchiveCommand otherUnarchiveCommand = (UnarchiveCommand) other;
        return targetIndices.equals(otherUnarchiveCommand.targetIndices);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndices", targetIndices)
                .toString();
    }
}
