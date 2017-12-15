package Login;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Benjamin Probst on 06.10.2017.
 */
public class LoginModel {

    /**
     * Methode überprüft ob es sich um eine IP Adresse handelt
     * Kopiert von https://stackoverflow.com/questions/15689945/how-do-i-check-if-a-text-contains-an-ip-address
     *
     * @param text IP Adresse übergeben
     * @return True/False ist IP Adresse oder nicht
     */
    public static boolean checkIP(String text) {
        Pattern p = Pattern.compile("^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
        Matcher m = p.matcher(text);
        return m.find();
    }
}
