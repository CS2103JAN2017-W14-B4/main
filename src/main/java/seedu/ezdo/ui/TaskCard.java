package seedu.ezdo.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.ezdo.model.todo.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private AnchorPane priorityColor;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label priority;
    @FXML
    private Label startDate;
    @FXML
    private Label dueDate;
    @FXML
    private FlowPane tags;

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        name.setText(task.getName().fullName);
        id.setText(displayedIndex + ". ");
        setPriority(task);
        startDate.setText(task.getStartDate().value);
        dueDate.setText(task.getDueDate().value);
        initTags(task);
    }

    private void setPriority(ReadOnlyTask task) {
        String priorityInString = task.getPriority().value;
        priority.setText(priorityInString);

        switch (priorityInString) {

        case "1":
            priorityColor.setStyle("-fx-background-color: green");
            break;
        case "2":
            priorityColor.setStyle("-fx-background-color: orange");
            break;
        case "3":
            priorityColor.setStyle("-fx-background-color: red");
            break;

        }

    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
