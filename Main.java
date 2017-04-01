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
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
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
    	//get all the files in the assignment path
    	String assignmentPath = System.getProperty("user.dir") + "\\src\\" + myPackage;
		File f = new File(assignmentPath);
		File[] files = f.listFiles();

		//find the java files and add them to ArrayList so that they can be added to a combo box later
		for (File file : files) {
			if (file.getName().endsWith(".java")) {
				String fileName = file.getName().substring(0, file.getName().length() - 5);
				try {
					Critter.getCritterFromString(myPackage + "." + fileName);
					arr.add(fileName);
				} catch (InvalidCritterException e) {
					// TODO Auto-generated catch block
				} catch (ClassCastException e) {
					
				}
			}
		}
		
		launch(args);
    }
    
	@Override
	public void start(Stage gridStage) throws Exception {
		final AnimationTimer timer; // sets up the animation timer
		GridPane critGrid = new GridPane();
		GridPane settGrid = new GridPane();
		GridPane statsGrid = new GridPane();
		
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
			
		int row = Params.world_height;	
		int col = Params.world_width;
		int small = 0;
		
//		if (row < col) {
//			small = col;
//		} else {
//			small = row;
//		}
		
		int gridSize = (int) (screenBounds.getWidth()/2); //900
		
		
		//critGrid.setGridLinesVisible(true);
		// Set up constraints for grids
		
		ColumnConstraints column1 = new ColumnConstraints(gridSize/row); //700
		for (int i = 0; i < col; i++) {
	         critGrid.getColumnConstraints().add(column1);
	    }
		
		RowConstraints row1 = new RowConstraints(gridSize/row); //700
		for (int i = 0; i < row; i++) {
	         critGrid.getRowConstraints().add(row1);
	    }
		
//		critGrid.setHgap(2);
//		critGrid.setVgap(2);	
//		critGrid.setPadding(new Insets(2, 2, 2, 2));
		critGrid.setAlignment(Pos.CENTER);
		
		// Add squares for background
		ArrayList<Rectangle> rectArr = new ArrayList<Rectangle>();
		for (int i = 0; i < col; i++) {
			for (int j = 0; j < row; j++) {
				Rectangle rect = new Rectangle(gridSize/row, gridSize/row);
				rect.setFill(javafx.scene.paint.Color.WHITE);
				rect.setStroke(javafx.scene.paint.Color.LIGHTGREY);
				rect.setStrokeWidth(.5);
				
	            critGrid.add(rect, i, j);
	            rectArr.add(rect);
			}
		}
		
		critGrid.getChildren().retainAll(rectArr);
		
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setPercentWidth(40);

        settGrid.getColumnConstraints().add(column2);
        settGrid.getColumnConstraints().add(column2);

		RowConstraints row2 = new RowConstraints();
		row2.setPercentHeight(100/10);
		
		for (int i = 0; i < 10; i++) {
			settGrid.getRowConstraints().add(row2);
		}
		
		settGrid.setHgap(15);
		settGrid.setVgap(15);
		

		
		RowConstraints row4 = new RowConstraints(50);
		RowConstraints row5 = new RowConstraints();
		ColumnConstraints col6 = new ColumnConstraints();
		col6.setPercentWidth(100);
        
        statsGrid.getRowConstraints().add(row4);
        statsGrid.getRowConstraints().add(row5);
        statsGrid.getColumnConstraints().add(col6);
        
		statsGrid.setPadding(new Insets(10));
		
		
		// Set up scenes/stages
		
		Scene critScene = new Scene(critGrid, screenBounds.getWidth()/2, screenBounds.getWidth()/2); //902
		gridStage.setScene(critScene);
		gridStage.show();
		
		
		// Set up screen bounds/window positioning
		
		

		gridStage.setX(screenBounds.getMinX());
		gridStage.setY(screenBounds.getMinY());
		gridStage.show();
		
		Scene settScene = new Scene(settGrid, gridStage.getWidth()*3/5, gridStage.getHeight()/2);
		Stage settStage = new Stage();
		settStage.setScene(settScene);
		settStage.show();
		
		
		Scene statsScene = new Scene(statsGrid, gridStage.getWidth()*3/5, gridStage.getHeight()/2);
		Stage statsStage = new Stage();
		statsStage.setScene(statsScene);
		statsGrid.setHgap(15);
		statsStage.show();
		
		
        gridStage.setX((screenBounds.getWidth() - gridStage.getWidth()));
        gridStage.setY((screenBounds.getHeight() - gridStage.getHeight()));
        
        settStage.setX((screenBounds.getWidth() - settStage.getWidth()) - gridStage.getWidth());
        settStage.setY((screenBounds.getHeight() - gridStage.getHeight()));
		
        statsStage.setX(screenBounds.getWidth() - gridStage.getWidth() - settStage.getWidth());
        statsStage.setY(settStage.getY() + settStage.getHeight());
		
        
        //Setting up the drop down menu for selecting Critters
		ComboBox cb = new ComboBox();
		cb.getItems().addAll(arr);
		
		//Setting up text boxes------------------------------------------------------

		//set seed textbox
		TextField seedText = new TextField();
		seedText.setPromptText("INSERT #");
		seedText.setOnMouseClicked((new EventHandler<MouseEvent>(){
			 
	          @Override
	          public void handle(MouseEvent arg0) {           
	              seedText.setText(null);
	          }
	 
	    }));
		
		//set number of timeStep textbox
		TextField timeText = new TextField();
		timeText.setPromptText("INSERT #");
		timeText.setOnMouseClicked((new EventHandler<MouseEvent>(){
			 
	          @Override
	          public void handle(MouseEvent arg0) {           
	              timeText.setText(null);
	          }
	 
	    }));
		
		//set number of Critters to add textbox 
		TextField critText = new TextField();
		critText.setPromptText("INSERT #");
		critText.setOnMouseClicked((new EventHandler<MouseEvent>(){
			 
	          @Override
	          public void handle(MouseEvent arg0) {           
	              critText.setText(null);
	          }
	 
	    }));
		critText.setMaxWidth(100);


		//setting up sliders and buttons---------------------------------------------

		//set up the animation speed slider
		Slider animSpeed = new Slider(0, 10, 2);
		animSpeed.setMinorTickCount(0);	
		animSpeed.setShowTickMarks(true);
		animSpeed.setShowTickLabels(true);
		animSpeed.setMajorTickUnit(2);
		animSpeed.setSnapToTicks(true);
		animSpeed.setValue(0);
		
		//set up the selection of Critters for runStats
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
		
		//invoke the runStats method
		statsBut.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				String output = "";
				ArrayList<String> selectedItems = new ArrayList<String>();
				statsText.clear();
				
				//displaying the name of Critters 
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
		
		//set up set seed
		Button seedBut = new Button();
		seedBut.setText("SUBMIT");
		
		//gets num from textbox and set the seed
		seedBut.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String prompt = seedText.getText();
				int seed = Integer.parseInt(prompt);
				System.out.println(seed);
				Critter.setSeed(seed);
			}
		});
		
		//set up adding Critters
		Button critBut = new Button();
		critBut.setText("SUBMIT");

		//gets the Critter selected form the combo box and adds the Critter
		critBut.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				String prompt = critText.getText();
				int critCount = 0;
				
				if(prompt.equals("")){
					critCount = 1;
				} else {
					critCount = Integer.parseInt(prompt);
				}

				while (critCount > 0) {
					try {
						Critter.makeCritter(cb.getValue().toString());
					} catch (InvalidCritterException e) {
						// TODO Auto-generated catch block
					}
					critCount--;
				}
				
				critGrid.getChildren().retainAll(rectArr);
				Critter.displayWorld(critGrid);
				statsBut.fire();
			}
		});
		
		//sets up timeSteps 
		Button timeBut = new Button();
		timeBut.setText("SUBMIT");

		//gets the int from time steps text box and run that number of time steps
		timeBut.setOnAction(new EventHandler<ActionEvent>() { 
			@Override
			public void handle(ActionEvent event) {
				String prompt = timeText.getText();
				int times = 0;
				int count = 0;
				//defaults to one time step if nothing is entered
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
				
				critGrid.getChildren().retainAll(rectArr);
				Critter.displayWorld(critGrid);
				statsBut.fire();
			}
		});
		
		//sets up the exit button 
		Button quitBut = new Button();
		quitBut.setText("QUIT");

		//exits out of the system when pressed 
		quitBut.setOnAction(new EventHandler<ActionEvent>() { 
			@Override
			public void handle(ActionEvent event) {
				System.exit(0);
			}
		});

		//set up the two buttons for animation 
		Button startAnimBut = new Button();
		startAnimBut.setText("START ANIMATING");

		Button endAnimBut = new Button();
		endAnimBut.setText("STOP ANIMATING");
		endAnimBut.setDisable(true);

		//use animation timer to set up animation 
		timer = new AnimationTimer() {
			@Override
		    public void handle(long now) {
		    	//get the value from the animation slider 
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
				}

				critGrid.getChildren().retainAll(rectArr);
				Critter.displayWorld(critGrid);
				Critter.worldTimeStep();
				statsBut.fire();
			}
		};
			
		//stops the animation timer and enable buttons after animation is over
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
			
		//starts the animation timer and disable buttons to start an animation 
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

		Line line = new Line(0, 0, settGrid.getWidth(), 0);
	    line.setStroke(Color.LIGHTGRAY);
		
		settGrid.setPadding(new Insets(10, 20, 10, 20));

		settGrid.addRow(0, new Label("SET SEED"), seedText, seedBut);
		settGrid.addRow(1, new Label("ADD CRITTER"), cb, critBut);
		settGrid.add(critText, 1, 2);
		settGrid.addRow(3, line);
		settGrid.addRow(4, new Label("SET TIMESTEPS"), timeText, timeBut);
		settGrid.addRow(5, new Label("SET ANIMATION SPEED"), animSpeed);
		settGrid.add(startAnimBut, 0, 6);
		settGrid.add(endAnimBut, 1, 6);
		settGrid.addRow(7);
		settGrid.addRow(8, quitBut);	
		settGrid.setAlignment(Pos.TOP_CENTER);
		//settGrid.setGridLinesVisible(true);
		
		statsGrid.add(menuBar, 0, 0);
		statsGrid.add(statsText, 0, 1);
	}
}
