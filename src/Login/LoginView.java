package Login;

import Localisation.Localisator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;


/**
 * Created by camillo.schweizer on 06.10.2017.
 */
public class LoginView {

    protected Stage primaryStage;
    protected Button hostButton, joinButton, tutorialButton, musicButton;
    protected TextField userNameField;
    protected Label userNameLabel, connectingLabel;
    protected ComboBox<String> languageBox, sizeBox;
    protected Localisator localisator;
    protected TextInputDialog dialog;
    protected Alert conError;


    public LoginView(Stage primaryStage, Localisator localisator) {
        this.primaryStage = primaryStage;
        this.localisator = localisator;

        userNameLabel = new Label(localisator.getResourceBundle().getString("username"));
        connectingLabel = new Label(localisator.getResourceBundle().getString("connecting"));
        connectingLabel.setVisible(false);
        hostButton = new Button(localisator.getResourceBundle().getString("hosting"));
        joinButton = new Button(localisator.getResourceBundle().getString("join"));
        userNameField = new TextField();
        userNameField.setPromptText(localisator.getResourceBundle().getString("username"));
        VBox centerBox = new VBox();


        languageBox = new ComboBox<>();
        languageBox.setPromptText("Schwiizerdütsch");
        languageBox.getItems().addAll(
                "English",
                "Deutsch",
                "Schwiizerdütsch"
        );

        sizeBox = new ComboBox<>();
        sizeBox.setPromptText("720p");
        sizeBox.getItems().addAll(
                "1080p",
                "720p"
        );

        musicButton = new Button();
        musicButton.getStyleClass().clear();
        musicButton.getStyleClass().add("musicButtonOn");
        /** Music Button soll 40x40 sein **/


        conError = new Alert(Alert.AlertType.ERROR);
        dialog = new TextInputDialog("localhost");        // TODO (localisator.getResourceBundle().getString("address"));

        dialog.setTitle(localisator.getResourceBundle().getString("addressTitle"));
        dialog.setHeaderText(localisator.getResourceBundle().getString("addressHeader"));
        dialog.setContentText(localisator.getResourceBundle().getString("addressText"));

        conError.setTitle(localisator.getResourceBundle().getString("conErrorTitle"));
        conError.setHeaderText(localisator.getResourceBundle().getString("conErrorHeader"));
        conError.setContentText(localisator.getResourceBundle().getString("conErrorText"));
        Stage conStage = (Stage) conError.getDialogPane().getScene().getWindow();
        conStage.getIcons().add(new Image("Backgrounds/DominionSchildTransparent.png"));


        BorderPane root = new BorderPane();
        GridPane gridPane = new GridPane();

        centerBox.setAlignment(Pos.BOTTOM_CENTER);
        centerBox.getChildren().add(connectingLabel);
        centerBox.getChildren().add(gridPane);

        root.setCenter(centerBox);
        gridPane.add(userNameLabel, 0, 0);
        gridPane.add(userNameField, 1, 0);
        gridPane.add(hostButton, 0, 2);
        gridPane.add(joinButton, 1, 2);
        gridPane.setPadding(new Insets(10, 0, 20, 290));
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        VBox bottom = new VBox();
        VBox musicBox = new VBox();
        musicBox.getChildren().add(musicButton);
        musicBox.setPadding(new Insets(10));
        musicBox.setAlignment(Pos.TOP_RIGHT);
        root.setTop(musicBox);
        root.setBottom(bottom);
        bottom.setPadding(new Insets(0, 0, 40, 740));
        bottom.getChildren().addAll(languageBox, sizeBox);

        //Scene Initialisieren
        Scene scene = new Scene(root, 1000, 800);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("../Stylesheets/LoginStyles.css").toExternalForm());
        primaryStage.getIcons().add(new Image("Backgrounds/DominionSchildTransparent.png"));
        primaryStage.setTitle("Goldfinger Dominion");
        primaryStage.setMaxWidth(1000);
        primaryStage.setMaxHeight(800);
        primaryStage.setMinWidth(100);
        primaryStage.setMinHeight(800);
    }

    public void start() {
        primaryStage.show();
    }

    public void stop() {
        primaryStage.hide();

    }

    public Label getConnectingLabel() {
        return connectingLabel;
    }
}

