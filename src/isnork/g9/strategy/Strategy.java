package isnork.g9.strategy;

import isnork.g9.Diver;
import isnork.g9.PlayerPrototype;
//import isnork.g9.comm.Suggestion;
import isnork.g9.comm.Suggestion;
import isnork.g9.utils.BoardParams;
import isnork.g9.utils.Parameter;
import isnork.g9.utils.risk.IndividualRiskProfile;
import isnork.sim.GameObject.Direction;
import isnork.sim.Observation;

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
	
	//private StrategyPrototype random;
	
	
	public Strategy(PlayerPrototype p) {
		player = p;
		
		riskAvoidance = new RiskAvoidance();
		riskAvoidance.setPlayer(p);
		
		global = new GlobalCircleStrategy();
		global.setPlayer(p);
		
		returning = new Return();
		returning.setPlayer(p);
		
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
					
		ArrayList<Direction> possDirs = new ArrayList<Direction>();
		ArrayList<Double> confs = new ArrayList<Double>();
		
		// Risk avoidance
		possDirs.add(riskAvoidance.getDirection());
		confs.add(riskAvoidance.getConfidence() * Parameter.ESCAPE_CONFIDENCE_COEFFICIENT);
		
		// Global strategy
		global.setLocation(player.getLocation());
		possDirs.add(global.getDirection());
		confs.add(Parameter.GLOBAL_CIRCLE_STRATEGY_CONFIDENCE * 
					Parameter.GLOBAL_STRATEGY_CONFIDENCE_COEFFICIENT);
		
		// Returning
		possDirs.add(returning.getDirection());
		confs.add(returning.getConfidence() * Parameter.RETURNING_CONFIDENCE_COEFFICIENT);
		
		// Communication
		Suggestion sug = player.getComm().getDirection(player.getLocation());
		possDirs.add(sug.getDir());
		confs.add(sug.getConfidence() * Parameter.COMMUNICATION_CONFIDENCE_COEFFICIENT);
		
		// Choose the most confident direction
		Direction bestDir = null;
		double highestConf = 0;
		int chosenStrat = 0;
		for (int i=0; i<4; i++) {
			if (confs.get(i) > highestConf) {
				bestDir = possDirs.get(i);
				highestConf = confs.get(i);
				chosenStrat = i;
			}
		}
		Diver diver = (Diver) player;
		diver.setLastDirection(bestDir);
		return bestDir;
	}
}
