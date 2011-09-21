package isnork.g9.strategy;

import isnork.g9.PlayerPrototype;
import isnork.g9.utils.BoardParams;
import isnork.sim.GameObject.Direction;
import isnork.sim.Observation;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Set;

public class GlobalRadial implements StrategyPrototype {

	private ArrayList<Direction> dirs = Direction.allBut(Direction.STAYPUT);
	private BoardParams board;
	
	private PlayerPrototype player;
	private Direction dir = null;
	
	@Override
	public void setBoardParams(BoardParams b) {
		board = b;
	}

	@Override
	public Direction getDirection() {
		if (dir == null) {
			dir = dirs.get((int)(Math.random()*7));
		}
		return dir;
	}

	@Override
	public void setLocation(Point2D loc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPlayer(PlayerPrototype p) {
		player = p;
	}

	public void setOverallStrategy(Strategy s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getConfidence() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public void setSighting(Set<Observation> s) {
		// TODO Auto-generated method stub
		
	}

}
