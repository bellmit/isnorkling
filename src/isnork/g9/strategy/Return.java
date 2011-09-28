package isnork.g9.strategy;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Set;

import isnork.g9.Diver;
import isnork.g9.PlayerPrototype;
import isnork.g9.utils.BoardParams;
import isnork.g9.utils.GameParams;
import isnork.g9.utils.Parameter;
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
		
		Direction bestDir = null;
		double maxGain = 0;
		
		for (Direction d : dirs) {
			Point2D newloc = new Point2D.Double(cur.getX() + d.dx, cur.getY() + d.dy);
			
			if (home.distance(newloc) < home.distance(cur) && (home.distance(cur) - home.distance(newloc) > maxGain )) {
				maxGain = home.distance(cur) - home.distance(newloc);
				bestDir = d;
			}	
		}
		
		return bestDir;
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
		double conf = 0;
		if(player instanceof Diver){
			System.out.println("HAPPINESS SCORED: "+((Diver)player).getHappinessScored());
		}
		if(player instanceof Diver &&
				((Diver)player).getHappinessScored() > 
		(Parameter.HAPPINESS_THRESHOLD * GameParams.getMaxHappinessPossible())){
			System.out.println("********TIME TO GO HOME***********");
			return 1;
		}
		
		int returnTime = Parameter.GAME_LENGTH - Parameter.RETURN_WINDOW;
		if (player.getTimeElapsed() > returnTime) {
			int timePast = player.getTimeElapsed() - returnTime;
			conf = Math.pow( 1 - (Parameter.RETURN_WINDOW - timePast) / Parameter.RETURN_WINDOW, 2.0 );
		} else {
			conf = 0;
		}
		return conf;
	}

	@Override
	public void setSighting(Set<Observation> s) {
		// TODO Auto-generated method stub
		
	}
}
