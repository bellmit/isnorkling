package isnork.g9.utils;

import java.util.ArrayList;

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
		
		//Failed;
		return -1;
	}
	
}
