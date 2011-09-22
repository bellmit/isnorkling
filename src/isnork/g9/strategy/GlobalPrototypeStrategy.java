


package isnork.g9.strategy;

import isnork.g9.PlayerPrototype;
import isnork.g9.utils.BoardParams;
import isnork.sim.GameObject.Direction;
import isnork.sim.Observation;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

/**
 * DEPRECATED !!!
 * @author sandeep
 *
 */
public class GlobalPrototypeStrategy implements StrategyPrototype {

	int currentTimeElapsed = 0;
	Point2D location;
	private boolean firstRevolutionCompleted = false;
	private boolean secondRevolutionCompleted = false;
	private double timeToCompleteNthRevolution;
	private final int timeToStartSpiral = 6;
	private int spiralRadius =8;
	/*private int spiralRadius1 = 8;
	private int spiralRadius2 = 16;*/
	private int spiralBoundary;
	private int changeToSpiralRadius = 5;
	private Direction direction;
	int returnFlag = 0;
	private Random random = new Random();
	final static Point2D locationOfBoat = new Point2D.Double(0, 0);
	private int timeToCompleteFirstRevolution = 128; // spiral radius - 8
	// -> 16*(4 sides)*2
	// mins
	private int timeToCompleteSecondRevolution = 336;
	
	private PlayerPrototype player;

	@Override
	public void setBoardParams(BoardParams b) {
		// TODO Auto-generated method stub

	}

	public Direction getDirection() {
		location = player.getLocation();
		direction = getNewDirection();
		int timeElapsed = player.getTimeElapsed();

		if (firstRevolutionCompleted == false) {
			timeToCompleteNthRevolution = timeToCompleteFirstRevolution;
		} else {
			timeToCompleteNthRevolution = timeToCompleteSecondRevolution;
		}

		if (timeElapsed >= timeToStartSpiral) {
			if (timeElapsed > timeToCompleteNthRevolution
					&& location.getX() < spiralBoundary
					&& location.getY() < spiralBoundary) {
				if (firstRevolutionCompleted == false)
					firstRevolutionCompleted = true;
				else {
					secondRevolutionCompleted = true;
				}
				
				spiralRadius = spiralRadius + changeToSpiralRadius;
			}

			if (secondRevolutionCompleted == false)
				direction = getDirectionInSpiralPath();

		}
		return direction;
	}

	public Direction getDirectionInSpiralPath() {

		Direction directionInSpiral = null;

		double spiralRadiusLowBound = -1 * spiralRadius;
		double spiralRadiusHighBound = 1 * spiralRadius;

		if (location.getX() == spiralRadiusLowBound
				&& location.getY() <= spiralRadiusHighBound
				&& location.getY() > spiralRadiusLowBound)
			directionInSpiral = Direction.N;
		else if (location.getX() == spiralRadiusHighBound
				&& location.getY() < spiralRadiusHighBound
				&& location.getY() >= spiralRadiusLowBound)
			directionInSpiral = Direction.S;
		else if (location.getY() == spiralRadiusLowBound
				&& location.getX() < spiralRadiusHighBound
				&& location.getX() >= spiralRadiusLowBound)
			directionInSpiral = Direction.E;
		else if (location.getY() == spiralRadiusHighBound
				&& location.getX() <= spiralRadiusHighBound
				&& location.getX() > spiralRadiusLowBound)
			directionInSpiral = Direction.W;
		else if (location.getY() == 0 && location.getX() > spiralRadiusLowBound
				&& location.getX() < spiralRadiusHighBound)
			if (location.getX() < 0)
				directionInSpiral = Direction.W;
			else
				directionInSpiral = Direction.E;
		else if (location.getX() == 0 && location.getY() > spiralRadiusLowBound
				&& location.getY() < spiralRadiusHighBound) {
			if (location.getY() < 0)
				directionInSpiral = Direction.N;
			else
				directionInSpiral = Direction.S;
		}

		else if (location.getX() > spiralRadiusHighBound)
			directionInSpiral = Direction.W;
		else if (location.getX() < spiralRadiusLowBound)
			directionInSpiral = Direction.E;
		else if (location.getY() > spiralRadiusHighBound)
			directionInSpiral = Direction.N;
		else if (location.getY() < spiralRadiusLowBound)
			directionInSpiral = Direction.S;

		else if (location.getX() > 0) {
			directionInSpiral = Direction.E;
		} else if (location.getX() < 0) {
			directionInSpiral = Direction.W;
		}

		else {
			directionInSpiral = null;

		}
		return directionInSpiral;
	}

	@Override
	public void setLocation(Point2D loc) {
		this.location = loc;
	}

	@Override
	public void setPlayer(PlayerPrototype p) {
		player = p;
	}

	private Direction getNewDirection() {
		int r = random.nextInt(100);
		if (r < 10 || direction == null) {
			ArrayList<Direction> directions = Direction.allBut(direction);
			direction = directions.get(random.nextInt(directions.size()));
		}
		return direction;
	}

	@Override
	public void setOverallStrategy(Strategy s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getConfidence() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setSighting(Set<Observation> s) {
		// TODO Auto-generated method stub
		
	}
}