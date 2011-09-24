package isnork.g9.strategy;

import isnork.g9.Diver;
import isnork.g9.PlayerPrototype;
import isnork.g9.utils.BoardParams;
import isnork.g9.utils.DirectionUtil;
import isnork.g9.utils.Parameter;
import isnork.sim.Observation;
import isnork.sim.GameObject.Direction;


import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class GlobalCircleStrategy implements StrategyPrototype {
	
	Diver diver;
	Point2D location;
	private Random rand = new Random(); // For testing


	@Override
	public void setBoardParams(BoardParams b) {

	}

	// To choose the next direction, 5 directions are considered (the last direction, and 2 on
	// either side of it). Of those 5, the direction is returned that is truest to the diver's
	// desired global exploration strategy (specifically, that minimizes the offset between
	// itself and the desired circle radius).
	@Override
	public Direction getDirection() {
		Direction lastDir, nextDir, prevDir, nextNextDir, prevPrevDir;
		
		lastDir = diver.getLastDirection();
		nextDir = DirectionUtil.getNextDirection(lastDir);
		nextNextDir = DirectionUtil.getNextDirection(nextDir);
		prevDir = DirectionUtil.getPreviousDirection(lastDir);
		prevPrevDir = DirectionUtil.getPreviousDirection(prevDir);
		
		ArrayList<Direction> possDirs = new ArrayList<Direction>();
		possDirs.add(prevPrevDir);
		possDirs.add(prevDir);
		possDirs.add(lastDir);
		possDirs.add(nextDir);
		possDirs.add(nextNextDir);
		
		// Compare each of the 5 directions to see which one brings us closest to the radius
		Direction bestDir = null;
		double directDist = 0;
		double minDelta = 10000;
		double currDelta = 0;
		for (Direction dir : possDirs) {
			try {
				directDist = calcDistFromOrigin(calcLocFromOffset(location,
						dir));
			} catch (NullPointerException e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			currDelta = Math.abs(directDist - diver.getDesiredRadius());
			if (currDelta < minDelta) {
				minDelta = currDelta;
				bestDir = dir;
			}
		}
		// Randomly perturb 20% of the time
		double prob = rand.nextDouble();
		if (prob <= Parameter.GLOBAL_CIRCLE_STRATEGY_PERTURBATION_LIKELIHOOD) {
			int rint = rand.nextInt(Parameter.GLOBAL_CIRCLE_STRATEGY_MAX_PERTURBATION_DISTANCE);
			if (rand.nextDouble() > 0.5) {
				rint *= -1;
			}
			int newDirectionIndex = rint + DirectionUtil.getIndexFromDirection(bestDir);
			newDirectionIndex = newDirectionIndex %= 7;
			if (newDirectionIndex < 0) {
				newDirectionIndex += 8;
			}
			bestDir = DirectionUtil.getDirectionFromIndex(newDirectionIndex);
		}
		return bestDir;
	}

	// Returns the absolute distance of a given point from the origin.
	private double calcDistFromOrigin(Point2D pt) {
		return Math.sqrt( Math.pow(pt.getX(),2) + Math.pow(pt.getY(),2) );
	}

	// Returns a new location, given a starting location and a direction.
	private Point2D calcLocFromOffset(Point2D loc, Direction dir) {
		try {
			return new Point2D.Double( loc.getX() + dir.getDx(), loc.getY() + dir.getDy() );
		} catch (NullPointerException e) { // Occurs after STAYPUT
			return new Point2D.Double( loc.getX(), loc.getY());
		}
	}

	@Override
	public void setLocation(Point2D loc) {
		this.location = loc;
	}

	@Override
	public void setPlayer(PlayerPrototype p) {
		this.diver = (Diver) p;

	}

	@Override
	public void setOverallStrategy(Strategy s) {

	}

	@Override
	public double getConfidence() {
		return Parameter.GLOBAL_CIRCLE_STRATEGY_CONFIDENCE;
	}

	@Override
	public void setSighting(Set<Observation> s) {

	}

}
