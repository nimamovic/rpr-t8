package ba.unsa.etf.rpr.tutorijal8;

import javafx.application.Platform;
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
import java.util.ResourceBundle;


public class Formular implements Initializable {
    public TextField naziv;
    public TextField adresa;
    public TextField grad;
    public TextField postanskiBroj;
    public GridPane mainPane;
    private boolean nazivValidan;
    private boolean adresaValidna;
    private boolean gradValidan;


    private boolean daLiJeIspravanUnos(String n) {
        if (n.length() < 1 || n.length() > 20) return false;
        for (int i = 0; i < n.length(); i++) {
            if (!(n.charAt(i) >= 'A' && n.charAt(i) <= 'Z') && !(n.charAt(i) >= 'a' && n.charAt(i) <= 'z'))
                return false;
        }
        return !n.trim().isEmpty();
    }

    private boolean daLiJeIspravnaAdresa(String n) {
        if (n.length() != 0) return false;
        return true;
    }

    private boolean daLiJePostanskiBrojValidan() {
        String apiURL = "http://c9.etf.unsa.ba/proba/postanskiBroj.php?postanskiBroj=";
        try {
            URL url = new URL(apiURL + postanskiBroj.getText().trim());
            BufferedReader ulaz = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
            String res = ulaz.readLine();
            return res.trim().equals("OK");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nazivValidan = false;
        adresaValidna = false;
        gradValidan = false;

        naziv.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String n) {
                if (daLiJeIspravanUnos(n)) {
                    naziv.getStyleClass().removeAll("nijeOkej");
                    naziv.getStyleClass().add("okej");
                    nazivValidan = true;
                } else {
                    naziv.getStyleClass().removeAll("okej");
                    naziv.getStyleClass().add("nijeOkej");
                    nazivValidan = false;
                }
            }
        });

        adresa.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String n) {
                if (daLiJeIspravnaAdresa(n)) {
                    adresa.getStyleClass().removeAll("nijeOkej");
                    adresa.getStyleClass().add("okej");
                    adresaValidna = true;
                } else {
                    adresa.getStyleClass().removeAll("okej");
                    adresa.getStyleClass().add("nijeOkej");
                    adresaValidna = false;
                }
            }
        });

        grad.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String n) {
                if (daLiJeIspravanUnos(n)) {
                    grad.getStyleClass().removeAll("nijeOkej");
                    grad.getStyleClass().add("okej");
                    gradValidan = true;
                } else {
                    grad.getStyleClass().removeAll("okej");
                    grad.getStyleClass().add("nijeOkej");
                    gradValidan = false;
                }
            }
        });
        if (postanskiBroj != null) {
            postanskiBroj.focusedProperty().addListener((obs, oldVal, newVal) -> {
                if (!newVal)
                    validanPostanskiBroj();
            });
        }


    }

    private void validanPostanskiBroj() {
        new Thread(() -> {
            System.out.println("Pokrenut program za validnost!");
            if(daLiJePostanskiBrojValidan()) {
                Platform.runLater(() -> postanskiBroj.getStyleClass().add("okej"));
                System.out.println("OK");
            }else {
                Platform.runLater(() -> postanskiBroj.getStyleClass().add("nijeOkej"));
                System.out.println("NIJE OK");
            }
        }).start();
    }
}
