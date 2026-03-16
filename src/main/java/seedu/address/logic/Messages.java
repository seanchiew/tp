package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.opportunity.Opportunity;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_OPPORTUNITY_DISPLAYED_INDEX =
            "The opportunity index provided is invalid";
    public static final String MESSAGE_OPPORTUNITIES_LISTED_OVERVIEW = "%1$d opportunities listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code opportunity} for display to the user.
     */
    public static String format(Opportunity opportunity) {
        final StringBuilder builder = new StringBuilder();
        builder.append("Name: ")
                .append(opportunity.getName())
                .append("; Email: ")
                .append(opportunity.getEmail())
                .append("; Contact Role: ")
                .append(opportunity.getContactRole())
                .append("; Company: ")
                .append(opportunity.getCompany())
                .append("; Role: ")
                .append(opportunity.getRole())
                .append("; Status: ")
                .append(opportunity.getStatus());
        opportunity.getPhone().ifPresent(phone -> builder.append("; Phone: ").append(phone));
        return builder.toString();
    }

}
