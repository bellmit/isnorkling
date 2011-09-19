/**
 * Really Dumb Player to demonstrate interacting with simulator API
 * @author Group 10: Yufei, Chris, Neil, Sandeep
 */

package isnork.g9;

import isnork.sim.GameEngine;
import isnork.sim.GameObject.Direction;
import isnork.sim.Observation;
import isnork.sim.Player;
import isnork.sim.SeaLifePrototype;
import isnork.sim.iSnorkMessage;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Set;

public class ReallyDumbPlayer extends Player {

	private Direction[] choices = new Direction[] { Direction.N, Direction.NW,
			Direction.W, Direction.SW, Direction.S, Direction.SE, Direction.E,
			Direction.NE, Direction.STAYPUT };
	Point2D currentPosition = null;
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
	int returnFlag = 0;
	final static Point2D locationOfBoat = new Point2D.Double(0, 0);

	private int timeToCompleteFirstRevolution = 128; // spiral radius - 8
	// -> 16*(4 sides)*2
	// mins
	private int timeToCompleteSecondRevolution = 336;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Real Dumb Player by Group 10";
	}

	@Override
	public void newGame(Set<SeaLifePrototype> seaLifePossibilites, int penalty,
			int d, int r, int n) {

	}

	@Override
	public String tick(Point2D myPosition, Set<Observation> whatYouSee,
			Set<iSnorkMessage> incomingMessages,
			Set<Observation> playerLocations) {
		// TODO Auto-generated method stub
		currentTimeElapsed++;
		currentPosition = myPosition;
		return null;
	}

	/**
	 * pick one out of the nine choices with equal probability
	 */
	@Override
	public Direction getMove() {
		// Equal probability for each of the 8 compass direction, as well as
		// staying stationary

		direction = getNewDirection();
		
		if (firstRevolutionCompleted == false) {
			timeToCompleteNthRevolution = timeToCompleteFirstRevolution;
		} else {
			timeToCompleteNthRevolution = timeToCompleteSecondRevolution;

		}
		if (currentTimeElapsed >= timeToStartSpiral) {
			if (currentTimeElapsed > timeToCompleteNthRevolution
					&& currentPosition.getX() < spiralBoundary
					&& currentPosition.getY() < spiralBoundary) {
				if (firstRevolutionCompleted == false)
					firstRevolutionCompleted = true;
				else {
					GameEngine.println("second revolution completed");
					secondRevolutionCompleted = true;
				}

				spiralRadius = spiralRadius + changeToSpiralRadius;

			}

			if (secondRevolutionCompleted == false)
				direction = getDirectionInSpiralPath();
			else {
				returnFlag = 1;

			}
		}
		
		if (returnFlag == 1) {
			direction = towardsBoat();
			
		}
		return direction;
	}

	// the case for random exploration
	public boolean isDangerous() {
		return false;
	}

	public Direction getDirectionInSpiralPath() {

		Direction directionInSpiral = null;

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
			directionInSpiral = null;

		}

		return directionInSpiral;

	}
	
	public Direction towardsBoat() {

		Direction direction = null;

		if (currentPosition.getY() == locationOfBoat.getY()) {
			if (currentPosition.getX() < locationOfBoat.getX())
				direction = Direction.E;
			else if (currentPosition.getX() > locationOfBoat.getX())
				direction = Direction.W;

		}

		if (currentPosition.getX() == locationOfBoat.getX()) {
			if (currentPosition.getY() < locationOfBoat.getY())
				direction = Direction.S;
			else if (currentPosition.getY() > locationOfBoat.getY())
				direction = Direction.N;
		}

		if (currentPosition.getY() < locationOfBoat.getY()
				&& currentPosition.getX() < locationOfBoat.getX())
			direction = Direction.SE;
		else if (currentPosition.getY() > locationOfBoat.getY()
				&& currentPosition.getX() < locationOfBoat.getX())
			direction = Direction.NE;
		else if (currentPosition.getY() < locationOfBoat.getY()
				&& currentPosition.getX() > locationOfBoat.getX())
			direction = Direction.SW;
		else if (currentPosition.getY() > locationOfBoat.getY()
				&& currentPosition.getX() > locationOfBoat.getX())
			direction = Direction.NW;

		

		return direction;

	}
	
	private Direction getNewDirection() {
		int r = random.nextInt(100);
		if (r < 10 || direction == null) {
			ArrayList<Direction> directions = Direction.allBut(direction);
			direction = directions.get(random.nextInt(directions.size()));
		}
		return direction;
	}
}