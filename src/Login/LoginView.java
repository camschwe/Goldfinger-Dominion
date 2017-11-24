package Login;

import Localisation.Localisator;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


/**
 * Created by camillo.schweizer on 06.10.2017.
 */
public class LoginView {

    protected Stage primaryStage;
    protected Button hostButton, joinButton;
    protected TextField userNameField;
    protected Label userNameLabel, connectingLabel;
    protected ComboBox<String> switchBox;
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


        switchBox = new ComboBox<>();
        switchBox.setPromptText(localisator.getResourceBundle().getString("language"));
        switchBox.getItems().addAll(
                "English",
                "Deutsch",
                "Schwiizerdütsch"
        );

        conError = new Alert(Alert.AlertType.ERROR);
        dialog = new TextInputDialog("localhost");        // TODO (localisator.getResourceBundle().getString("address"));

        dialog.setTitle(localisator.getResourceBundle().getString("addressTitle"));
        dialog.setHeaderText(localisator.getResourceBundle().getString("addressHeader"));
        dialog.setContentText(localisator.getResourceBundle().getString("addressText"));

        conError.setTitle(localisator.getResourceBundle().getString("conErrorTitle"));
        conError.setHeaderText(localisator.getResourceBundle().getString("conErrorHeader"));
        conError.setContentText(localisator.getResourceBundle().getString("conErrorText"));

        BorderPane root = new BorderPane();
        GridPane gridPane = new GridPane();
        root.setCenter(gridPane);
        gridPane.add(userNameLabel, 0, 0);
        gridPane.add(userNameField, 1, 0);
        gridPane.add(hostButton, 0, 2);
        gridPane.add(joinButton, 1, 2);
        gridPane.setPadding(new Insets(500, 0, 0, 290));
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        HBox hBox = new HBox();
        root.setBottom(hBox);
        hBox.setPadding(new Insets(0, 0, 40, 740));
        hBox.getChildren().addAll(switchBox);

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

}

