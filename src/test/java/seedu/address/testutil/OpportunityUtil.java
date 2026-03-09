package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditOpportunityDescriptor;
import seedu.address.model.opportunity.Opportunity;

/**
 * A utility class for Opportunity.
 */
public class OpportunityUtil {

    /**
     * Returns an add command string for adding the {@code opportunity}.
     */
    public static String getAddCommand(Opportunity opportunity) {
        return AddCommand.COMMAND_WORD + " " + getOpportunityDetails(opportunity);
    }

    /**
     * Returns the part of command string for the given {@code opportunity}'s details.
     */
    public static String getOpportunityDetails(Opportunity opportunity) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_COMPANY + opportunity.getCompany().companyName + " ");
        sb.append(PREFIX_ROLE + opportunity.getRole().roleName + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditOpportunityDescriptor}'s details.
     */
    public static String getEditOpportunityDescriptorDetails(EditOpportunityDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getCompany().ifPresent(company -> sb.append(PREFIX_COMPANY).append(company.companyName).append(" "));
        descriptor.getRole().ifPresent(role -> sb.append(PREFIX_ROLE).append(role.roleName).append(" "));
        return sb.toString();
    }
}
