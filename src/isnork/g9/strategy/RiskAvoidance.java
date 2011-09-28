package isnork.g9.strategy;

import isnork.g9.PlayerPrototype;
import isnork.g9.utils.BoardParams;
import isnork.g9.utils.Parameter;
import isnork.g9.utils.risk.IndividualRiskProfile;
import isnork.sim.GameObject.Direction;
import isnork.sim.Observation;
import isnork.sim.SeaLifePrototype;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.jws.soap.SOAPBinding.ParameterStyle;

public class RiskAvoidance implements StrategyPrototype {

	private PlayerPrototype player;
	private Set<Observation> sighting;
	private double confidence;
	private Strategy strategy;
	private IndividualRiskProfile risk;
	private BoardParams board;
	
	private static double avgRisk = -1;
	
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

	public void setSighting(Set<Observation> s) {
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
			o.happiness = getHappiness(ob.getName());
			o.direction = ob.getDirection();
			o.id = ob.getId();
			o.location = ob.getLocation();
			
			if (o.location == null || o.direction == null) { continue; }
			
			o.location.setLocation(o.location.getX() + o.direction.dx, o.location.getY() + o.direction.dy);
			
			nextSighting.add(o);
		}
		
		
		double minDanger = Double.MAX_VALUE;
		Direction curDir = Direction.STAYPUT;
		
		for (Direction d : dirs) {
			Point2D newLoc = new Point2D.Double(loc.getX() + d.dx, loc.getY() + d.dy);
			double danger = weightedDanger(nextSighting, newLoc);
			
			if (danger < minDanger) {
				System.out.println("  risk: " + danger);
				minDanger = danger;
				curDir = d;
			}
		}
		
		if (minDanger == Double.MAX_VALUE) { minDanger = 0; }
		
		//TODO this is really primitive, not taking into consideration of board params, etc.
		confidence = Math.min(minDanger / avgRisk, 1.0);
		System.out.println("avgRisk: " + avgRisk);
		System.out.println("confidence prepared: " + confidence);
		//confidence = 0.5 * Math.min(sighting.size() / 10, 1) + 0.5 * Math.min(minDanger / 200, 1);
		
		Point2D boatLoc = new Point(0,0);
		if(loc.distance(boatLoc) < Parameter.RET_TO_BOAT_THRESHOLD &&
				minDanger > Parameter.CONSERVATIVE_RISK_COEFF){
			//return to boat
			double thetaRad = Math.atan2(loc.getY()-boatLoc.getY(), boatLoc.getX()-loc.getX());
			double thetaDeg = thetaRad * 180 / Math.PI;
			if(thetaDeg < 0 ) thetaDeg += 360;
			int dirChoice = ((int)thetaDeg)/45 + ( ((int) thetaDeg)%45 < 23 ? 0 : 1);
			curDir = Parameter.ALL_DIRS[dirChoice];
			
		}
		
		//Adjust by risk profile
		//confidence *= Math.sqrt(risk.getRiskAvoidance());
		
		return curDir;
	}
	
	private int getHappiness(String name) {
		ArrayList<SeaLifePrototype> sealife = board.getArrayListOfSeaLifePrototypes();
		for (SeaLifePrototype sl : sealife) {
			if (sl.getName().equals(name)) {
				return sl.getHappiness();
			}
		}
		return 0;
	}
	
	//Heuristics of estimating how dangerous a sighting configuration is 
	private double weightedDanger(Set<ObservationWrapper> sighting, Point2D loc) {
		double danger = 0;
		for (ObservationWrapper ob : sighting) {
			
			double distance = loc.distance(ob.location);
			
			if (distance <= 1.5) {
				danger+=Math.abs(ob.happiness*2);
			} else {
				double badFactor = 0.79;
				double goodFactor = 0.03;
				
				double temp = ob.happiness*2 * (1 - (distance - 1.5) * Parameter.DANGER_ESTIMATION_DROPPING_FACTOR);
				
				double dy = ob.direction.dy;
				double dx = ob.direction.dx;
				
				Point2D nextLoc = new Point2D.Double(ob.location.getX()+dx, ob.location.getY()+dy);
				
				if (loc.distance(nextLoc) < loc.distance(ob.location)) {
					temp*=badFactor;
				} else {
					temp*=goodFactor;
				}
				danger+=Math.abs(temp);
			}
			
		}
		
		return danger;
	}
	
	@Override
	public double getConfidence() {
		return confidence;
	}

	@Override
	public void setBoardParams(BoardParams b) {
		board = b;
		
		if (avgRisk == -1) {
			
			int totalHappiness = 0;
			
			for (SeaLifePrototype ssl : b.getArrayListOfSeaLifePrototypes()) {
				
				if (!ssl.isDangerous()) { continue; }
				totalHappiness+=ssl.getHappiness()*ssl.getMaxCount();
			}
			
			avgRisk = totalHappiness * 2.0 / (b.getDimension() * b.getDimension() * 4);
		}
	}

	@Override
	public void setLocation(Point2D loc) {
		// TODO Auto-generated method stub
		
	}
}
