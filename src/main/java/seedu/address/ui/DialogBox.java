package seedu.address.ui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * A UI component that displays messages to the user depending on
 * the validity of the user's input.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;


    private DialogBox(String text) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);

    }

    /**
     * Flips the dialog box such that the text appears on the left.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }


    /**
     * Returns a new instance of DialogBox displaying the user's
     * input.
     *
     * @param text String representing user's input.
     * @return a new instance of DialogBox containing the user's
     *     input.
     */
    public static DialogBox getUserDialog(String text) {
        return new DialogBox(text);
    }

    /**
     * Returns a new instance of DialogBox displaying the AddCommandHelper's
     * output.
     *
     * @param text String representing AddCommandHelper's output.
     * @return a new instance of DialogBox containing AddCommandHelper's
     *     output.
     */
    public static DialogBox getAddCommandHelperDialog(String text) {
        DialogBox dialogBox = new DialogBox(text);
        dialogBox.flip();
        return dialogBox;
    }
}
