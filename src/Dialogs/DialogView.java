package Dialogs;


import Localisation.Localisator;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class DialogView {
    protected Label inputLabel, connectingLabel;
    private TextField inputTextField;
    protected Button btnOK, btnCancel;
    private Stage dialogStage;
    private Localisator localisator;

    public DialogView(String inputText, String preSetText, String title, Localisator localisator){
        this.localisator = localisator;
        dialogStage = new Stage();
        inputLabel = new Label(inputText);
        inputTextField = new TextField(preSetText);
        dialogStage = new Stage();
        dialogStage.setTitle(title);
        btnOK = new Button("OK");
        btnCancel = new Button("Cancel");

        connectingLabel = new Label(localisator.getResourceBundle().getString("conErrorText"));
        connectingLabel.setVisible(false);

        VBox allBoxes = new VBox();
        HBox upperBox = new HBox();
        HBox underBox = new HBox();
        upperBox.getChildren().addAll(inputLabel, inputTextField);
        upperBox.setAlignment(Pos.CENTER);
        underBox.getChildren().addAll(btnOK, btnCancel);
        underBox.setAlignment(Pos.BOTTOM_RIGHT);
        allBoxes.setAlignment(Pos.CENTER);

        allBoxes.getChildren().addAll(upperBox, underBox, connectingLabel);

        Pane root = new Pane();
        root.getChildren().add(allBoxes);


        Scene scene = new Scene(root, 310, 150);

        dialogStage.setScene(scene);
        dialogStage.getIcons().add(new Image("Backgrounds/DominionSchildTransparent.png"));

    }

    public void start(){
        dialogStage.show();
    }

    public void stop(){
        dialogStage.close();
    }

    public String getAddress(){
        return inputTextField.getText();
    }

}
