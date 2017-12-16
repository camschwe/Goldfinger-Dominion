package Localisation;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by camillo.schweizer on 07.10.2017.
 *
 * Klasse um die Sprache in den Views anzupassen. Standardmässig ist Schweizerdeutsch hinterlegt. Der String gibt die
 * aktuelle Sprache wieder. Bei jeder Anpassung der Sprache wird der String neu gesetzt und ein neues Sprachobjekt generiert
 */
public class Localisator {

    String language;
    ResourceBundle resourceBundle;

    public Localisator(){

        this.language = "CH";
        newLocal();
    }

    public void switchCH(){
        this.language = "CH";
        newLocal();
    }

    public void switchENG(){
        this.language = "eng";
        newLocal();
    }

    public void switchGER(){
        this.language = "ger";
        newLocal();
    }

    public void newLocal(){
        Locale locale = new Locale(language);
        resourceBundle = ResourceBundle.getBundle("Properties/Bundle", locale);
    }

    public ResourceBundle getResourceBundle(){
        return this.resourceBundle;
    }

    public String getLanguage() {
        return language;
    }

}

