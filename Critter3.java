package assignment5;
/* CRITTERS Critter3.java
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

/**
 * A custom Critter that travels in a certain direction for a certain number of times before changing its direction 
 * Reproduce if it encounters another Critter3, fight an Algae, and avoid all other Critters
 */
public class Critter3 extends Critter{
	private int direction;
	private int turnCount;
	private int turn; 
	
	/**
	 * Create a Critter3. Set the direction it will travel and the number of times it will travel in that direction 
	 */
	public Critter3(){
		
		direction = Critter.getRandomInt(8);
		turnCount = Critter.getRandomInt(50);
		turn = 0;	
	}

	@Override
	/**
	 * Prints 3 as its symbol
	 */
	public String toString() { return "3"; }
	
	@Override
	/**
	 * Walks in a specified direction for some number of turns before changing direction
	 */
	public void doTimeStep() {
		//walk in the specified direction for some number of turns
		if(turn<turnCount){
			walk(direction);
			turn++;
		}
		
		//change direction and run once it has been walking in that direction for the specified time
		else{
			turn = 0;
			direction = Critter.getRandomInt(8);
			if(look(direction, true).equals("@")){
				run(direction);
			}
			turn++;
		}
		
	}

	@Override
	/**
	 * Reproduce if it meets another Critter3, fight if it encounters Algae, and runs away for all other Critters 
	 */
	public boolean fight(String opponent) {
		//reproduce if it meets the same critter as itself
		if(opponent.equals(this.toString())){
			Critter3 baby3 = new Critter3();
			reproduce(baby3, Critter.getRandomInt(8));
			return true;
		}
		
		//fight if it encounters an algae 
		else if(opponent.equals("@")){
			return true;
		}
		
		//run away if it encounters anything else
		else{
			run(direction);
			turn++;
			return false;
		}
		
	}

	@Override
	public CritterShape viewShape() {
		// TODO Auto-generated method stub
		 return CritterShape.STAR;
	}
	
	@Override
	public javafx.scene.paint.Color viewOutlineColor() { return javafx.scene.paint.Color.RED; }
	
	@Override
	public javafx.scene.paint.Color viewFillColor() { return javafx.scene.paint.Color.RED; }

}
