package isnork.g9.strategy;

import isnork.g9.PlayerPrototype;
import isnork.sim.GameObject.Direction;
import isnork.sim.Observation;

import java.util.Set;

public interface RiskAvoidancePrototype {
	public void setPlayer(PlayerPrototype p);
	public void setOverallStrategy(Strategy s);
	public void setSighting(Set<Observation> s);
	public Direction getDirection();
	public double getConfidence();
}
