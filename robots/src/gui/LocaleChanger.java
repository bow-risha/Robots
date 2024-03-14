package gui;

import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleChanger {
    private final ResourceBundle bundle;

    public LocaleChanger(String language, String country) {
        Locale locale = new Locale(language, country);
        bundle = ResourceBundle.getBundle("resources", locale);
    }

    public void changeUiLocale() {
        for (String key : bundle.keySet()) {
            UIManager.put(key, bundle.getString(key));
        }
    }
}
