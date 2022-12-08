package easv_MTunes;

import easv_MTunes.gui.Controller.SongViewController;
import easv_MTunes.gui.Model.MTModel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/easv_MTunes/gui/View/MyTunesView.fxml"));
        Parent root = loader.load();
        SongViewController controller = loader.getController();
        controller.setModel(new MTModel());
        controller.setup();

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("My Tunes");
        primaryStage.show();
        primaryStage.setResizable(false);
        Image icon = new Image("easv_MTunes/images/icon-modified.png");
        primaryStage.getIcons().add(icon);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
                System.exit(0);
            }
        });
    }
}
