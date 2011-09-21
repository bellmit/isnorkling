package isnork.g9.strategy;

import isnork.g9.PlayerPrototype;
import isnork.g9.utils.BoardParams;
import isnork.sim.Observation;
import isnork.sim.GameObject.Direction;

import java.awt.geom.Point2D;
import java.util.Set;

public class RandomWalk implements StrategyPrototype {

	@Override
	public void setBoardParams(BoardParams b) {
		// TODO Auto-generated method stub

	}

	@Override
	public Direction getDirection() {
		// TODO Auto-generated method stub
		return Direction.allBut(null).get((int)(Math.random()*8));
	}

	@Override
	public void setLocation(Point2D loc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPlayer(PlayerPrototype p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setOverallStrategy(Strategy s) {
		// TODO Auto-generated method stub

	}

	@Override
	public double getConfidence() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setSighting(Set<Observation> s) {
		// TODO Auto-generated method stub

	}

}
