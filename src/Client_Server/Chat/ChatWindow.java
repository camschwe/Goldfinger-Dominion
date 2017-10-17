package Client_Server.Chat;

import Localisation.Localisator;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class ChatWindow {
    protected Button sendButton;
    protected Stage stage;
    protected Localisator localisator;
    protected TextField txtMessage;
    protected TextArea txtChatMessages;     // try TextFlow
    protected TextFlow txtChatFlow;
    protected Pane root;
    private String newLine;
    protected VBox vBox;


    public ChatWindow(Localisator localisator) {
        this.stage = new Stage();
        this.localisator = localisator;
        txtChatMessages = new TextArea();
        txtMessage = new TextField();
        sendButton = new Button(localisator.getResourceBundle().getString("sendButton"));
        txtChatMessages.setEditable(false);
        txtChatFlow = new TextFlow();
        txtChatFlow.setPrefSize(100, 300);
        txtChatFlow.setMaxHeight(150);
        txtChatFlow.setStyle("-fx-background-color: #FFFFFF");
        newLine = System.getProperty("line.separator");

        HBox hBox = new HBox();
        vBox = new VBox();
        txtMessage.setPromptText(localisator.getResourceBundle().getString("message"));

        root = new Pane();
        //vBox.getChildren().addAll(txtChatMessages, hBox);
        vBox.getChildren().addAll(txtChatFlow, hBox);
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

    public Button getSendButton() {
        return sendButton;
    }

    public String getMessage(){
        String message = txtMessage.getText();
        return message;
    }

    public TextArea getTxtChatMessages() {
        return txtChatMessages;
    }

    public void clearMessageField(){
        this.txtMessage.clear();
    }

    public TextField getTxtMessage() {
        return txtMessage;
    }

    public VBox getVBox(){
        return this.vBox;
    }

    public void actualizeTextArea(String message){
        txtChatMessages.appendText(message + newLine);
    }

    public void actualizeChatFlow(Message message){
        Text user = new Text(message.getClientName());
        user.setStyle(message.getColor());
        Text content = new Text(": " + message.getMessage() + newLine);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                txtChatFlow.getChildren().addAll(user, content);
                if (txtChatFlow.getChildren().size() > 30) {
                    txtChatFlow.getChildren().remove(1);
                    txtChatFlow.getChildren().remove(0);
                }
            }
        });

    }
}
