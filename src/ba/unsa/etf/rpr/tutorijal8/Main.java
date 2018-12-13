package ba.unsa.etf.rpr.tutorijal8;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        FileList model = new FileList();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        loader.setController(new Controller(model));
        Parent root = loader.load();
        primaryStage.setTitle("Pretraživanje");
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.setMinWidth(480);
        primaryStage.setMinHeight(320);
        primaryStage.show();

        primaryStage.setOnHiding((event) -> {
            System.exit(0);
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}