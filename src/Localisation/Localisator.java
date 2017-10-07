package Localisation;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by camillo.schweizer on 07.10.2017.
 */
public class Localisator {

    String language;
    ResourceBundle resourceBundle;

    public Localisator(){

        this.language = "eng";
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
        resourceBundle = ResourceBundle.getBundle("Localisation.Bundle", locale);
    }

    public ResourceBundle getResourceBundle(){
        return this.resourceBundle;
    }
}

