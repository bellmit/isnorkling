package isnork.g9.strategy;

import isnork.g9.PlayerPrototype;
import isnork.g9.utils.BoardParams;
import isnork.sim.GameObject.Direction;

import java.util.Set;

public class Global implements GlobalPrototype {

	private BoardParams params;
	
	@Override
	public void setBoardParams(BoardParams b) {
		// TODO Auto-generated method stub
		params = b;
	}

	@Override
	public Direction getDirection() {
		return Direction.allBut(Direction.STAYPUT).get( (int)(Math.random() * 8) );
	}
}
