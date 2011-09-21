package isnork.g9.strategy;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Set;

import isnork.g9.PlayerPrototype;
import isnork.g9.utils.BoardParams;
import isnork.sim.GameObject.Direction;
import isnork.sim.Observation;

public class Return implements StrategyPrototype {
	private PlayerPrototype player;
	
	public void setPlayer(PlayerPrototype p) {
		player = p;
	}
	
	public Direction getDirection() {
		List<Direction> dirs = Direction.allBut(null);
		
		Point2D home = new Point2D.Double(0,0);
		Point2D cur = player.getLocation();
		
		if (cur.getX()==0 && cur.getY()==0) {
			return Direction.STAYPUT;
		}
		
		for (Direction d : dirs) {
			Point2D newloc = new Point2D.Double(cur.getX() + d.dx, cur.getY() + d.dy);
			
			if (home.distance(newloc) < home.distance(cur)) 
				return d;
		}
		
		return Direction.STAYPUT;
	}

	@Override
	public void setBoardParams(BoardParams b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLocation(Point2D loc) {
		// TODO Auto-generated method stub
		
	}

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
