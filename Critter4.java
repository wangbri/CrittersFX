package assignment5;
/* CRITTERS Critter4.java
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
 * A custom Critter that acts according to chance; tries to reproduce and run away if it encounters another Critter 
 */
public class Critter4 extends Critter{

	
	@Override
	/**
	 * Prints 4 as its symbol
	 */
	public String toString() { return "4"; }
	
	@Override
	/**
	 * Rolls a dice and decides its actions based on the value rolled on the dice 
	 */
	public void doTimeStep() {
		int dice = Critter.getRandomInt(120);//roll the dice to see what it does
		
		if(dice < 40){
			walk(dice%8);
		}
		else if(dice>80){
			run(dice%8);
		}
		else{
			Critter4 baby = new Critter4();
			reproduce(baby, dice%8);
		}
		
	}

	
	@Override
	/**
	 * If it encounters any Critter, it tries to reproduce as much as possible before running away
	 */
	public boolean fight(String opponent) {
		//keep reproducing if it has enough energy
		int directionCount = 0;
		while(getEnergy()>20){
			Critter4 baby4 = new Critter4();
			reproduce(baby4,(directionCount+1)%8);
		}
		
		//attempts to walk away if it has enough energy after reproducing
		if(getEnergy()>10){
			walk(Critter.getRandomInt(8));
		}
		return false;
	}

}
