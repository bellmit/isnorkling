package isnork.g9.strategy;

import isnork.g9.Diver;
import isnork.g9.PlayerPrototype;
//import isnork.g9.comm.Suggestion;
import isnork.g9.comm.Suggestion;
import isnork.g9.utils.BoardParams;
import isnork.g9.utils.GameParams;
import isnork.g9.utils.Parameter;
import isnork.g9.utils.risk.IndividualRiskProfile;
import isnork.sim.GameObject.Direction;
import isnork.sim.Observation;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * this is the overall strategy
 * @author yufeiliu
 * @refactorer chrishaueter
 */
public class Strategy {
	
	private IndividualRiskProfile risk;
	private PlayerPrototype player;
		
	private StrategyPrototype global;
	private StrategyPrototype riskAvoidance;
	private StrategyPrototype returning;
	private StrategyPrototype randomWalk;
	
	//private StrategyPrototype random;
	
	
	public Strategy(PlayerPrototype p) {
		player = p;
		
		riskAvoidance = new RiskAvoidance();
		riskAvoidance.setPlayer(p);
		
		global = new GlobalCircleStrategy();
		global.setPlayer(p);
		
		returning = new Return();
		returning.setPlayer(p);
		
		randomWalk = new RandomWalk();
		
	}
	
	public void setBoardParams(BoardParams b) {
		riskAvoidance.setBoardParams(b);
	}
	
	public void setRisk(IndividualRiskProfile r) {
		risk = r;
	}
	
	public IndividualRiskProfile getRisk() {
		return risk;
	}
	
	public void setSighting(Set<Observation> sighting) {
		HashSet<Observation> dangerous = new HashSet<Observation>();
		for (Observation ob : sighting) {
			if (ob.isDangerous()) {
				dangerous.add(ob);
			}
		}
		
		riskAvoidance.setSighting(dangerous);
	}
	
	// Utility Function :)
	public Direction getDirection() {
		
		if (GameParams.isDenselyDangerous()) {
			
			if (player.getId() % 10 != 0) {
				return Direction.STAYPUT;
			}
			
			if (player.getTimeElapsed() > Parameter.GAME_LENGTH - Parameter.RETURN_WINDOW) {
				return returning.getDirection();
			}
			
			Direction tempd;
			Point2D nl ;
			
			do {
				
				double tempr = riskAvoidance.getConfidence();
				
				if (tempr > 0.95) {
					return riskAvoidance.getDirection();
				}
				
				if (Math.random()>=0.9) {
					tempd = randomWalk.getDirection();
				} else {
					tempd = riskAvoidance.getDirection();
				}
				
				nl = new Point2D.Double(player.getLocation().getX() + tempd.dx, player.getLocation().getY() + tempd.dy);
				
			} while (nl.distance(new Point2D.Double(0,0))>1.5);
			
			return tempd;
		}
		
		ArrayList<Direction> possDirs = new ArrayList<Direction>();
		ArrayList<Double> confs = new ArrayList<Double>();
		ArrayList<String> strats = new ArrayList<String>();
		
		// Risk avoidance
		possDirs.add(riskAvoidance.getDirection());
		confs.add(riskAvoidance.getConfidence() * Parameter.ESCAPE_CONFIDENCE_COEFFICIENT);
		strats.add("* Escape");
		
		// Global strategy
		global.setLocation(player.getLocation());
		possDirs.add(global.getDirection());
		confs.add(Parameter.GLOBAL_CIRCLE_STRATEGY_CONFIDENCE * 
					Parameter.GLOBAL_STRATEGY_CONFIDENCE_COEFFICIENT);
		strats.add("* Global");
		
		// Returning
		possDirs.add(returning.getDirection());
		confs.add(returning.getConfidence() * Parameter.RETURNING_CONFIDENCE_COEFFICIENT);
		strats.add("* Return");
		
		// Communication
		Suggestion sug = player.getComm().getDirection(player.getLocation());
		possDirs.add(sug.getDir());
		confs.add(sug.getConfidence() * Parameter.COMMUNICATION_CONFIDENCE_COEFFICIENT);
		strats.add("* Comm");
		
		// Choose the most confident direction
		Direction bestDir = Direction.STAYPUT;
		double highestConf = 0;
		String whichStrat = "";
		for (int i=0; i<4; i++) {
			if (confs.get(i) > highestConf) {
				bestDir = possDirs.get(i);
				highestConf = confs.get(i);
				whichStrat = strats.get(i);
			}
		}
		Diver diver = (Diver) player;
		diver.setLastDirection(bestDir);
		System.out.println(whichStrat);
		return bestDir;
	}
}
