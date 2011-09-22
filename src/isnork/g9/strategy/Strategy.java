package isnork.g9.strategy;

import isnork.g9.Diver;
import isnork.g9.PlayerPrototype;
import isnork.g9.comm.Suggestion;
import isnork.g9.utils.BoardParams;
import isnork.g9.utils.risk.IndividualRiskProfile;
import isnork.sim.GameObject.Direction;
import isnork.sim.Observation;

import java.util.HashSet;
import java.util.Set;

/**
 * this is the overall strategy
 * @author yufeiliu
 *
 */
public class Strategy {
	
	private IndividualRiskProfile risk;
	private PlayerPrototype player;
	
	private static final int ALL_TIME = 480;
	
	private StrategyPrototype global;
	private StrategyPrototype riskAvoidance;
	private StrategyPrototype returning;
	
	private StrategyPrototype random;
	
	public Strategy(PlayerPrototype p) {
		player = p;
		
		riskAvoidance = new RiskAvoidance();
		riskAvoidance.setPlayer(p);
		
		global = new GlobalCircleStrategy();
		global.setPlayer(p);
		
		returning = new Return();
		returning.setPlayer(p);
		
		random = new RandomWalk();
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
	
	//stub
	public Direction getDirection() {
		
		Direction chosenDirection = null;
		
		Direction escape = riskAvoidance.getDirection();
		
		System.out.println(riskAvoidance.getConfidence());
		
		double baseRiskConfidence = 0.3;
		int startConsideringReturn = 60;
		
		if (player.getTimeElapsed() > ALL_TIME - startConsideringReturn) {
			int tn = player.getTimeElapsed() - (ALL_TIME - startConsideringReturn);
			baseRiskConfidence = Math.pow((tn-30) / 30.0, 2.0);
		}
		
		if (riskAvoidance.getConfidence() > baseRiskConfidence) {
			chosenDirection = escape;
		}
		
		global.setLocation(player.getLocation());
		
		//In the beginning, try to spread out
		if (player.getTimeElapsed() < 40) {
			chosenDirection = global.getDirection();
		//TODO don't hardcode this
		} else if (player.getTimeElapsed() > ALL_TIME - startConsideringReturn) {
			chosenDirection = returning.getDirection();
		} else {
			
			Suggestion suggest = player.getComm().getDirection(player.getLocation());
			
			if (suggest.getConfidence() > 0.2) {
				Direction dir = suggest.getDir();
				chosenDirection = dir;
			} else {
				chosenDirection = global.getDirection();
			}
		}
		Diver diver = (Diver) player;
		diver.setLastDirection(chosenDirection);
		return chosenDirection;
	}
}
