package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_UNARCHIVED_OPPORTUNITIES;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.opportunity.Cycle;
import seedu.address.model.opportunity.Opportunity;

/**
 * Archives all active opportunities for a given cycle.
 */
public class ArchiveCycleCommand extends Command {

    public static final String SUBCOMMAND_WORD = "cycle";
    public static final String MESSAGE_USAGE = ArchiveCommand.COMMAND_WORD + " " + SUBCOMMAND_WORD
            + ": Archives all active opportunities with the specified cycle.\n"
            + "Parameters: CYCLE\n"
            + Cycle.PARSER_MESSAGE_CONSTRAINTS + "\n"
            + "Examples: " + ArchiveCommand.COMMAND_WORD + " " + SUBCOMMAND_WORD + " SUMMER 2026\n"
            + "          " + ArchiveCommand.COMMAND_WORD + " " + SUBCOMMAND_WORD + " S2 2026";

    public static final String MESSAGE_ARCHIVE_CYCLE_SUCCESS =
            "Archived %1$d active %2$s for cycle %3$s:%4$s";

    public static final String MESSAGE_NO_ACTIVE_OPPORTUNITIES_FOR_CYCLE =
            "No active opportunities found for cycle %1$s.";

    private final Cycle targetCycle;

    /**
     * Creates an {@code ArchiveCycleCommand} that archives all active opportunities
     * matching {@code targetCycle}.
     *
     * @param targetCycle cycle of the active opportunities to archive
     */
    public ArchiveCycleCommand(Cycle targetCycle) {
        requireNonNull(targetCycle);
        this.targetCycle = targetCycle;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Opportunity> matchingOpportunities = new ArrayList<>();
        for (Opportunity opportunity : model.getAddressBook().getOpportunityList()) {
            if (!opportunity.isArchived() && opportunity.getCycle().equals(targetCycle)) {
                matchingOpportunities.add(opportunity);
            }
        }

        if (matchingOpportunities.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_NO_ACTIVE_OPPORTUNITIES_FOR_CYCLE, targetCycle));
        }

        StringBuilder archivedOpportunities = new StringBuilder();
        for (Opportunity opportunityToArchive : matchingOpportunities) {
            Opportunity archivedOpportunity = opportunityToArchive.archive();
            model.setOpportunity(opportunityToArchive, archivedOpportunity);
            archivedOpportunities.append(String.format("\n%1$s", Messages.format(archivedOpportunity)));
        }

        model.updateFilteredOpportunityList(PREDICATE_SHOW_UNARCHIVED_OPPORTUNITIES);

        int archivedCount = matchingOpportunities.size();
        String archivedLabel = archivedCount == 1 ? "opportunity" : "opportunities";
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_ARCHIVE_CYCLE_SUCCESS,
                archivedCount, archivedLabel, targetCycle, archivedOpportunities.toString()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ArchiveCycleCommand)) {
            return false;
        }

        ArchiveCycleCommand otherArchiveCycleCommand = (ArchiveCycleCommand) other;
        return targetCycle.equals(otherArchiveCycleCommand.targetCycle);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetCycle", targetCycle)
                .toString();
    }
}
