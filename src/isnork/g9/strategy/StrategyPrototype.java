package isnork.g9.strategy;

import isnork.g9.PlayerPrototype;
import isnork.g9.utils.BoardParams;
import isnork.sim.Observation;
import isnork.sim.GameObject.Direction;

import java.awt.geom.Point2D;
import java.util.Set;

public interface StrategyPrototype {
	public void setBoardParams(BoardParams b);
	public Direction getDirection();
	public void setLocation(Point2D loc);
	public void setPlayer(PlayerPrototype p);
	public void setOverallStrategy(Strategy s);
	public double getConfidence();
	public void setSighting(Set<Observation> s);
}
