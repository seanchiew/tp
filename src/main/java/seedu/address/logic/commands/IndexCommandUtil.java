package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Shared helpers for commands that act on multiple displayed indices.
 */
final class IndexCommandUtil {

    private IndexCommandUtil() {}

    /**
     * Returns the items referenced by {@code targetIndices} in the same order.
     *
     * @throws CommandException if the indices contain duplicates or are out of bounds.
     */
    static <T> List<T> getItemsAtIndices(List<Index> targetIndices, List<T> items,
                                         String invalidIndexMessage) throws CommandException {
        requireNonNull(targetIndices);
        requireNonNull(items);
        requireNonNull(invalidIndexMessage);

        validateUniqueIndices(targetIndices);

        List<T> selectedItems = new ArrayList<>();
        for (Index targetIndex : targetIndices) {
            if (targetIndex.getZeroBased() >= items.size()) {
                throw new CommandException(invalidIndexMessage);
            }
            selectedItems.add(items.get(targetIndex.getZeroBased()));
        }

        return selectedItems;
    }

    /**
     * Returns a descending copy of the given indices.
     */
    static List<Index> getIndicesInDescendingOrder(List<Index> targetIndices) {
        requireNonNull(targetIndices);

        List<Index> sortedIndices = new ArrayList<>(targetIndices);
        sortedIndices.sort((index1, index2) -> Integer.compare(index2.getZeroBased(), index1.getZeroBased()));
        return sortedIndices;
    }

    private static void validateUniqueIndices(List<Index> targetIndices) throws CommandException {
        if (new HashSet<>(targetIndices).size() != targetIndices.size()) {
            throw new CommandException(Messages.MESSAGE_DUPLICATE_INDICES);
        }
    }
}
