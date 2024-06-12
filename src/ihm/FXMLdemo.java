package ihm;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FXMLdemo extends Application {

  public void start(Stage stage) throws IOException {
          FXMLLoader loader = new FXMLLoader();
          URL fxmlFileUrl = getClass().getResource("Prototype_haute_fidelite.fxml");
          if (fxmlFileUrl == null) {
                  System.out.println("Impossible de charger le fichier fxml");
                  System.exit(-1);
          }
          loader.setLocation(fxmlFileUrl);
          Parent root = loader.load();

          Scene scene = new Scene(root);
          stage.setScene(scene);
          stage.setTitle("FXML demo");
          stage.show();
  }

  public static void main(String[] args) {
          Application.launch(args);
  }
}