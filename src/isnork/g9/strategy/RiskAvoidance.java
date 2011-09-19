package isnork.g9.strategy;

import isnork.g9.PlayerPrototype;
import isnork.g9.utils.risk.IndividualRiskProfile;
import isnork.sim.Observation;
import isnork.sim.GameObject.Direction;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class RiskAvoidance implements RiskAvoidancePrototype {

	private PlayerPrototype player;
	private Set<Observation> sighting;
	private double confidence;
	private Strategy strategy;
	private IndividualRiskProfile risk;
	
	private ArrayList<Direction> dirs = Direction.allBut(null);
	
	@Override
	public void setPlayer(PlayerPrototype p) {
		player = p;
	}
	
	@Override
	public void setOverallStrategy(Strategy s) {
		strategy = s;
		risk = strategy.getRisk();
	}

	@Override
	public void setDangerousSighting(Set<Observation> s) {
		sighting = s;
	}

	@Override
	//TODO too simple to assume dangerous creatures stay in same direction
	public Direction getDirection() {
		confidence = 0;
		
		Point2D loc = player.getLocation();
		
		HashSet<ObservationWrapper> nextSighting = new HashSet<ObservationWrapper>();
		
		for (Observation ob : sighting) {
			ObservationWrapper o = new ObservationWrapper();
			o.happiness = ob.happiness();
			o.direction = ob.getDirection();
			o.id = ob.getId();
			o.location = ob.getLocation();
			
			
			o.location.setLocation(o.location.getX() + o.direction.dx, o.location.getY() + o.direction.dy);
			
			nextSighting.add(o);
		}
		
		double minDanger = Double.MAX_VALUE;
		Direction curDir = Direction.STAYPUT;
		
		for (Direction d : dirs) {
			Point2D newLoc = new Point2D.Double(loc.getX() + d.dx, loc.getY() + d.dy);
			double danger = weightedDanger(nextSighting, newLoc);
			
			if (danger < minDanger) {
				minDanger = danger;
				curDir = d;
			}
		}
		
		//TODO this is really primitive, not taking into consideration of board params, etc.
		confidence = 0.5 * Math.min(sighting.size() / 10, 1) + 0.5 * Math.min(minDanger / 200, 1);
		
		//Adjust by risk profile
		confidence *= Math.sqrt(risk.getRiskAvoidance());
		
		return curDir;
	}
	
	//Heuristics of estimating how dangerous a sighting configuration is 
	private double weightedDanger(Set<ObservationWrapper> sighting, Point2D loc) {
		double danger = 0;
		for (ObservationWrapper ob : sighting) {
			if (loc.distance(ob.location) <= 1.5) {
				danger+=ob.happiness*2;
			} else {
				double badFactor = 0.79;
				double goodFactor = 0.03;
				
				double temp = ob.happiness*2 / Math.pow(loc.distance(ob.location), 2);
				
				double dy = ob.direction.dy;
				double dx = ob.direction.dx;
				
				Point2D nextLoc = new Point2D.Double(ob.location.getX()+dx, ob.location.getY()+dy);
				
				if (loc.distance(nextLoc) < loc.distance(ob.location)) {
					temp*=badFactor;
				} else {
					temp*=goodFactor;
				}
				
				danger+=temp;
			}
		}
		
		return danger;
	}

	@Override
	public double getConfidence() {
		return confidence;
	}
}
