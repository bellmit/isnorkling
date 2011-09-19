package isnork.g9.strategy;

import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;

import isnork.g9.PlayerPrototype;
import isnork.g9.utils.risk.IndividualRiskProfile;
import isnork.sim.GameObject.Direction;
import isnork.sim.Observation;

/**
 * this is the overall strategy
 * @author yufeiliu
 *
 */
public class Strategy {
	
	private IndividualRiskProfile risk;
	private PlayerPrototype player;
	
	private LocalBenignSightingPrototype local;
	private GlobalPrototype global;
	private RiskAvoidancePrototype riskAvoidance;
	private Return returning;
	
	private Set<Observation> sighting;
	
	public Strategy(PlayerPrototype p) {
		player = p;
		
		riskAvoidance = new RiskAvoidance();
		riskAvoidance.setPlayer(p);
	}
	
	public void setRisk(IndividualRiskProfile r) {
		risk = r;
	}
	
	public IndividualRiskProfile getRisk() {
		return risk;
	}
	
	public void setSighting(Set<Observation> sighting) {
		this.sighting = sighting;
		
		HashSet<Observation> dangerous = new HashSet<Observation>();
		HashSet<Observation> benign = new HashSet<Observation>();
		
		for (Observation ob : sighting) {
			if (ob.isDangerous()) {
				dangerous.add(ob);
			} else {
				benign.add(ob);
			}
		}
		
		local.setObservations(benign);
		riskAvoidance.setDangerousSighting(dangerous);
	}
	
	//stub
	public Direction getDirection() {
		
		if (riskAvoidance.getConfidence() > 0.5) {
			return riskAvoidance.getDirection();
		}
		
		//In the beginning, try to spread out
		if (player.getTimeElapsed() < 60) {
			return global.getDirection();
		//TODO don't hardcode this
		} else if (player.getTimeElapsed() > 400) {
			return returning.getDirection();
		} else {
			//get local strategy or message queue strategy, based on what's more important
			return local.getDirection();
		}
		
	}
}
