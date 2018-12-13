package ba.unsa.etf.rpr.tutorijal8;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private FileList model;
    public Button findBtn;
    public TextField inputField;
    public ListView<String> listaPuteva;
    public File root = new File(System.getProperty("user.home"));
    public Button stopBtn;

    public Controller(FileList model) {
        this.model = model;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listaPuteva.setItems(model.getPutevi());

    }

    public void trazi(javafx.event.ActionEvent actionEvent) {
        model.deletePutevi();
        findBtn.setDisable(true);
        stopBtn.setDisable(false);
        Finder myFinder = new Finder();
        Thread myThread = new Thread(myFinder);
        prekidacZaPretrazivanje(true);
        myThread.start();

    }

    public void prekini(javafx.event.ActionEvent actionEvent) {
        stopBtn.setDisable(true);
        findBtn.setDisable(false);
    }

    public void prekidacZaPretrazivanje(boolean vrijednost) {
        findBtn.setDisable(vrijednost);
        stopBtn.setDisable(!vrijednost);
    }

    public class Finder implements Runnable{

        @Override
        public void run() {
            find(inputField.getText(),root.getAbsolutePath());
        }

        public void find(String name, String parent){
            if(stopBtn.isDisabled()){
                Thread.currentThread().stop();
            }
            File[] child = new File(parent).listFiles();
            if (child != null) {
                if(child.length!=0){
                    for (File aChild : child) {
                        if (aChild.getName().contains(name) && aChild.isFile()) {
                            Platform.runLater(()-> {
                                model.addPut(aChild.getAbsolutePath()); });
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        if (aChild.isDirectory()) {
                            find(name, aChild.getAbsolutePath());
                        }
                    }
                }
            }
            if(parent.equals(root.getAbsolutePath())){
                findBtn.setDisable(false);
            }
        }

    }
    private void otvoriFile() {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("formular.fxml"));
            stage.setTitle("Formular");
            stage.setScene(new Scene(root, 317, 200));
            stage.initOwner(listaPuteva.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void otvori(MouseEvent mouseEvent) {
        ObservableList file = listaPuteva.getSelectionModel().getSelectedItems();
        if (file == null){
            System.out.println("Nista nije izabrano!");
        }
        else {
            otvoriFile();
        }
    }



}