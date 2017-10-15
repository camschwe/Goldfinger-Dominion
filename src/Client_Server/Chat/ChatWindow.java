package Client_Server.Chat;

import Localisation.Localisator;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChatWindow {
    protected Button sendButton;
    protected Stage stage;
    protected Localisator localisator;
    protected TextField txtMessage;
    protected TextArea txtChatMessages;
    protected Pane root;


    public ChatWindow(Localisator localisator) {
        this.stage = new Stage();
        this.localisator = localisator;
        txtChatMessages = new TextArea();
        txtMessage = new TextField();
        sendButton = new Button(localisator.getResourceBundle().getString("sendButton"));
        txtChatMessages.setEditable(false);

        HBox hBox = new HBox();
        VBox vBox = new VBox();
        txtMessage.setPromptText(localisator.getResourceBundle().getString("message"));

        root = new Pane();
        vBox.getChildren().addAll(txtChatMessages, hBox);
        hBox.getChildren().addAll(txtMessage, sendButton);
        vBox.setSpacing(5);
        hBox.setSpacing(5);

        root.getChildren().add(vBox);
    }

    public void start(){
        stage.show();
    }

    public void stop(){
        stage.hide();
    }

    public Pane getRoot(){
        return root;
    }
}
