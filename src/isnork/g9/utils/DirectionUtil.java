package isnork.g9.utils;

import java.util.ArrayList;

import isnork.sim.GameObject.Direction;

public class DirectionUtil {
	private static ArrayList<Direction> allDirections = Direction.allBut(null);
	
	public static Direction getPreviousDirection(Direction myD) {
		Direction prevD = allDirections.get(7);
		Direction outD = null;
		for ( Direction d : allDirections) {
			if (d.equals(myD)) {
				outD = prevD;
			}
			else {
				prevD = d;
			}
		}
		return outD;
	}
	
	public static Direction getNextDirection(Direction myD) {
		Direction d = null;
		Direction nextD = allDirections.get(0);
		Direction outD = null;
		for (int i=7; i>=0; i--) {
			d = allDirections.get(i);
			if (d.equals(myD)) {
				outD = nextD;
			}
			else {
				nextD = d;
			}
		}
		return outD;
	}
	
	public static Direction getDirectionFromIndex(int index) {
		return allDirections.get(index);
	}
	
	public static int getIndexFromDirection(Direction dir) {
		return allDirections.indexOf(dir);
	}

}
