package assignment5;
/* CRITTERS Critter1.java
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
 * A custom Critter that doesn't fight Critters of the same class and on encountering another Critter1, runs
 * and then becomes immobilized
 */
public class Critter1 extends Critter {
	public boolean canWalk = true;

	@Override
	public void doTimeStep() {
		if (canWalk) {
			walk(Critter.getRandomInt(8));
		}
	}

	@Override
	public boolean fight(String opponent) {
		Class opp;

		if (opponent.equals(this.toString())) {
			canWalk = false;
			run(Critter.getRandomInt(8));
			return false;
		}

		return true;
	}
	
	public String toString() {
		return "1";
	}
}