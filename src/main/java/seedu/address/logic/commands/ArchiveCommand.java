package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_UNARCHIVED_OPPORTUNITIES;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.opportunity.Opportunity;

/**
 * Archives one or more opportunities identified using their displayed indices from the tracker.
 */
public class ArchiveCommand extends Command {

    public static final String COMMAND_WORD = "archive";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Archives the opportunity identified by the index number used in the displayed opportunity list.\n"
            + "Parameters: INDEX [MORE_INDICES]... (must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1 2 3";

    public static final String MESSAGE_ARCHIVE_OPPORTUNITY_SUCCESS = "Archived Opportunity:%1$s";

    public static final String MESSAGE_NOT_IN_UNARCHIVED_VIEW =
            "You are viewing the archive. Use 'list' first to see unarchived opportunities.";

    private final List<Index> targetIndices;

    public ArchiveCommand(List<Index> targetIndices) {
        this.targetIndices = targetIndices;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (new HashSet<>(targetIndices).size() != targetIndices.size()) {
            throw new CommandException(Messages.MESSAGE_DUPLICATE_INDICES);
        }

        boolean isViewingArchive = model.getFilteredOpportunityList().stream()
                .anyMatch(Opportunity::isArchived);
        if (isViewingArchive) {
            throw new CommandException(MESSAGE_NOT_IN_UNARCHIVED_VIEW);
        }

        List<Opportunity> lastShownList = model.getFilteredOpportunityList();

        for (Index targetIndex : targetIndices) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_OPPORTUNITY_DISPLAYED_INDEX);
            }
        }

        // Sort indices in descending order to prevent index mismatch
        // due to shifting when archiving multiple opportunities
        List<Index> sortedIndices = new ArrayList<>(targetIndices);
        sortedIndices.sort((index1, index2) -> index2.getZeroBased() - index1.getZeroBased());

        StringBuilder archivedOpportunities = new StringBuilder();

        for (Index targetIndex : sortedIndices) {
            Opportunity opportunityToArchive = lastShownList.get(targetIndex.getZeroBased());
            Opportunity archivedOpportunity = createArchivedOpportunity(opportunityToArchive);

            model.setOpportunity(opportunityToArchive, archivedOpportunity);
            archivedOpportunities.append(String.format("\n%1$s", Messages.format(archivedOpportunity)));
        }

        model.updateFilteredOpportunityList(PREDICATE_SHOW_UNARCHIVED_OPPORTUNITIES);

        return new CommandResult(
                String.format(MESSAGE_ARCHIVE_OPPORTUNITY_SUCCESS, archivedOpportunities.toString()));
    }

    /**
     * Creates and returns an archived {@code Opportunity} based on {@code opportunityToArchive}.
     * Only {@code isArchived} is changed to {@code true}.
     */
    private static Opportunity createArchivedOpportunity(Opportunity opportunityToArchive) {
        assert opportunityToArchive != null;

        return new Opportunity(
                opportunityToArchive.getName(),
                opportunityToArchive.getEmail(),
                opportunityToArchive.getContactRole(),
                opportunityToArchive.getCompany(),
                opportunityToArchive.getRole(),
                opportunityToArchive.getStatus(),
                opportunityToArchive.getCycle(),
                true,
                opportunityToArchive.getPhone().orElse(null)
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ArchiveCommand)) {
            return false;
        }

        ArchiveCommand otherArchiveCommand = (ArchiveCommand) other;
        return targetIndices.equals(otherArchiveCommand.targetIndices);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndices", targetIndices)
                .toString();
    }
}
