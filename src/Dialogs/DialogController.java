package Dialogs;

import Localisation.Localisator;
import Login.LoginController;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Created by Benjamin Probst on 27.11.17.
 **/

public class DialogController {
    private DialogView dialogView;
    private Localisator localisator;
    private LoginController loginController;

    public DialogController(DialogView dialogView,  Localisator localisator, LoginController loginController){
        this.dialogView = dialogView;
        this.localisator = localisator;
        this.loginController = loginController;

        /**
         * Eventhandler für das drücken des OK Buttons
         */
        dialogView.btnOK.setOnAction(event -> {
            dialogView.connectingLabel.setVisible(false);
            String address = dialogView.getAddress();
            if (address.equals("localhost") || loginController.getLoginModel().checkIP(address)){
                String result = loginController.connect(address);
                switch (result){
                    case "successful":
                        dialogView.stop();
                        break;
                    case "username used":
                        dialogView.stop();
                        loginController.getLoginView().userNameField.setText("");
                        loginController.getLoginView().userNameField.setPromptText(localisator.getResourceBundle().getString("usernameUsed"));
                        loginController.getLoginView().userNameField.getStyleClass().clear();
                        loginController.getLoginView().userNameField.getStyleClass().add("userNameNeeded");
                        loginController.getClient().resetChecked();
                        break;
                    case "Error connecting":
                        dialogView.connectingLabel.setVisible(true);
                        break;
                }
            } else {
                dialogView.connectingLabel.setVisible(true);
            }
        });

        /**
         * Eventhandler für drücken der OK Taste
         */
        dialogView.inputTextField.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ENTER)){
                dialogView.btnOK.fire();
            }
        });

        dialogView.btnCancel.setOnAction(event -> dialogView.stop());
    }

}
