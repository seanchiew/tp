package seedu.address.ui;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.opportunity.Opportunity;

/**
 * Panel containing the list of opportunities.
 */
public class OpportunityListPanel extends UiPart<Region> {
    private static final String FXML = "OpportunityListPanel.fxml";

    @FXML
    private ListView<Opportunity> opportunityListView;

    /**
     * Creates a {@code OpportunityListPanel} with the given {@code ObservableList}.
     */
    public OpportunityListPanel(ObservableList<Opportunity> opportunityList) {
        super(FXML);
        opportunityListView.setItems(opportunityList);
        opportunityListView.setCellFactory(listView -> new OpportunityListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Opportunity} using a {@code OpportunityCard}.
     */
    class OpportunityListViewCell extends ListCell<Opportunity> {
        @Override
        protected void updateItem(Opportunity opportunity, boolean empty) {
            super.updateItem(opportunity, empty);

            if (empty || opportunity == null) {
                setGraphic(null);
                setText(null);
            } else {
                Node card = new OpportunityCard(opportunity, getIndex() + 1).getRoot();
                if (card instanceof Region) {
                    Region cardRegion = (Region) card;
                    cardRegion.prefWidthProperty().bind(
                            Bindings.createDoubleBinding(() -> Math.max(0,
                                            getWidth() - snappedLeftInset() - snappedRightInset()),
                            widthProperty(), paddingProperty()));
                }
                setGraphic(card);
            }
        }
    }

}
