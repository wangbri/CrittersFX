package assignment5;
/* CRITTERS Main.java
 * EE422C Project 4 submission by
 * Phyllis Ang
 * pya74
 * 16220
 * Brian Wang
 * brw922
 * 16235
 * Slip days used: <1>
 * Fall 2016
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main extends Application {

    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console

    static ArrayList<String> arr = new ArrayList<String>();
    private final static int[] speed = {0};

    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name, 
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     */
    public static void main(String[] args){ 

    	String assignmentPath = System.getProperty("user.dir") + "\\src\\" + myPackage;
		//System.out.println("Working Directory = " + assignmentPath);
		
		File f = new File(assignmentPath);
		
		File[] files = f.listFiles();
		for (File file : files) {
			if (file.getName().endsWith(".java")) {
				String fileName = file.getName().substring(0, file.getName().length() - 5);
				try {
					Critter.getCritterFromString(myPackage + "." + fileName);
					arr.add(fileName);
					//System.out.println(fileName);
				} catch (InvalidCritterException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				} catch (ClassCastException e) {
					
				}
			}
		}
		
		launch(args);
    }
    
   
    
	@Override
	public void start(Stage primaryStage) throws Exception {
		final AnimationTimer timer; // sets up the animation timer
		// TODO Auto-generated method stub
		GridPane grid = new GridPane();
		
		//grid.setGridLinesVisible(true);
		//System.out.println(grid.isGridLinesVisible());

//		Scene scene = new Scene(grid, 570, 570); //40 and 40 added for gaps, 20 (30?) added for padding
		int row = 20;
		
		//Scene scene = new Scene(grid, 560, 560);
		
		Scene scene = new Scene(grid, 910, 910);
		
		int gridSize = 900 - 10*row;
		
		//System.out.println(grid.getHeight());
		//System.out.println(grid.getWidth());
		
		ColumnConstraints column1 = new ColumnConstraints(gridSize/row);
		for (int i = 0; i < row; i++) {
	         
	         //column.setPercentWidth(20);
	         grid.getColumnConstraints().add(column1);
	    }
		
		RowConstraints row1 = new RowConstraints(gridSize/row);
		for (int i = 0; i < row; i++) {
	         
	         //row.setPercentHeight(20);
	         grid.getRowConstraints().add(row1);
	    }

		
		//grid.getChildren().clear(); // clean up grid.
		
		grid.setHgap(10);
		grid.setVgap(10);
		
		grid.setPadding(new Insets(10, 10, 10, 10));
		


		
		
		
		GridPane grid9 = new GridPane();
		Scene scene9 = new Scene(grid9, 910, 910);
		//System.out.println(grid.getHeight());
		//System.out.println(grid.getWidth());
		
		ColumnConstraints column9 = new ColumnConstraints(gridSize/row);
		for (int i = 0; i < row; i++) {
	         
	         //column.setPercentWidth(20);
	         grid9.getColumnConstraints().add(column1);
	    }
		
		RowConstraints row9 = new RowConstraints(gridSize/row);
		for (int i = 0; i < row; i++) {
	         
	         //row.setPercentHeight(20);
	         grid9.getRowConstraints().add(row1);
	    }

		
		//grid.getChildren().clear(); // clean up grid.
		
		grid9.setHgap(10);
		grid9.setVgap(10);
		
		grid9.setPadding(new Insets(10, 10, 10, 10));
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

		
		
//		NumberBinding rectsAreaSize = Bindings.min(grid.heightProperty(), grid.widthProperty());
//		NumberBinding rectHeight = Bindings.add(scene.heightProperty(), 0);

		for (int i = 0; i < row; i++) {
			for (int j = 0; j < row; j++) {
				Rectangle rect = new Rectangle(gridSize/row, gridSize/row);
				rect.setFill(javafx.scene.paint.Color.WHITE);
				
				//grid.setAlignment(Pos.CENTER);
				
				rect.setStroke(javafx.scene.paint.Color.BLACK);
				rect.setStrokeWidth(2);
				
	            grid.add(rect, i, j); // add the shape to the grid.
			}
		}
		
		primaryStage.setScene(scene);
		//primaryStage.setScene(scene9);
		//primaryStage.setWidth(570);
		//primaryStage.setHeight(570);
		primaryStage.show();
		
		//grid.setGridLinesVisible(true);
		
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

		 //set Stage boundaries to visible bounds of the main screen
		primaryStage.setX(primaryScreenBounds.getMinX());
		primaryStage.setY(primaryScreenBounds.getMinY());
		//primaryStage.setWidth(primaryScreenBounds.getWidth());
		//primaryStage.setHeight(primaryScreenBounds.getHeight());

		primaryStage.show();
		 
		
		GridPane grid2 = new GridPane();
		//grid2.setGridLinesVisible(true);
		Scene scene2 = new Scene(grid2, 590, 480);
		
		
		Stage secondaryStage = new Stage();
		secondaryStage.setScene(scene2);
		secondaryStage.show();
		
		ColumnConstraints column2 = new ColumnConstraints(200);
		ColumnConstraints column3 = new ColumnConstraints(100);

        //column.setPercentWidth(20);
        grid2.getColumnConstraints().add(column2);
        grid2.getColumnConstraints().add(column2);
        grid2.getColumnConstraints().add(column3);
        
		
		RowConstraints row2 = new RowConstraints(50);
		RowConstraints row3 = new RowConstraints(30);
		RowConstraints row3a = new RowConstraints(5);
		
		
		grid2.getRowConstraints().add(row2);
		grid2.getRowConstraints().add(row2);
		grid2.getRowConstraints().add(row3);
		grid2.getRowConstraints().add(row3a);
		grid2.getRowConstraints().add(row2);
		grid2.getRowConstraints().add(row2);
		
		grid2.getRowConstraints().add(row3);
		
		grid2.setHgap(20);
		grid2.setVgap(20);
		
//		Label seedLabel = new Label("SET SEED"); //gridpane doesn't support node alignment
//		TextField seedText = new TextField();
//		HBox hb = new HBox();
//		hb.getChildren().addAll(seedLabel, seedText);
//		hb.setSpacing(10);
//		//seedLabel.setAlignment(Pos.TOP_LEFT);
		
		//System.out.println("Working Directory = " + this.getClass().getPackage().getName());
		
	  
		ComboBox cb = new ComboBox();
		//System.out.println(arr.toString());
		cb.getItems().addAll(arr);
		
		TextField seedText = new TextField();
		seedText.setPromptText("INSERT #");
		seedText.setOnMouseClicked((new EventHandler<MouseEvent>(){
			 
	          @Override
	          public void handle(MouseEvent arg0) {           
	              seedText.setText(null);
	          }
	 
	    }));
		
		TextField timeText = new TextField();
		timeText.setPromptText("INSERT #");
		timeText.setOnMouseClicked((new EventHandler<MouseEvent>(){
			 
	          @Override
	          public void handle(MouseEvent arg0) {           
	              timeText.setText(null);
	          }
	 
	    }));
		
		TextField critText = new TextField();
		critText.setPromptText("INSERT #");
		critText.setOnMouseClicked((new EventHandler<MouseEvent>(){
			 
	          @Override
	          public void handle(MouseEvent arg0) {           
	              critText.setText(null);
	          }
	 
	    }));
		
		critText.setMaxWidth(100);
		//critText.setPrefWidth(30);
		Slider animSpeed = new Slider(0, 10, 2);
		animSpeed.setMinorTickCount(0);	
		animSpeed.setShowTickMarks(true);
		animSpeed.setShowTickLabels(true);
		animSpeed.setMajorTickUnit(2);
		animSpeed.setSnapToTicks(true);
		animSpeed.setValue(0);
		
		Critter.displayWorld(grid9);
		grid9.getChildren().clear();

		//animSpeed handler

		
		//set Seed
		Button seedBut = new Button();
		seedBut.setText("SUBMIT");
		
		Menu subsystemsMenu = null;	
		MenuBar menuBar = new MenuBar();
		
		for (int i = 0; i < arr.size(); i++) {
			CheckMenuItem subsystem1 = new CheckMenuItem("SHOW");
			subsystemsMenu = new Menu(arr.get(i));
			subsystemsMenu.getItems().add(subsystem1);
			menuBar.getMenus().add(subsystemsMenu);
		}
		
		ArrayList<Menu> menuItems = new ArrayList<Menu>(menuBar.getMenus());
		int subsystemsSize = menuItems.size();
		

		TextArea statsText = new TextArea();
		Button statsBut = new Button();
		statsBut.setText("SUBMIT");
		
		statsBut.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				String output = "";
				ArrayList<String> selectedItems = new ArrayList<String>();
				statsText.clear();
				
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				for (int i = 0; i < subsystemsSize; i++) {
					final MenuItem item = menuItems.get(i).getItems().get(0);
					
					if (((CheckMenuItem) item).isSelected()) {
						selectedItems.add(menuItems.get(i).getText());
					}
				}
				
				try {
					for (int i = 0; i < selectedItems.size(); i++) {
						List<Critter> critList = Critter.getInstances(selectedItems.get(i));
						Class critterClass = null;
						Method method = null;
						
						try {
							critterClass = Class.forName(myPackage + "." + selectedItems.get(i));
							method = critterClass.getMethod("runStats", List.class);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							throw new InvalidCritterException(selectedItems.get(i));
						} catch (NoSuchMethodException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SecurityException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						output += method.invoke(critterClass, critList) + "\n";
					}
				} catch (InvalidCritterException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				final String output1 = output;
				
				byte[] b = output1.getBytes();
				try {
					os.write(b);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				statsText.setText(output1);
				Platform.runLater(() -> statsText.setText(output1));
			}
			
		});

		seedBut.setOnAction(new EventHandler<ActionEvent>() { // what to do when butt is pressed
			@Override
			public void handle(ActionEvent event) {
				String prompt = seedText.getText();
				int seed = Integer.parseInt(prompt);
				System.out.println(seed);
				Critter.setSeed(seed);
			}
		});
		
		Button critBut = new Button();
		critBut.setText("SUBMIT");
		critBut.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				int critCount = Integer.parseInt(critText.getText());
				// TODO Auto-generated method stub
				while (critCount > 0) {
					try {
						Critter.makeCritter(cb.getValue().toString());
					} catch (InvalidCritterException e) {
						// TODO Auto-generated catch block
					}
					critCount--;
				}
				Critter.displayWorld(grid9);
			}
		});
		
		//time steps
		Button timeBut = new Button();
		timeBut.setText("SUBMIT");

		timeBut.setOnAction(new EventHandler<ActionEvent>() { // what to do when butt is pressed
			@Override
			public void handle(ActionEvent event) {
				String prompt = timeText.getText();
				int times = 0;
				int count = 0;
				if(prompt.equals("")){
					times = 1;
				}
				else{
					times = Integer.parseInt(prompt);
				}
				while (count < times) {
					Critter.worldTimeStep();
					count++;
				}
				//TODO: displayworld and runStats
				Critter.displayWorld(grid);
				statsBut.fire();
			}
		});
		
		
		//quit
		Button quitBut = new Button();
		quitBut.setText("QUIT");

		quitBut.setOnAction(new EventHandler<ActionEvent>() { // what to do when butt is pressed
			@Override
			public void handle(ActionEvent event) {
				System.exit(0);
			}
		});


		//Sets up animation 
		Button startAnimBut = new Button();
		startAnimBut.setText("START ANIMATING");

		Button endAnimBut = new Button();
		endAnimBut.setText("STOP ANIMATING");
		endAnimBut.setDisable(true);

		timer = new AnimationTimer() {
			@Override
		    public void handle(long now) {
				animSpeed.valueProperty().addListener(new ChangeListener<Number>() {
					public void changed(ObservableValue<? extends Number> ov,
							Number old_val, Number new_val) {
						    speed[0] = (int)new_val.doubleValue();
						            	 
					}
				});
				
				int time = speed[0];		
				//animation refresh rate
				try {
					if(time == 0){
						Thread.sleep(1000);
					}
					else{
					  Thread.sleep(1000/time);
					}
				} catch (InterruptedException ie) {
					    //Handle exception
				}
				for (int i = 0; i < row; i++) {
					for (int j = 0; j < row; j++) {
						Rectangle rect = new Rectangle(gridSize/row, gridSize/row);
						rect.setFill(javafx.scene.paint.Color.WHITE);
						
						rect.setStroke(javafx.scene.paint.Color.BLACK);
						rect.setStrokeWidth(2);
			            grid.add(rect, i, j); // add the shape to the grid.
					}
				}
				Critter.displayWorld(grid);
				Critter.worldTimeStep();
				statsBut.fire();

				//TODO: run stats and display world
			}
		};//.start();
			
				
		endAnimBut.setOnAction(new EventHandler<ActionEvent>() { // what to do when butt is pressed
			@Override
			public void handle(ActionEvent event) {
				//animation[0] = false;
				timer.stop();
				
				//enable the makeCritter button and dropdown 
				cb.setDisable(false);
				critBut.setDisable(false);
				critText.setDisable(false);
				
				//enable the timeStep button and textbox
				timeBut.setDisable(false);
				timeText.setDisable(false);
					
				//enable the setSeed button and textbox
				seedBut.setDisable(false);
				seedText.setDisable(false);
				
				//disable end animation button
				endAnimBut.setDisable(true);

				//enable start animation button 
				startAnimBut.setDisable(false);
			}
			
		});
			
			
		startAnimBut.setOnAction(new EventHandler<ActionEvent>() { // what to do when butt is pressed
			@Override
			public void handle(ActionEvent event) { 
				//animation[0] = true;
				timer.start();
					
				//gray out the makeCritter button and dropdown 
				cb.setDisable(true);
				critBut.setDisable(true);
				critText.setDisable(true);
				
				//gray out the timeStep button and textbox
				timeBut.setDisable(true);
				timeText.setDisable(true);
					
				//gray out the setSeed button and textbox
				seedBut.setDisable(true);
				seedText.setDisable(true);
				
				//enable stop animation button
				endAnimBut.setDisable(false);

				//disable this button
				startAnimBut.setDisable(true);
			}
				
		});
		
		
		
		grid2.setPadding(new Insets(10, 25, 10, 25));
		grid2.addRow(0, new Label("SET SEED"), seedText, seedBut);
		grid2.addRow(1, new Label("ADD CRITTER"), cb, critBut);
		grid2.add(critText, 1, 2);
		grid2.addRow(3);
		grid2.addRow(4, new Label("SET TIMESTEPS"), timeText, timeBut);
		grid2.addRow(5, new Label("SET ANIMATION SPEED"), animSpeed);
		grid2.add(startAnimBut, 0, 6);
		grid2.add(endAnimBut, 1, 6);
		grid2.addRow(7);
		grid2.addRow(8, quitBut);
		
		
		
		GridPane grid3 = new GridPane();
		//grid3.setGridLinesVisible(true);
		Scene scene3 = new Scene(grid3, 590, 300);
		
		RowConstraints row4 = new RowConstraints(50);
		RowConstraints row5 = new RowConstraints(220);
        
        grid3.getRowConstraints().add(row4);
        grid3.getRowConstraints().add(row5);
        
        ColumnConstraints column4 = new ColumnConstraints(450);
        
        grid3.getColumnConstraints().add(column4);
		grid3.setPadding(new Insets(10, 25, 10, 25));
		
		Stage tertiaryStage = new Stage();
		tertiaryStage.setScene(scene3);
		tertiaryStage.show();
		
		grid3.setHgap(15);
		
		
		
		
		grid3.addRow(0, menuBar, statsBut);
		grid3.addRow(1,  statsText);
		// Set position of the windows on the screen
		
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((screenBounds.getWidth() - primaryStage.getWidth()));
        primaryStage.setY((screenBounds.getHeight() - primaryStage.getHeight()));
        
        secondaryStage.setX((screenBounds.getWidth() - secondaryStage.getWidth()) - primaryStage.getWidth());
        secondaryStage.setY((screenBounds.getHeight() - primaryStage.getHeight()));
		
        tertiaryStage.setX(screenBounds.getWidth() - primaryStage.getWidth() - secondaryStage.getWidth());
        tertiaryStage.setY(secondaryStage.getY() + secondaryStage.getHeight());
	}
}
