package isnork.g9.strategy;

import isnork.g9.PlayerPrototype;
import isnork.sim.GameObject.Direction;
import isnork.sim.Observation;

import java.util.Set;

public interface LocalBenignSightingPrototype {
	//Ignore all dangerous observations - that'll be handled by a different sub-strategy
	public void setObservations(Set<Observation> observations);
	public Direction getDirection();
	//How confident is the direction? range of 0 to 1
	public double getConfidence();
	public void setPlayer(PlayerPrototype p);
}
