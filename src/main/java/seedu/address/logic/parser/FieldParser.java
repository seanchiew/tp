package seedu.address.logic.parser;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * A functional interface for a parse function that may throw a {@link ParseException}.
 *
 * @param <T> the type of the parsed result
 */
@FunctionalInterface
interface FieldParser<T> {
    T parse(String value) throws ParseException;
}
