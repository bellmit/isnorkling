package isnork.g9.strategy;

import java.awt.geom.Point2D;

import isnork.g9.PlayerPrototype;
import isnork.g9.utils.risk.IndividualRiskProfile;
import isnork.sim.GameObject.Direction;

/**
 * this is the overall strategy
 * @author yufeiliu
 *
 */
public class Strategy {
	
	private IndividualRiskProfile risk;
	private PlayerPrototype player;
	private Point2D position;
	
	public Strategy(PlayerPrototype p) {
		player = p;
	}
	
	public void setRisk(IndividualRiskProfile r) {
		risk = r;
	}
	
	public IndividualRiskProfile getRisk() {
		return risk;
	}
	
	public void setCurrentPosition(Point2D p) {
		position = p;
	}
	
	//stub
	public Direction getDirection() {
		return Direction.N;
	}
}
