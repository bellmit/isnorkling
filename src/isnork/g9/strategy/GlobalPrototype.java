package isnork.g9.strategy;

import java.awt.geom.Point2D;
import java.util.Set;

import isnork.g9.PlayerPrototype;
import isnork.g9.utils.BoardParams;
import isnork.sim.GameObject.Direction;

public interface GlobalPrototype {
	public void setBoardParams(BoardParams b);
	public Direction getDirection();
	public void setLocation(Point2D loc);
	public void setPlayer(PlayerPrototype p);
}
