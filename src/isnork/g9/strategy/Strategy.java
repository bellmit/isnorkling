package isnork.g9.strategy;

import isnork.g9.PlayerPrototype;
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
	
	private StrategyPrototype global;
	private StrategyPrototype riskAvoidance;
	private StrategyPrototype returning;
	
	private StrategyPrototype random;
	
	public Strategy(PlayerPrototype p) {
		player = p;
		
		riskAvoidance = new RiskAvoidance();
		riskAvoidance.setPlayer(p);
		
		global = new GlobalPrototypeStrategy();
		global.setPlayer(p);
		
		returning = new Return();
		returning.setPlayer(p);
		
		random = new RandomWalk();
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
		
		if (riskAvoidance.getConfidence() > 0.5) {
			return riskAvoidance.getDirection();
		}
		
		global.setLocation(player.getLocation());
		
		//In the beginning, try to spread out
		if (player.getTimeElapsed() < 150) {
			return global.getDirection();
		//TODO don't hardcode this
		} else if (player.getTimeElapsed() > 350) {
			return returning.getDirection();
		} else {
			Direction dir = player.getComm().getDirection(player.getLocation()).getDir();
			return dir;
		}
		
	}
}
