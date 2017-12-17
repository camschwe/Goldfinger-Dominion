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
 *
 * View für den Login mit Eingabe des Benutzernamens, Buttons für das hosten sowie beitreten eines Servers
 * sowie den Spracheinstellungen und der Auflösung und der Musik
 */

public class LoginView {

    protected Stage primaryStage;
    protected Button hostButton, joinButton, tutorialButton, musicButton;
    public TextField userNameField;
    protected Label userNameLabel, connectingLabel;
    protected ComboBox<String> languageBox, sizeBox;
    protected Localisator localisator;
    protected TextInputDialog dialog;
    protected Alert conError;


    public LoginView(Stage primaryStage, Localisator localisator) {
        this.primaryStage = primaryStage;
        this.localisator = localisator;

        /**
         * Label und Buttons
         */

        userNameLabel = new Label(localisator.getResourceBundle().getString("username"));
        connectingLabel = new Label(localisator.getResourceBundle().getString("connecting"));
        connectingLabel.setVisible(false);
        hostButton = new Button(localisator.getResourceBundle().getString("hosting"));
        joinButton = new Button(localisator.getResourceBundle().getString("join"));
        userNameField = new TextField();
        userNameField.setPromptText(localisator.getResourceBundle().getString("username"));
        VBox centerBox = new VBox();

        /**
         * Box zur Auswahl der Sprache - Englisch, Deutsch und Schweizerdeutsch
         */

        languageBox = new ComboBox<>();
        //languageBox.getStyleClass().add("box");
        languageBox.setPromptText("Schwiizerdütsch");
        languageBox.getItems().addAll(
                "English",
                "Deutsch",
                "Schwiizerdütsch"
        );

        /**
         * Box zur Auswahl der Auflösung - 720p sowie 1080p
         */

        sizeBox = new ComboBox<>();
        sizeBox.setPromptText("720p");
        sizeBox.getItems().addAll(
                "1080p",
                "720p"
        );

        /**
         * Button für Musik ein/ausschalten
         */

        musicButton = new Button();
        musicButton.getStyleClass().clear();
        musicButton.getStyleClass().add("musicButtonOn");


        conError = new Alert(Alert.AlertType.ERROR);
        dialog = new TextInputDialog("localhost");

        dialog.setTitle(localisator.getResourceBundle().getString("addressTitle"));
        dialog.setHeaderText(localisator.getResourceBundle().getString("addressHeader"));
        dialog.setContentText(localisator.getResourceBundle().getString("addressText"));

        conError.setTitle(localisator.getResourceBundle().getString("conErrorTitle"));
        conError.setHeaderText(localisator.getResourceBundle().getString("conErrorHeader"));
        conError.setContentText(localisator.getResourceBundle().getString("conErrorText"));
        Stage conStage = (Stage) conError.getDialogPane().getScene().getWindow();
        conStage.getIcons().add(new Image("Backgrounds/DominionSchildTransparent.png"));

        /**
         * Container
         */

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
        bottom.setSpacing(20);
        bottom.getChildren().addAll(languageBox, sizeBox);

        /**
         * Initialisierung der Scene mit einer vorgegebenen Grösse von 1000x800px sowie dem Hintergrund, dem Stylesheet
         * und dem Icon
         */

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

