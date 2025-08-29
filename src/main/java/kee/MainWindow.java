package kee;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Kee kee;
    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/confused_emoji.png"));
    private Image keeImage = new Image(this.getClass().getResourceAsStream("/images/cool_emoji.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

    }

    /** Injects the Kee instance */
    public void setKee(Kee k) {
        kee = k;
        dialogContainer.getChildren().add(
                DialogBox.getDukeDialog(kee.startChat(), keeImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Kee's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = kee.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, keeImage)
        );
        userInput.clear();
        if (input.equalsIgnoreCase("bye")) {
            javafx.application.Platform.exit();
        }
    }
}
