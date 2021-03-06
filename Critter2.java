package assignment5;
/* CRITTERS Critter2.java
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

import assignment5.Critter.CritterShape;

/**
 * A custom Critter that reproduces Critters around it when encountered and runs away afterwards
 */
public class Critter2 extends Critter {

	@Override
	public void doTimeStep() {
		run(Critter.getRandomInt(8));
	}

	@Override
	public boolean fight(String opponent) {
		for (int i = 0; i < 8; i++) {
			Critter2 crit = new Critter2();
			reproduce(crit, i); // reproduces in all positions adjacent to it 
		}
		
		run(Critter.getRandomInt(8)); // runs away when encountered
	
		return false;
	}
	
	public String toString() {
		return "2";
	}

	@Override
	public CritterShape viewShape() {
		// TODO Auto-generated method stub
		return CritterShape.CIRCLE;
	}
	
	@Override
	public javafx.scene.paint.Color viewOutlineColor() { return javafx.scene.paint.Color.LAVENDER; }
	
	@Override
	public javafx.scene.paint.Color viewFillColor() { return javafx.scene.paint.Color.LAVENDER; }
}