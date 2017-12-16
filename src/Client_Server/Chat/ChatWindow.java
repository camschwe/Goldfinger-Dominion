package Client_Server.Chat;

import Localisation.Localisator;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * Created by Benjamin Probst on 11.10.2017.
 **/

public class ChatWindow {
    protected Button sendButton;
    protected Stage stage;
    protected Localisator localisator;
    protected TextField txtMessage;
    protected TextFlow txtChatFlow;
    protected Pane root;
    private String newLine;
    protected VBox vBox;
    protected ScrollPane scrollPane;


    public ChatWindow(Localisator localisator) {
        this.stage = new Stage();
        this.localisator = localisator;
        txtMessage = new TextField();
        sendButton = new Button(localisator.getResourceBundle().getString("sendButton"));
        sendButton.getStyleClass().add("chatButton");
        txtChatFlow = new TextFlow();


        newLine = System.getProperty("line.separator");
        scrollPane = new ScrollPane();
        scrollPane.setPrefSize(100, 300);
        scrollPane.getStyleClass().add("scroll-pane");
        txtChatFlow.getStyleClass().add("chatFlow");
        scrollPane.setContent(txtChatFlow);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.vvalueProperty().bind(txtChatFlow.heightProperty());
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);


        HBox hBox = new HBox();
        vBox = new VBox();
        txtMessage.setPromptText(localisator.getResourceBundle().getString("message"));

        root = new Pane();
        vBox.getChildren().addAll(scrollPane, hBox);
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
        return txtMessage.getText();
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

    public ScrollPane getScrollPane(){
        return this.scrollPane;
    }

    /**
     * Aktualisiert das Nachrichtenfenster mit der neusten Nachricht
      */
    public void actualizeChatFlow(Message message){
        System.out.println(message);
        Text user = new Text(message.getClientName() + ": ");
        user.setStyle(message.getColor());
        Text content = new Text(message.getMessage() + newLine);
        Platform.runLater(() -> txtChatFlow.getChildren().addAll(user, content));

    }


}
