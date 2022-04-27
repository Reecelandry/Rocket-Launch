import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class RocketShipLaunch extends RocketShip {

	
	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) {
		
		//create rocket object
	    RocketShipPane rocketPane = new RocketShipPane();
    
	    // Create an animation for alternating text
	    // After the duration specified, the eventHandler
	    // is called.
	    
	    
	    rocketPane.setOnMousePressed(e -> rocketPane.play());
	    rocketPane.setOnMouseReleased(e -> rocketPane.pause());
	    
	    // Create a scene and place it in the stage
	    Scene scene = new Scene(rocketPane, 300, 600);
	    primaryStage.setTitle("Rocketman"); // Set the stage title
	    primaryStage.setScene(scene); // Place the scene in the stage
	    primaryStage.show(); // Display the stage
	    
	}

	
	
	
	/**
	* The main method is only needed for the IDE with limited
	* JavaFX support. Not needed for running from the command line.
	*/
	public static void main(String[] args) {
		launch(args);
	}

}
