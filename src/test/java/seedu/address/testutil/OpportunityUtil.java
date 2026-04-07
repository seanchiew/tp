package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CYCLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

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
        sb.append(PREFIX_NAME + opportunity.getName().getFullName() + " ");
        sb.append(PREFIX_EMAIL + opportunity.getEmail().getValue() + " ");
        sb.append(PREFIX_CONTACT_ROLE + opportunity.getContactRole().getContactRoleName() + " ");
        sb.append(PREFIX_COMPANY + opportunity.getCompany().getCompanyName() + " ");
        sb.append(PREFIX_ROLE + opportunity.getRole().getRoleName() + " ");
        sb.append(PREFIX_STATUS + opportunity.getStatus().getStatusName() + " ");
        sb.append(PREFIX_CYCLE + opportunity.getCycle().getValue() + " ");
        opportunity.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE + phone.getValue() + " "));
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditOpportunityDescriptor}'s details.
     */
    public static String getEditOpportunityDescriptorDetails(EditOpportunityDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.getFullName()).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.getValue()).append(" "));
        descriptor.getContactRole().ifPresent(cr -> sb.append(PREFIX_CONTACT_ROLE)
                .append(cr.getContactRoleName()).append(" "));
        descriptor.getCompany().ifPresent(company -> sb.append(PREFIX_COMPANY)
                .append(company.getCompanyName()).append(" "));
        descriptor.getRole().ifPresent(role -> sb.append(PREFIX_ROLE).append(role.getRoleName()).append(" "));
        descriptor.getStatus().ifPresent(status -> sb.append(PREFIX_STATUS).append(status.getStatusName()).append(" "));
        descriptor.getCycle().ifPresent(cycle -> sb.append(PREFIX_CYCLE).append(cycle.getValue()).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.getValue()).append(" "));
        return sb.toString();
    }
}
