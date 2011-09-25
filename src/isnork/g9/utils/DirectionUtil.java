package isnork.g9.utils;

import isnork.sim.GameObject.Direction;

import java.awt.geom.Point2D;
import java.util.ArrayList;

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
	
	public static int getRelativeOctant(Point2D myPos, Point2D target){
		//Simulator id screwed up. The Y axis is flipped
		double thetaRad = Math.atan2(myPos.getY()-target.getY(), target.getX()-myPos.getX());
		double thetaDeg = thetaRad * 180 / Math.PI;
		if(thetaDeg < 0 ) thetaDeg += 360;
		return ((int)thetaDeg)/45 + ( ((int) thetaDeg)%45 < 23 ? 0 : 1);
	}

	public static Direction getDirectionFromIndex(int index) {
		return allDirections.get(index);
	}
	
	public static int getIndexFromDirection(Direction dir) {
		return allDirections.indexOf(dir);
	}

}
