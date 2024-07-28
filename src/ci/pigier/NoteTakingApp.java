package ci.pigier;

import ci.pigier.database.DatabaseConnection;
import ci.pigier.ui.FXMLPage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.*;

public class NoteTakingApp extends Application {

	@Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(FXMLPage.LIST.getPage());
        
        Scene scene = new Scene(root);
     
        stage.setScene(scene);
        stage.setTitle("P'Note-Taking Application v1.0.0");
        stage.setResizable(false);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn != null) {
                System.out.println("Database connected!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	
        launch(args);
    }
    

}
