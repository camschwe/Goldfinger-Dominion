package Dialogs;

import Localisation.Localisator;
import Login.LoginController;

public class DialogController {
    private DialogView dialogView;
    private DialogModel dialogModel;
    private Localisator localisator;
    private LoginController loginController;

    public DialogController(DialogView dialogView, DialogModel dialogModel, Localisator localisator, LoginController loginController){
        this.dialogView = dialogView;
        this.dialogModel = dialogModel;
        this.localisator = localisator;
        this.loginController = loginController;

        dialogView.btnOK.setOnAction(event -> {
            dialogView.connectingLabel.setVisible(false);
            String address = dialogView.getAddress();
            if (address.equals("localhost") || loginController.getLoginModel().checkIP(address)){
                String result = loginController.connect(address);
                switch (result){
                    case "successfull":
                        dialogView.stop();
                        break;
                    case "username used":
                        dialogView.stop();
                        break;
                    case "Error connecting":
                        dialogView.connectingLabel.setVisible(true);
                        break;
                }
            } else {
                dialogView.connectingLabel.setVisible(true);
            }
        });

        dialogView.btnCancel.setOnAction(event ->{
            //Do something
        });
    }

}
