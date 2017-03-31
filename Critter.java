package assignment5;
/* CRITTERS Critter.java
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


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.*;
import javafx.*;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	private static String myPackage;
	private boolean has_moved = false;
	private boolean is_fighting = false;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	/* NEW FOR PROJECT 5 */
	public enum CritterShape {
		CIRCLE,
		SQUARE,
		TRIANGLE,
		DIAMOND,
		STAR
	}
	
	/* the default color is white, which I hope makes critters invisible by default
	 * If you change the background color of your View component, then update the default
	 * color to be the same as you background 
	 * 
	 * critters must override at least one of the following three methods, it is not 
	 * proper for critters to remain invisible in the view
	 * 
	 * If a critter only overrides the outline color, then it will look like a non-filled 
	 * shape, at least, that's the intent. You can edit these default methods however you 
	 * need to, but please preserve that intent as you implement them. 
	 */
	public javafx.scene.paint.Color viewColor() { 
		return javafx.scene.paint.Color.WHITE; 
	}
	
	public javafx.scene.paint.Color viewOutlineColor() { return viewColor(); }
	public javafx.scene.paint.Color viewFillColor() { return viewColor(); }
	
	public abstract CritterShape viewShape(); 
	

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	protected final String look(int direction, boolean steps) {
		energy -= Params.look_energy_cost;
		int[] location = new int[2];
		if(is_fighting){
			location[0] = old_x;
			location[1] = old_y;
		}
		else{
			location[0] = x_coord;
			location[1] = y_coord;
		}
		
		
		if(steps){
			direction_change(location, direction);
		}
		direction_change(location, direction);
		for(int i = 0; i < population.size(); i++){
			if(population.get(i).x_coord == location[0] && population.get(i).y_coord == location[1] && population.get(i).energy > 0){
				return population.get(i).toString();
			}
		}
		
		return "";
	}

	/* PROJECT 4 BEGINS HERE */

	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	private int old_x;
	private int old_y;
	
	/**
	 * Allows Critter to move one spot in one time step
	 * @param direction: The direction the Critter will move in
	 */
	protected final void walk(int direction) {
		if(!has_moved){
			int[] location = {x_coord, y_coord};
			direction_change(location, direction);
			if(is_fighting){
				if(!OccupiedLocation(location[0], location[1])){
					x_coord = location[0];
					y_coord = location[1];
					has_moved = true;
				}
			}
			else{
				x_coord = location[0];
				y_coord = location[1];
				has_moved = true;
			}
		}
		energy -= Params.walk_energy_cost;
	}
	
	/**
	 * Allows Critter to move two spots in the same direction in one time step
	 * @param direction: The direction the Critter will move in
	 */
	protected final void run(int direction) {
		if(!has_moved){
			int[] location = {x_coord, y_coord};
			direction_change(location, direction);
			direction_change(location, direction);
			if(is_fighting){
				if(!OccupiedLocation(location[0], location[1])){
					x_coord = location[0];
					y_coord = location[1];
					has_moved = true;
				}
			}
			else{
				x_coord = location[0];
				y_coord = location[1];
				has_moved = true;
			}
		}
		energy -= Params.run_energy_cost;
	}
	
	/**
	 * Checks to see if the location is occupied by a living Critter
	 * @param x: x coordinate of the location 
	 * @param y: y coordinate of the location
	 * @return true if the location is occupied; false otherwise
	 */
	public static boolean OccupiedLocation(int x, int y){
		for(int i = 0; i < population.size(); i++){
			if(population.get(i).x_coord == x && population.get(i).y_coord == y && population.get(i).energy > 0){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Changes the location of the Critter base on the direction 
	 * @param location: an int array containing current location of the Critter, 
	 * 			first element is x coordinates and second element is y coordinates
	 * @param direction: direction that the Critter should move in 
	 * @return an int array with the modified location - first element is x coordinate; 
	 *  		second element is y coordinate
	 */
	public static int[] direction_change(int[] location, int direction){
		if(direction == 1 || direction == 0 || direction == 7){
			location[0]++;
		}
		if(direction == 5 || direction == 4 || direction == 3){
			location[0]--;
		}
		if(direction == 5 || direction == 6 || direction == 7){
			location[1]--;
		}
		if(direction == 3 || direction == 2 || direction == 1){
			location[1]++;
		}
		
		// check for out of bounds access, then wrap movement
		if(location[0] > Params.world_width - 1) {
			location[0] = 0;
		} else if (location[0] < 0) {
			location[0] = Params.world_width - 1;
		}
		
		if(location[1] > Params.world_height - 1) {
			location[1] = 0;
		} else if (location[1] < 0) {
			location[1] = Params.world_height - 1;
		}
		
		return location;
	}
	
	/**
	 * Allows Critter to reproduce an offspring
	 * @param offspring: the offspring of the Critter that called this method
	 * @param direction: the direction to place its offspring in relation to the calling
	 * 			Critter current location
	 */
	protected final void reproduce(Critter offspring, int direction) {
		if(this.energy < Params.min_reproduce_energy){
			return;
		} 
		else{	
			offspring.energy = this.energy/2;
			this.energy -= offspring.energy;
			offspring.x_coord = this.x_coord;
			offspring.y_coord = this.y_coord;
			int[] location = {offspring.x_coord, offspring.y_coord};
			direction_change(location, direction);
			offspring.x_coord = location[0];
			offspring.y_coord = location[1];
			babies.add(offspring);
		}
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		critter_class_name = Critter.myPackage + "." + critter_class_name;
		Critter newCritter = getCritterFromString(critter_class_name);
		newCritter.x_coord = getRandomInt(Params.world_width);
		newCritter.y_coord = getRandomInt(Params.world_height);
		newCritter.energy = Params.start_energy;
		population.add(newCritter);
	}
	
	/**
	 * Converts a string into a concrete Critter class
	 * @param critter_class_name: string indicating which class it is referring to
	 * @return the concrete Critter class as indicated by critter_class_name
	 * @throws InvalidCritterException
	 */
	public static Critter getCritterFromString (String critter_class_name) throws InvalidCritterException {
		Class<?> myCritter = null;
		Constructor<?> constructor = null;
		Object instanceOfMyCritter = null;

		try {
			myCritter = Class.forName(critter_class_name); 	// Class object of specified name
		} catch (ClassNotFoundException e) {
			throw new InvalidCritterException(critter_class_name);
		}
		try {
			constructor = myCritter.getConstructor();		// No-parameter constructor object
			instanceOfMyCritter = constructor.newInstance();	// Create new object using constructor
		} catch (NoSuchMethodException e) {
			throw new InvalidCritterException(critter_class_name);
		} catch (InvocationTargetException e) {
			throw new InvalidCritterException(critter_class_name);
		} catch (IllegalAccessException e) {
			throw new InvalidCritterException(critter_class_name);
		} catch (InstantiationException e) {
			throw new InvalidCritterException(critter_class_name);
		}
		
		Critter me = (Critter)instanceOfMyCritter;		// Cast to Critter
		return me;
	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException{
		List<Critter> result = new java.util.ArrayList<Critter>();
		Constructor<?> constructor = null;
		critter_class_name = Critter.myPackage + "." + critter_class_name;
		
		try {
			Class<?> critterClass = Class.forName(critter_class_name);
			if (Modifier.isAbstract(critterClass.getModifiers())) {
				throw new InvalidCritterException(critter_class_name);
			}
			constructor = critterClass.getConstructor(); // check if class is concrete
			for (int i = 0; i < population.size(); i++) {
				if (critterClass.isInstance(population.get(i))) {
					result.add(population.get(i));
				}
			}
		} catch (ClassNotFoundException e) {
			throw new InvalidCritterException(critter_class_name);
		} catch (NoSuchMethodException e) {
			throw new InvalidCritterException(critter_class_name);
		} catch (SecurityException e) {
			throw new InvalidCritterException(critter_class_name);
		}		
		
		return result;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static String runStats(List<Critter> critters) {
		String output = "";
		
		output += "" + critters.size() + " critters as follows -- ";
		//System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			//System.out.print(prefix + s + ":" + critter_count.get(s));
			output += prefix + s + ":" + critter_count.get(s);
			prefix = ", ";
		}
		
		return output;
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		population.clear();

	}

	public static void store_old_location(){
		for(int i = 0; i < population.size(); i++){
			population.get(i).old_x = population.get(i).x_coord;
			population.get(i).old_y = population.get(i).y_coord;
		}
	}
	
	/**
	 * Simulate one time step for all the critters 
	 */
	public static void worldTimeStep() {
		//store all of Critter's location before they move
		store_old_location();		
		
		//invoke time steps for each critter 
		for(int i = 0; i < population.size(); i++){
			population.get(i).doTimeStep();
		}
		
		//Stage 2 implementation of encounters
		findEncounters();
		handleEncounters();
		
		//update energy and remove dead critters
		for(int j = 0; j < population.size(); j++){
			population.get(j).has_moved = false;
			population.get(j).is_fighting = false;
			population.get(j).energy -= Params.rest_energy_cost;
			if(population.get(j).energy <= 0){
				population.remove(j);
				j--;
			}
		}

		//algae creation
		for(int k = 0; k < Params.refresh_algae_count; k++){
			Critter newAlgae = new Algae();
			newAlgae.x_coord = getRandomInt(Params.world_width);
			newAlgae.y_coord = getRandomInt(Params.world_height);
			newAlgae.energy = Params.start_energy;
			population.add(newAlgae);
		}
		
		//add all the offsprings
		population.addAll(babies);
		babies.clear();
		
		
	}
	
	//list of critter encounters
	private static Map<String,ArrayList<Critter>> critEncounters = new HashMap<String,ArrayList<Critter>>();
	//boolean existingEncounter = false;

	/**
	 * Searches through the Critter world to look for any encounters
	 */
	public static void findEncounters() {
		for (int i = 0; i < population.size(); i++) {
			Critter critA = population.get(i);
			if (critA.energy > 0) {
				// list of critter encounters with critA
				ArrayList<Critter> critEncounter = new ArrayList<Critter>();
				boolean encounterFound = false;
				
				if (critEncounters.containsKey(String.valueOf(critA.x_coord) + " " + String.valueOf(critA.y_coord))) {
					if (!critEncounters.get(String.valueOf(critA.x_coord) + " " + String.valueOf(critA.y_coord)).contains(critA)) {
						for (int j = 0; j < population.size(); j++) {
							Critter critB = population.get(j);
							// critter can't encounter itself
							if (i == j || critB.energy <= 0) {
								break;
							}		
		
							// found critter encounter
							if (critA.x_coord == critB.x_coord && critA.y_coord == critB.y_coord) {	
								if (!critEncounters.get(String.valueOf(critA.x_coord) + " " + String.valueOf(critA.y_coord)).contains(critB)) {
									if (!encounterFound) {
										critEncounter.add(critA);
										encounterFound = true;
									}
									critEncounter.add(critB);
				
									//System.out.println("FOUND ENCOUNTER @ " + String.valueOf(critA.x_coord) + "," + String.valueOf(critA.y_coord));
								}
							}
						}
					}
				}
				critEncounters.put(String.valueOf(critA.x_coord) + " " + String.valueOf(critA.y_coord), critEncounter);
			}
		}
		//System.out.println(critEncounters.toString());
//		System.out.println("ENCOUNTERS " + critEncounters.get("51").size());
	}
	
	/**
	 * Invoke the fight method for any encounters found and resolve all encounters 
	 * in the Critter world 
	 */
	public static void handleEncounters() {
		ArrayList<Critter> graveyard = new ArrayList<Critter>();
		
		for (ArrayList<Critter> encounter : critEncounters.values()) {
			// iterate through all critters in each encounter
			for (int j = 0; j < encounter.size() - 1; j++) {
				Critter critj = encounter.get(j);
				
				// if critj is not dead, handle encounters for critj
				if (!graveyard.contains(critj)) {
					for (int k = j; k < encounter.size() - 1; k++) {						
						int critjRoll = 0;
						int critkRoll = 0;
						
						Critter critk = encounter.get(k + 1);
						
						int[] critjLoc = {critj.x_coord, critj.y_coord};
						int[] critkLoc = {critk.x_coord, critk.y_coord};
						
						critk.is_fighting = true;
						critj.is_fighting = true;
							
						// if critter j wants to fight critter k
						boolean critjHostile = critj.fight(critk.toString());
						// if critter k wants to fight critter j
						boolean critkHostile = critk.fight(critj.toString());
						
						// check if critters died before fighting
						if (!(critj.energy > 0)) {
							graveyard.add(critj);
							break;
						}
						
						if (critk.energy > 0) {
		
							// if critj tries to run away, end encounter
							if (critj.x_coord != critjLoc[0] || critj.y_coord != critjLoc[1]) {
								break; 
							}
							
							// if critk tries to run away, end encounter
							if (critk.x_coord != critkLoc[0] || critk.y_coord != critkLoc[1]) {
								break;
							}
	
							if (critjHostile) {
								critjRoll = Critter.getRandomInt(critj.energy + 1);
								
							} else {
								critjRoll = 0;
							}
							
							if (critkHostile) {
								critkRoll = Critter.getRandomInt(critk.energy + 1);
							} else {
								critkRoll = 0;
							}
							
							if (critjRoll > critkRoll) {
								critj.energy += critk.energy/2;
								critk.energy = 0;
								graveyard.add(critk);
							} else if (critjRoll < critkRoll) {
								critk.energy += critj.energy/2;
								critj.energy = 0;
								graveyard.add(critj);
								break; // end encounters for critj
							} else {
								critj.energy += critk.energy/2;
								critk.energy = 0;
								graveyard.add(critk);
							}
						} else {
							graveyard.add(critk);
						}
					}
				}
			}
		}
		//System.out.println("NUMBER OF CRITTERS DEAD: " + graveyard.size());
		population.removeAll(graveyard);
		
		Set<Critter> set = new HashSet<Critter>(graveyard);
		//System.out.println("GRAVEYARD SIZE " + set.size());
		//System.out.println(population.size());
		//System.out.println(graveyard.toString());
		critEncounters.clear();
	}
	
	private static int[] getGridDimensions(GridPane grid) {
		Method method;
		Method method2;
		int[] dimensions = {0,0};
		
		try {
			method = grid.getClass().getDeclaredMethod("getNumberOfRows");
			method.setAccessible(true);
			
			method2 = grid.getClass().getDeclaredMethod("getNumberOfColumns");
			method2.setAccessible(true);
			try {
				dimensions[0] = (int) method.invoke(grid);
				dimensions[1] = (int) method2.invoke(grid);
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
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dimensions;
	}
	
	private static Shape getCritterShape(Critter c, double size) {
		Shape s = null;
		CritterShape cs = c.viewShape();
		
		switch (cs) {
			case CIRCLE:
				s = new Circle(size/2);
				break;
			case SQUARE:
				s = new Rectangle(size, size);
				break;
			case TRIANGLE:
				s = new Polygon();
				((Polygon) s).getPoints().addAll(
						0.0, 0.0,
						size, size,
						size/2, size
				);
				break;
			case DIAMOND:
				s = new Polygon();
				((Polygon) s).getPoints().addAll(
						size/2, 0.0,
						size, size/2,
						size/2, size,
						0.0, size/2
				);
				break;
			case STAR:
				s = new Polygon();
				((Polygon) s).getPoints().addAll(
						size/4, size/4, //
						
						size/4, 0.0,
						
						size/2, size/4, //
						
						size*3/4, 0.0,
						
						size*3/4, size/4, //
						
						size, size/2,
						
						size*3/4, size/2, //
						
						size/2, size,
						
						size/4, size/2, //
						
						0.0, size/2
				);
				break;
		}
		
		s.setFill(c.viewFillColor());
		s.setStroke(c.viewOutlineColor());
		
		return s;
	}
	
	/**
	 * Shows the current state of the Critter World 
	 */
	public static void displayWorld(Object o) {
		GridPane grid = (GridPane) o;
		int rows = getGridDimensions(grid)[0];
		int cols = getGridDimensions(grid)[1];
		double size = ((grid.getHeight() - 10*(rows + 1))/rows) - 10;
		//double colSize = grid.getColumnConstraints().get(0).getPrefWidth();
		
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				for (int c = 0; c < population.size(); c++) {
					if (population.get(c).x_coord == i && population.get(c).y_coord == j) {
						Shape s = getCritterShape(population.get(c), size);
						//System.out.println(population.get(c).viewShape());
						grid.add(s, i, j);
						grid.setHalignment(s, HPos.CENTER);
						grid.setValignment(s, VPos.CENTER);
					}
				}
			}
		}
		
	}
}
