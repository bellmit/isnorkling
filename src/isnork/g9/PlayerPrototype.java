package isnork.g9;

import java.awt.geom.Point2D;
import java.util.Set;

import isnork.g9.strategy.Strategy;
import isnork.g9.utils.risk.IndividualRiskProfile;
import isnork.sim.Observation;

public interface PlayerPrototype {
	public void setIndividualRiskProfile(IndividualRiskProfile r);
	public void setStrategy(Strategy s);
	public int getTimeElapsed();
	public Set<Observation> getCurrentSighting();
	public Memory getMemory();
	public Point2D getLocation();
	public int getId();
}
