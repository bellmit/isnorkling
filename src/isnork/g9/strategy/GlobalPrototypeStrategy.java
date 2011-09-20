package isnork.g9.strategy;

import isnork.g9.PlayerPrototype;
import isnork.g9.utils.BoardParams;
import isnork.sim.GameEngine;
import isnork.sim.GameObject.Direction;
import isnork.sim.Player;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class GlobalPrototypeStrategy implements GlobalPrototype {

	int currentTimeElapsed = 0;
	private boolean firstRevolutionCompleted = false;
	private boolean secondRevolutionCompleted = false;
	private double timeToCompleteNthRevolution;
	private boolean nthRevolutionCompleted = false;
	private final int timeToStartSpiral = 6;
	private double distanceFromBoat;
	private int spiralRadius = 8;
	private int spiralBoundary;
	private int changeToSpiralRadius = 5;
	private Direction direction;
	
	private int curDelay = 0;
	
	private PlayerPrototype player;
	
	int returnFlag = 0;
	Point2D currentPosition = null;
	private Random random;
	final static Point2D locationOfBoat = new Point2D.Double(0, 0);
	private int timeToCompleteFirstRevolution = 128; // spiral radius - 8
	// -> 16*(4 sides)*2
	// mins
	private int timeToCompleteSecondRevolution = 336;

	@Override
	public void setBoardParams(BoardParams b) {
		// TODO Auto-generated method stub

	}

	public void setAllDivers(Set<PlayerPrototype> allDivers) {
		// TODO Auto-generated method stub

	}

	@Override
	public Direction getDirection() {
		direction = getDirectionInSpiralPath();
		return direction;
	}
	
	public void setLocation(Point2D loc) {
		currentPosition = loc;
	}
	
	public void setPlayer(PlayerPrototype p) {
		player = p;
		curDelay = -(player.getId());
	}

	
	public Direction getDirectionInSpiralPath() {
		Direction directionInSpiral = null;

		if (curDelay>0) { 
			curDelay--;
			return Direction.STAYPUT;
		}
		
		
		double spiralRadiusLowBound = -1 * spiralRadius;
		double spiralRadiusHighBound = spiralRadius;

		if (currentPosition.getX() == spiralRadiusLowBound
				&& currentPosition.getY() <= spiralRadiusHighBound
				&& currentPosition.getY() > spiralRadiusLowBound)
			directionInSpiral = Direction.N;
		else if (currentPosition.getX() == spiralRadiusHighBound
				&& currentPosition.getY() < spiralRadiusHighBound
				&& currentPosition.getY() >= spiralRadiusLowBound)
			directionInSpiral = Direction.S;
		else if (currentPosition.getY() == spiralRadiusLowBound
				&& currentPosition.getX() < spiralRadiusHighBound
				&& currentPosition.getX() >= spiralRadiusLowBound)
			directionInSpiral = Direction.E;
		else if (currentPosition.getY() == spiralRadiusHighBound
				&& currentPosition.getX() <= spiralRadiusHighBound
				&& currentPosition.getX() > spiralRadiusLowBound)
			directionInSpiral = Direction.W;
		else if (currentPosition.getY() == 0
				&& currentPosition.getX() > spiralRadiusLowBound
				&& currentPosition.getX() < spiralRadiusHighBound)
			directionInSpiral = Direction.E;
		else if (currentPosition.getX() == 0
				&& currentPosition.getY() > spiralRadiusLowBound
				&& currentPosition.getY() < spiralRadiusHighBound)
			directionInSpiral = Direction.N;

		else if (currentPosition.getX() > spiralRadiusHighBound)
			directionInSpiral = Direction.W;
		else if (currentPosition.getX() < spiralRadiusLowBound)
			directionInSpiral = Direction.E;
		else if (currentPosition.getY() > spiralRadiusHighBound)
			directionInSpiral = Direction.N;
		else if (currentPosition.getY() < spiralRadiusLowBound)
			directionInSpiral = Direction.S;

		else if (currentPosition.getX() > 0) {// GameEngine.println ("snork " +
												// this.getId() +
												// " moving east from " +
												// currentPosition);
			directionInSpiral = Direction.E;
		} else if (currentPosition.getX() < 0) { // GameEngine.println ("snork "
													// + this.getId() +
													// " moving west from " +
													// currentPosition);
			directionInSpiral = Direction.W;
		}

		else {
			directionInSpiral = Direction.STAYPUT;

		}

		return directionInSpiral;
	}

	
}
