package assignment4;
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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console


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
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));			
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }

        boolean stop = false; //keeps track of whether quit command has occurred 
    	
    	while(!stop){
    		//prompts the user the gets their input 
    		System.out.print("critters> ");
		    ArrayList<String> inputArgs = new ArrayList<String>();
		    String wrongCommand = kb.nextLine();
		    inputArgs.addAll(Arrays.asList(wrongCommand.split("\\s+")));
			    
			String command = inputArgs.get(0);
			
			try{
				switch (command) {
					//stop the program
					case "quit": 
						if(inputArgs.size()>1){
							System.out.println("error processing: " + wrongCommand.toString());
						}
						stop = true;
						break;

					//display the current status of the Critter world 
					case "show": 
						if(inputArgs.size()>1){
							System.out.println("error processing: " + wrongCommand.toString());
						} else {
							Critter.displayWorld();
						}
						break;
					
					//executes a certain number of time steps
					case "step": 
						int count = 0;
						if(!(inputArgs.size() == 1 || inputArgs.size() == 2)) {
							System.out.println("error processing: " + wrongCommand.toString());
						}
						else if (inputArgs.size() == 2) {
							if(Integer.parseInt(inputArgs.get(1)) < 0){
								System.out.println("error processing: " + wrongCommand.toString());
							}
							else{
								while (count < Integer.parseInt(inputArgs.get(1))) {
									 Critter.worldTimeStep();
									 count++;
								}
							}
					    } else {
					    	Critter.worldTimeStep();
					    }
						break;

					//changes the seed number for the random number generator
					case "seed": 
						if(inputArgs.size() != 2){
							System.out.println("error processing: " + wrongCommand.toString());
						}
						else if(Integer.parseInt(inputArgs.get(1)) < 0){
							System.out.println("error processing: " + wrongCommand.toString());
						}
						else{
							Critter.setSeed(Integer.parseInt(inputArgs.get(1)));
						}
						break;

					//creates a concrete class of Critter
					case "make": 
						int count2 = 0;
						if(!(inputArgs.size() == 2 || inputArgs.size() == 3)) {
							System.out.println("error processing: " + wrongCommand.toString());
						}
						else if (inputArgs.size() == 3) {
							if(Integer.parseInt(inputArgs.get(2)) < 0){
								System.out.println("error processing: " + wrongCommand.toString());
							}
							else{
								while (count2 < Integer.parseInt(inputArgs.get(2))) {
									Critter.makeCritter(inputArgs.get(1));
									count2++;
								}
							}
						} else {
							Critter.makeCritter(inputArgs.get(1));
						}
						 break;

					//shows the statistics for a certain concrete class of Critters
					case "stats": 
						if(inputArgs.size() != 2){
							System.out.println("error processing: " + wrongCommand.toString());
						}
						else{
							List<Critter> critList = Critter.getInstances(inputArgs.get(1));
							Class critterClass;
							Method method;
							
							try {
								critterClass = Class.forName(myPackage + "." + inputArgs.get(1));
								method = critterClass.getMethod("runStats", List.class);
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								throw new InvalidCritterException(inputArgs.get(1));
							}
							
							method.invoke(critterClass, critList);
						}
						break;
					default: 					
						System.out.println("invalid command: " + wrongCommand.toString());
			}
				
	    	}catch(NumberFormatException n){
	    		System.out.print("error processing: " + wrongCommand.toString() + "\n");
	    	}catch(InvalidCritterException c){
	    		System.out.print("error processing: " + wrongCommand.toString() + "\n");
	    	}catch(NoSuchMethodException e) {
	    		System.out.print("error processing: " + wrongCommand.toString() + "\n");
	    	}catch(InvocationTargetException e) {
	    		System.out.print("error processing: " + wrongCommand.toString() + "\n");
	    	}catch(IllegalAccessException e) {
	    		System.out.print("error processing: " + wrongCommand.toString() + "\n");
	    	}
    	}

        /* Do not alter the code above for your submission. */
        /* Write your code below. */
        
        // System.out.println("GLHF");
        
        /* Write your code above */
        System.out.flush();

    }
}
