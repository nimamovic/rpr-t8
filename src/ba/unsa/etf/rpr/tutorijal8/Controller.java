package ba.unsa.etf.rpr.tutorijal8;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private FileList model;
    public Button findBtn;
    public TextField inputField;
    public ListView<String> listaPuteva;
    public File root = new File("/Users/nasiha");
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
        myThread.start();
    }

    public void prekini(javafx.event.ActionEvent actionEvent) {
        stopBtn.setDisable(true);
        findBtn.setDisable(false);
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


}