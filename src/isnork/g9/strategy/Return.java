package isnork.g9.strategy;

import java.awt.geom.Point2D;
import java.util.List;

import isnork.g9.PlayerPrototype;
import isnork.sim.GameObject.Direction;

public class Return {
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
}
