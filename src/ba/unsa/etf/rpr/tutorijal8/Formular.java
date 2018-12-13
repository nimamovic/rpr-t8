package ba.unsa.etf.rpr.tutorijal8;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class Formular implements Initializable {
    public TextField naziv;
    public TextField adresa;
    public TextField grad;
    public TextField postanskiBroj;
    public GridPane mainPane;


    private boolean daLiJeIspravanUnos(String n) {
        if (n.length() < 1 || n.length() > 20) return false;
        for (int i = 0; i < n.length(); i++) {
            if (!(n.charAt(i) >= 'A' && n.charAt(i) <= 'Z') && !(n.charAt(i) >= 'a' && n.charAt(i) <= 'z'))  return false;
        }
        return !n.trim().isEmpty();
    }

    private boolean daLiJeIspravnaAdresa(String n){
        if(n.length()!=0) return false;
        return true;
    }

    private boolean daLiJePostanskiBrojValidan() {
        String apiURL = "http://c9.etf.unsa.ba/proba/postanskiBroj.php?postanskiBroj=71000";
        try {
            URL url = new URL(apiURL + postanskiBroj.getText().trim());
            BufferedReader ulaz = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
            String res = ulaz.readLine();
            return res.trim().equals("OK");
             }
             catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }


    }



}
