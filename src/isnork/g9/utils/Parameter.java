package isnork.g9.utils;

import isnork.sim.GameObject.Direction;

public class Parameter {
	public static double DANGER_ESTIMATION_DROPPING_FACTOR = 0.2;
	public static final Direction[] ALL_DIRS = new Direction[] {Direction.E, Direction.NE,
		Direction.N, Direction.NW, Direction.W, Direction.SW, Direction.S, Direction.SE,
		Direction.STAYPUT}; ;
	
	
	public static int DIRECTION_LOOKUP(Direction d) {
		
		int counter = 0;
		for (Direction cur : ALL_DIRS) {
			if (cur.equals(d)) {
				return counter;
			}
			counter++;
		}
		
		//Stationary;
		return 0;
	}
	
	public static final double GLOBAL_CIRCLE_STRATEGY_CONFIDENCE = 1;
	public static final double GLOBAL_CIRCLE_STRATEGY_PERTURBATION_LIKELIHOOD = 0.1;
	public static final int GLOBAL_CIRCLE_STRATEGY_MAX_PERTURBATION_DISTANCE = 2;
	public static final int RETURN_WINDOW = 60;
	public static final int GAME_LENGTH = 480;
	
	// Utility function coefficients
	public static final double ESCAPE_CONFIDENCE_COEFFICIENT = 10;
	public static final double GLOBAL_STRATEGY_CONFIDENCE_COEFFICIENT = 6;
	public static final double RETURNING_CONFIDENCE_COEFFICIENT = 15;
	public static final double COMMUNICATION_CONFIDENCE_COEFFICIENT = 5;
}
