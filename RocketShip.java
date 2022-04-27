// Program RocketShip
// Purpose:
//		The purpose of this application is to 
//		paint a rocketship scene as specified 
//		in the assignment, using Rectangle
//		and Polygons (for triangles).
// Programmer: Reece Landry
// INFO 2313 A12
//

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.util.ArrayList;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class RocketShip extends Application {
	
	// Default size of the scene
	private double DEFAULT_SCENE_WIDTH = 200;
	private double DEFAULT_SCENE_HEIGHT = 400;
	
	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) { 
		
		// Create a Rocketship Pane
		RocketShipPane rocket = new RocketShipPane();
		
		// Create a scene and place it in the stage
		Scene scene = new Scene(rocket, 
				DEFAULT_SCENE_WIDTH, 
				DEFAULT_SCENE_HEIGHT);
		primaryStage.setTitle("Rocket Ship");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		// Print out the author
		System.out.println("Written by Reece Landry, INFO 2312 A12");
    
	}// start

	/**
	* The main method is only needed for the IDE with limited
	* JavaFX support. Not needed for running from the command line.
	*/
	public static void main(String[] args) {
		launch(args);
	}

	// Inner Class RocketPane
	// Defined inside Rocketship class
	class RocketShipPane extends Pane {
		
		// Constants to define rocket dimensions
		private final double ROCKET_BODY_WIDTH = 20;
		private final double ROCKET_BODY_HEIGHT = 50;
		private final double ROCKET_TOP_HEIGHT = 30;
		private final double ROCKET_TOP_WIDTH = 20;
		private final double ROCKET_FIN_WIDTH = 20;
		private final double ROCKET_FIN_HEIGHT = 20;
		private final double GROUND_HEIGHT = 20;
		private Timeline animation;
		public boolean isLaunched = false;
		private double speed = 0.05;
		private Color bgColor = Color.BLUE;
		private double rocketHeight = 50;
		private boolean showGround = true;
		private boolean dropStars = false;
		private ArrayList<Circle> stars = new ArrayList<Circle>();
		
		
		//create rocketman media player
		private String rocketman = "audio/rocketman.mp3";
		Media hit = new Media(new File(rocketman).toURI().toString());
		MediaPlayer rocketmanPlayer = new MediaPlayer(hit);
		
		//create launch sound media player
		private String liftoff = "audio/liftoff.mp3";
		Media lift = new Media(new File(liftoff).toURI().toString());
		MediaPlayer liftoffPlayer = new MediaPlayer(lift);
		
		// Private data fields to keep track of where 
		// the base of the rocket should be
		// Rocket will be in center of the pane
		// on the "ground" at the bottom of the pane
		private double baseX = DEFAULT_SCENE_WIDTH / 2 - 
				(ROCKET_BODY_WIDTH/2 + ROCKET_FIN_WIDTH);
		private double baseY = DEFAULT_SCENE_HEIGHT - GROUND_HEIGHT;

		// Non-arg constructor for RocketShipPane
		// Draw rocketship
		public RocketShipPane() {
			rocketmanPlayer.play();
			animation = new Timeline(
					new KeyFrame(Duration.millis(10), e -> moveUp()));
			animation.setCycleCount(Timeline.INDEFINITE);
			paintRocket();
		}
    
		// Method paintRocket
		//	Paint the rocket scene in the correct location
		// 	using Rectangles and Polygons (for Triangles)
		private void paintRocket () {
			
			// Create ground 
			// using a Rectangle
			this.getChildren().clear();
			Rectangle ground = new Rectangle(0,getHeight()-GROUND_HEIGHT,getWidth(),GROUND_HEIGHT);
			ground.setFill(Color.GREEN);
			
			// Print the author's name on the ground
			// using a Text 
			  Text name = new Text(0, getHeight()-ground.getLayoutBounds().getHeight()/2, "Landry Space Center");
			    name.setFont(Font.font("Courier", 
			    		FontPosture.ITALIC, 12));
			    name.setX(getWidth()/2 - name.getLayoutBounds().getWidth()/2);
			    name.setTextAlignment(TextAlignment.LEFT);
						
			// Create left fin of rocketship 
			// using a Polygon
			   Polygon leftFin = new Polygon(new double[] {
					   baseX,baseY,
					   baseX+ROCKET_FIN_WIDTH,baseY,
					   baseX+ROCKET_FIN_WIDTH,baseY-ROCKET_FIN_HEIGHT,
			   });
			   leftFin.setFill(Color.RED);
			   leftFin.setStroke(Color.BLACK);
			
			// Create right fin of rocketship
			// using a Polygon
			   Polygon rightFin = new Polygon(new double[] {
					   baseX+ROCKET_FIN_WIDTH*2+ROCKET_BODY_WIDTH,baseY,
					   baseX+ROCKET_FIN_WIDTH+ROCKET_BODY_WIDTH,baseY,
					   baseX+ROCKET_FIN_WIDTH+ROCKET_BODY_WIDTH,baseY-ROCKET_FIN_HEIGHT
			   });
			   rightFin.setFill(Color.RED);
			   rightFin.setStroke(Color.BLACK);
			
			// Create body of rocketship
			// using a Rectangle
			   Rectangle body = new Rectangle(baseX+ROCKET_FIN_WIDTH, baseY-ROCKET_BODY_HEIGHT, ROCKET_BODY_WIDTH, ROCKET_BODY_HEIGHT);
			   body.setFill(Color.WHITE);
			   body.setStroke(Color.BLACK);

			// Create top of rocketship
			// using a Polygon
			   Polygon top = new Polygon(new double[] {
					   baseX+ROCKET_FIN_WIDTH, baseY-ROCKET_BODY_HEIGHT,
					   baseX+ROCKET_FIN_WIDTH+ROCKET_TOP_WIDTH, baseY-ROCKET_BODY_HEIGHT,
					   baseX+ROCKET_FIN_WIDTH+ROCKET_TOP_WIDTH/2, baseY-ROCKET_BODY_HEIGHT-ROCKET_TOP_HEIGHT
			   });
			   top.setFill(Color.RED);
			   top.setStroke(Color.BLACK);
			   
			// Add Flame
			// using a Polygon
			   double random = 0;
			   //check if the rocket is launched yet
			   if (isLaunched) {
				   random = (Math.random()*30);
				   //prevent rocket flame from going over the ground
				   while (baseY - random < ground.getLayoutY()) {
					   random = (Math.random()*10);
				   }
			   }
			   Polygon flame = new Polygon (new double[] {
					   baseX+ROCKET_FIN_WIDTH, baseY,
					   baseX+ROCKET_BODY_WIDTH+ROCKET_FIN_WIDTH, baseY,
					   baseX+ROCKET_BODY_WIDTH/2+ROCKET_FIN_WIDTH, baseY + random,	   
			   });
			   flame.setFill(Color.ORANGE);
			   flame.setStroke(Color.BLACK);
			   
			   if(dropStars) {
				   int randomNum = (int)(Math.random()*40);
				   if (randomNum == 20) {
					   double randomX = Math.random()*getWidth();
					   Stop[] stops = new Stop[] {
						         new Stop(0.0, Color.WHITE),
						         new Stop(1, Color.YELLOW)
					   };
					   
					   RadialGradient starGradient = new RadialGradient(0, .1, 100, 100, 20, false, CycleMethod.NO_CYCLE, stops);
					   double radius = Math.random()*7 + 2;
					   GaussianBlur blur = new GaussianBlur();
				       blur.setRadius(10);
					   Circle star = new Circle();
					   star.setCenterX(randomX);
					   star.setCenterY(-getHeight() - radius*2);
					   star.setFill(starGradient);
					   star.setRadius(radius);
					   star.setEffect(blur);
					   stars.add(star);
				   }
				   
				   this.getChildren().addAll(stars);
				   
			   }
			
			// Clear the RocketShipPane and 
			// add all the rocket components 
			// + the ground.   Set the background
			// color of the RockShipPane to blue
			   
			   if (isLaunched) {
				   this.getChildren().add(flame);
			   }
			   if (showGround) {
				   this.getChildren().addAll(ground,name);
			   }
			   this.getChildren().addAll(rightFin, leftFin, body, top);
			   this.setBackground(new Background(new BackgroundFill(bgColor, null, null)));
		}// paintRocket
		 
		// setWidth 
		//	This method is called when window is resized.
		// 	If the width is changed, recalculate the 
		//	baseX of the rocket so the scene will be 
		//	redrawn correctly
		@Override
		public void setWidth(double width) {
			super.setWidth(width);
			baseX = super.getWidth() / 2 - 
					(ROCKET_BODY_WIDTH/2 + ROCKET_FIN_WIDTH);
			paintRocket();
		}
		
		// setHeight 
		//	This method is called when window is resized.
		// 	If the height is changed, recalculate the 
		//	baseY of the rocket so the scene will be 
		//	redrawn correctly
		@Override
		public void setHeight(double height) {
			super.setHeight(height);
			baseY = super.getHeight() - GROUND_HEIGHT;
			paintRocket();
		}
		
		//moveUp
		//	This method is called when the animation happens
		//	baseY of the rocket will be adjusted accordingly
		public void play() {
			liftoffPlayer.play();
			isLaunched = true;
			this.
			animation.play();
		}

		public void pause() {
			animation.pause();
		}
		
		protected void moveUp() {
			//if the rocket is not high enough
			if (rocketHeight < super.getHeight()/2) {
				if (speed<2) {
					speed *= 1.01;
				}
				baseY -= speed;
				rocketHeight += speed;
			}else {
				//once the rocket gets high enough (half the screen)
				dropStars = true;
				showGround = false;
				for(int i = 0; i < stars.size(); i++)
				{
				    if(stars.get(i).getCenterY() > getHeight()) {
				    	stars.remove(i);
				    }else {
				    	stars.get(i).setCenterY(stars.get(i).getCenterY() + 10);
				    }
				}
			}
			if (bgColor.getBlue() - speed*0.001 > 0.0) {
				bgColor = new Color(0.0, 0.0, bgColor.getBlue() - speed*0.001, 1);
			}
			paintRocket();
			
		}

	}// RocketShipPane
	
}//RocketShip
