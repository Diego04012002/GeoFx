package dad.geofx.client.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class IpApp extends Application {
	
	Controller controller;

	@Override
	public void start(Stage primaryStage) throws Exception {
		controller=new Controller();
		
		Scene escena= new Scene(controller.getView());
		
		primaryStage.setScene(escena);
		primaryStage.setTitle("GeoFX");
		primaryStage.show();

	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
