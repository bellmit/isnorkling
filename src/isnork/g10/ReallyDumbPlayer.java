/**
 * Really Dumb Player to demonstrate interacting with simulator API
 * @author Group 10: Yufei, Chris, Neil, Sandeep
 */

package isnork.g10;

import java.awt.geom.Point2D;
import java.util.Random;
import java.util.Set;

import isnork.sim.Observation;
import isnork.sim.Player;
import isnork.sim.SeaLifePrototype;
import isnork.sim.iSnorkMessage;
import isnork.sim.GameObject.Direction;

public class ReallyDumbPlayer extends Player {

	private Direction[] choices = new Direction[] { Direction.N, Direction.NW, 
			Direction.W, Direction.SW, Direction.S, 
            Direction.SE, Direction.E, Direction.NE, 
            Direction.STAYPUT }; 
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Real Dumb Player by Group 10";
	}

	@Override
	public void newGame(Set<SeaLifePrototype> seaLifePossibilites, int penalty,
			int d, int r, int n) {
		// TODO Auto-generated method stub

	}

	@Override
	public String tick(Point2D myPosition, Set<Observation> whatYouSee,
			Set<iSnorkMessage> incomingMessages,
			Set<Observation> playerLocations) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * pick one out of the nine choices with equal probability
	 */
	@Override
	public Direction getMove() {
		//Equal probability for each of the 8 compass direction, as well as staying stationary
		return choices[this.random.nextInt(9)];
	}
}
