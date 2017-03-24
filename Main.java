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
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Screen;
import javafx.stage.Stage;


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
//        if (args.length != 0) {
//            try {
//                inputFile = args[0];
//                kb = new Scanner(new File(inputFile));			
//            } catch (FileNotFoundException e) {
//                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
//                e.printStackTrace();
//            } catch (NullPointerException e) {
//                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
//            }
//            if (args.length >= 2) {
//                if (args[1].equals("test")) { // if the word "test" is the second argument to java
//                    // Create a stream to hold the output
//                    testOutputString = new ByteArrayOutputStream();
//                    PrintStream ps = new PrintStream(testOutputString);
//                    // Save the old System.out.
//                    old = System.out;
//                    // Tell Java to use the special stream; all console output will be redirected here from now
//                    System.setOut(ps);
//                }
//            }
//        } else { // if no arguments to main
//            kb = new Scanner(System.in); // use keyboard and console
//        }
//
//        boolean stop = false; //keeps track of whether quit command has occurred 
//    	
//    	while(!stop){
//    		//prompts the user the gets their input 
//    		System.out.print("critters> ");
//		    ArrayList<String> inputArgs = new ArrayList<String>();
//		    String wrongCommand = kb.nextLine();
//		    inputArgs.addAll(Arrays.asList(wrongCommand.split("\\s+")));
//			    
//			String command = inputArgs.get(0);
//			
//			try{
//				switch (command) {
//					//stop the program
//					case "quit": 
//						if(inputArgs.size()>1){
//							System.out.println("error processing: " + wrongCommand.toString());
//						}
//						stop = true;
//						break;
//
//					//display the current status of the Critter world 
//					case "show": 
//						if(inputArgs.size()>1){
//							System.out.println("error processing: " + wrongCommand.toString());
//						} else {
//							Critter.displayWorld();
//						}
//						break;
//					
//					//executes a certain number of time steps
//					case "step": 
//						int count = 0;
//						if(!(inputArgs.size() == 1 || inputArgs.size() == 2)) {
//							System.out.println("error processing: " + wrongCommand.toString());
//						}
//						else if (inputArgs.size() == 2) {
//							if(Integer.parseInt(inputArgs.get(1)) < 0){
//								System.out.println("error processing: " + wrongCommand.toString());
//							}
//							else{
//								while (count < Integer.parseInt(inputArgs.get(1))) {
//									 Critter.worldTimeStep();
//									 count++;
//								}
//							}
//					    } else {
//					    	Critter.worldTimeStep();
//					    }
//						break;
//
//					//changes the seed number for the random number generator
//					case "seed": 
//						if(inputArgs.size() != 2){
//							System.out.println("error processing: " + wrongCommand.toString());
//						}
//						else if(Integer.parseInt(inputArgs.get(1)) < 0){
//							System.out.println("error processing: " + wrongCommand.toString());
//						}
//						else{
//							Critter.setSeed(Integer.parseInt(inputArgs.get(1)));
//						}
//						break;
//
//					//creates a concrete class of Critter
//					case "make": 
//						int count2 = 0;
//						if(!(inputArgs.size() == 2 || inputArgs.size() == 3)) {
//							System.out.println("error processing: " + wrongCommand.toString());
//						}
//						else if (inputArgs.size() == 3) {
//							if(Integer.parseInt(inputArgs.get(2)) < 0){
//								System.out.println("error processing: " + wrongCommand.toString());
//							}
//							else{
//								while (count2 < Integer.parseInt(inputArgs.get(2))) {
//									Critter.makeCritter(inputArgs.get(1));
//									count2++;
//								}
//							}
//						} else {
//							Critter.makeCritter(inputArgs.get(1));
//						}
//						 break;
//
//					//shows the statistics for a certain concrete class of Critters
//					case "stats": 
//						if(inputArgs.size() != 2){
//							System.out.println("error processing: " + wrongCommand.toString());
//						}
//						else{
//							List<Critter> critList = Critter.getInstances(inputArgs.get(1));
//							Class critterClass;
//							Method method;
//							
//							try {
//								critterClass = Class.forName(myPackage + "." + inputArgs.get(1));
//								method = critterClass.getMethod("runStats", List.class);
//							} catch (ClassNotFoundException e) {
//								// TODO Auto-generated catch block
//								throw new InvalidCritterException(inputArgs.get(1));
//							}
//							
//							method.invoke(critterClass, critList);
//						}
//						break;
//					default: 					
//						System.out.println("invalid command: " + wrongCommand.toString());
//			}
//				
//	    	}catch(NumberFormatException n){
//	    		System.out.print("error processing: " + wrongCommand.toString() + "\n");
//	    	}catch(InvalidCritterException c){
//	    		System.out.print("error processing: " + wrongCommand.toString() + "\n");
//	    	}catch(NoSuchMethodException e) {
//	    		System.out.print("error processing: " + wrongCommand.toString() + "\n");
//	    	}catch(InvocationTargetException e) {
//	    		System.out.print("error processing: " + wrongCommand.toString() + "\n");
//	    	}catch(IllegalAccessException e) {
//	    		System.out.print("error processing: " + wrongCommand.toString() + "\n");
//	    	}
//    	}

        /* Do not alter the code above for your submission. */
        /* Write your code below. */
        
        // System.out.println("GLHF");
        
        /* Write your code above */
        //System.out.flush();
    	String assignmentPath = System.getProperty("user.dir") + "\\src\\" + myPackage;
		System.out.println("Working Directory = " + assignmentPath);
		
		File f = new File(assignmentPath);
		
		File[] files = f.listFiles();
		for (File file : files) {
			if (file.getName().endsWith(".java")) {
				String fileName = file.getName().substring(0, file.getName().length() - 5);
				try {
					Critter.getCritterFromString(myPackage + "." + fileName);
					arr.add(fileName);
					System.out.println(fileName);
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
		// TODO Auto-generated method stub
		GridPane grid = new GridPane();
		
		//grid.setGridLinesVisible(true);
		//System.out.println(grid.isGridLinesVisible());

//		Scene scene = new Scene(grid, 570, 570); //40 and 40 added for gaps, 20 (30?) added for padding
		int row = 20;
		
		//Scene scene = new Scene(grid, 560, 560);
		
		Scene scene = new Scene(grid, 910, 910);
		
		int gridSize = 900 - 10*row;
		
		System.out.println(grid.getHeight());
		System.out.println(grid.getWidth());
		
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
		
//		NumberBinding rectsAreaSize = Bindings.min(grid.heightProperty(), grid.widthProperty());
//		NumberBinding rectHeight = Bindings.add(scene.heightProperty(), 0);

		for (int i = 0; i < row; i++) {
			for (int j = 0; j < row; j++) {
				Rectangle rect = new Rectangle(gridSize/row, gridSize/row);
				rect.setFill(javafx.scene.paint.Color.WHITE);
				
				//grid.setAlignment(Pos.CENTER);
				
				rect.setStroke(javafx.scene.paint.Color.BLACK);
				rect.setStrokeWidth(2);
				
//				rect.xProperty().bind(rectsAreaSize.multiply(i).divide(5));
//	            rect.yProperty().bind(rectsAreaSize.multiply(j).divide(5));
//
//	            // here we bind rectangle size to pane size 
//	            rect.heightProperty().bind(new SimpleDoubleProperty((row.getPercentHeight()/100)*rectHeight.doubleValue()).asObject());
//	            rect.widthProperty().bind(new SimpleDoubleProperty((column.getPercentWidth()/100)*scene.getWidth()).asObject());

	            //grid.getChildren().add(rect);
	            grid.add(rect, i, j); // add the shape to the grid.
			}
		}
		
		primaryStage.setScene(scene);
		//primaryStage.setWidth(570);
		//primaryStage.setHeight(570);
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
		System.out.println(arr.toString());
		cb.getItems().addAll(arr);
		
		TextField seedText = new TextField();
		TextField timeText = new TextField();
		TextField critText = new TextField();
		critText.setMaxWidth(60);
		//critText.setPrefWidth(30);
		Slider animSpeed = new Slider(0, 10, 2);
		animSpeed.setMinorTickCount(0);	
		animSpeed.setShowTickMarks(true);
		animSpeed.setShowTickLabels(true);
		animSpeed.setMajorTickUnit(2);
		animSpeed.setSnapToTicks(true);
		
		
		
		Button seedBut = new Button();
		seedBut.setText("SUBMIT");
		
		Button critBut = new Button();
		critBut.setText("SUBMIT");
		critBut.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				int critCount = Integer.parseInt(critText.getText());
				// TODO Auto-generated method stub
				while (critCount > 0) {
					try {
						Critter.makeCritter(myPackage + "." + cb.getButtonCell());
					} catch (InvalidCritterException e) {
						// TODO Auto-generated catch block
					}
					critCount--;
				}
			}
			
		});
		
		Button timeBut = new Button();
		timeBut.setText("SUBMIT");
		
		Button quitBut = new Button();
		quitBut.setText("QUIT");
		
		Button startAnimBut = new Button();
		startAnimBut.setText("START/STOP ANIMATING");
		
		grid2.setPadding(new Insets(10, 25, 10, 25));
		grid2.addRow(0, new Label("SET SEED"), seedText, seedBut);
		grid2.addRow(1, new Label("ADD CRITTER"), cb, critBut);
		grid2.add(critText, 1, 2);
		grid2.addRow(3);
		grid2.addRow(4, new Label("SET TIMESTEPS"), timeText, timeBut);
		grid2.addRow(5, new Label("SET ANIMATION SPEED"), animSpeed);
		grid2.add(startAnimBut, 1, 6);
		grid2.addRow(7);
		grid2.addRow(8, quitBut);
		
		
		
		GridPane grid3 = new GridPane();
		//grid3.setGridLinesVisible(true);
		Scene scene3 = new Scene(grid3, 590, 300);
		
		RowConstraints row4 = new RowConstraints(50);
		RowConstraints row5 = new RowConstraints(220);
        
        grid3.getRowConstraints().add(row4);
        grid3.getRowConstraints().add(row5);
        
        ColumnConstraints column4 = new ColumnConstraints(540);
        
        grid3.getColumnConstraints().add(column4);
		grid3.setVgap(10);
		grid3.setPadding(new Insets(10, 25, 10, 25));
		
//		List<Critter> critList = Critter.getInstances(inputArgs.get(1));
//		Class critterClass;
//		Method method;
//		
//		try {
//			critterClass = Class.forName(myPackage + "." + inputArgs.get(1));
//			method = critterClass.getMethod("runStats", List.class);
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			throw new InvalidCritterException(inputArgs.get(1));
//		}
//		
//		method.invoke(critterClass, critList);
		
		Stage tertiaryStage = new Stage();
		tertiaryStage.setScene(scene3);
		tertiaryStage.show();
		
		ComboBox cb2 = new ComboBox();
		System.out.println(arr.toString());
		cb2.getItems().addAll(arr);
		
		TextArea statsText = new TextArea();
		
		grid3.addRow(0, cb2);
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
