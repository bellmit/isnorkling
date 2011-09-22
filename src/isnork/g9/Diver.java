package isnork.g9;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import isnork.g9.comm.CommPrototype;
import isnork.g9.comm.SimpleCommunicator;
import isnork.g9.strategy.Strategy;
import isnork.g9.utils.BoardParams;
import isnork.g9.utils.GameParams;
import isnork.g9.utils.risk.IndividualRiskProfile;
import isnork.g9.utils.risk.RiskDistributor;
import isnork.g9.utils.risk.RiskEvaluator;
import isnork.sim.Observation;
import isnork.sim.Player;
import isnork.sim.SeaLifePrototype;
import isnork.sim.iSnorkMessage;
import isnork.sim.GameObject.Direction;

public class Diver extends Player implements PlayerPrototype {
	private static HashSet<Diver> allDivers = new HashSet<Diver>();
	
	private Strategy strategy;
	private Set<Observation> sighting;
	private Memory memory;
	private int timeElapsed;
	private Point2D location;
	private int desiredRadius;
	private Direction lastDirection;
	
	private CommPrototype commPrototype = null;
	
	public Diver() {
		strategy = new Strategy(this);
		timeElapsed = 0;
		
		ArrayList<Direction> dirs = Direction.allBut(Direction.STAYPUT);
		dirs.add(Direction.E);
		
		lastDirection = dirs.get((int)(Math.random()*8));
	}
	
	@Override
	public void setIndividualRiskProfile(IndividualRiskProfile r) {
		strategy.setRisk(r);
	}

	@Override
	public void setStrategy(Strategy s) {
		strategy = s;
	}

	@Override
	public Set<Observation> getCurrentSighting() {
		return sighting;
	}

	@Override
	public Memory getMemory() {
		return memory;
	}

	@Override
	public Point2D getLocation() {
		return location;
	}

	@Override
	public String getName() {
		return "Diver by G9";
	}
	
	@Override
	public int getTimeElapsed() {
		return timeElapsed;
	}
	
	public Random getRandom() {
		return this.random;
	}
	
	@Override
	public void newGame(Set<SeaLifePrototype> seaLifePossibilites, int penalty,
			int d, int r, int n) {
		//TODO use penalty and r for something
		BoardParams params = new BoardParams(seaLifePossibilites, d, n);
		
		strategy.setBoardParams(params);
		
		GameParams.init(seaLifePossibilites, penalty, d, r, n);
		commPrototype = new SimpleCommunicator();
		commPrototype.init();
		
		allDivers.add(this);
		
		if (allDivers.size() == n) {
			RiskDistributor rd = new RiskDistributor();
			RiskEvaluator re = new RiskEvaluator();
			re.setBoardParams(params);
			rd.setBoardParams(params);
			rd.setOverallRiskProfile(re.getOverallRiskProfile());
			
			rd.distribute((Set<Diver>) allDivers);
			
			// Calculating all of the radii to be assigned to various divers
			
			int boardRadius=GameParams.getGridSize();
			int numberOfDivers=GameParams.getNumDivers();
			int visibilityRadius=GameParams.getVisibilityRadius();
			int numberOfCircles = (int) Math.ceil((boardRadius- 2*visibilityRadius)/visibilityRadius);
			int tempVariable=0;
			for( int i=0; i< numberOfCircles; i++){
				tempVariable =tempVariable + i;
			}
			int totalDistance = (int) (numberOfCircles + 4*Math.PI*visibilityRadius*tempVariable);
			int averageDistance = (int) totalDistance /numberOfDivers;

			int circleRadius, numDivers;
			ArrayList<Integer> radii = new ArrayList<Integer>();
			ArrayList<Integer> numDiversPerCircle = new ArrayList<Integer>();
			for (int i=0; i < numberOfCircles; i++) {
				circleRadius = visibilityRadius * (1 + 2 * numberOfCircles);
				radii.add(circleRadius);
				numDivers = (int) Math.floor( 2 * Math.PI * circleRadius / averageDistance );
				numDiversPerCircle.add(numDivers);			
				}
			
			int diversRemaining = 0;
			int i = 0;
			// Distribute desired radii across all divers 
			for ( Diver diver : allDivers ) {
				diversRemaining = numDiversPerCircle.get(i);
				if (diversRemaining > 0) {
					diversRemaining--;
					numDiversPerCircle.set(i, diversRemaining);
					diver.setDesiredRadius(radii.get(i));
				} else if (diversRemaining == 0) {
					i++;
				}
				
			}
			
		}
	}

	@Override
	public CommPrototype getComm() {
		return commPrototype;
	}
	
	@Override
	public String tick(Point2D myPosition, Set<Observation> whatYouSee,
			Set<iSnorkMessage> incomingMessages,
			Set<Observation> playerLocations) {
		timeElapsed++;
		sighting = whatYouSee;
		location = myPosition;
		
		strategy.setSighting(sighting);
		//return null;
		return commPrototype.createMessage(myPosition, whatYouSee, incomingMessages, playerLocations);
	}

	@Override
	public Direction getMove() {
		return strategy.getDirection();
	}

	public int getDesiredRadius() {
		return desiredRadius;
	}

	public void setDesiredRadius(int desiredRadius) {
		this.desiredRadius = desiredRadius;
	}

	public Direction getLastDirection() {
		return lastDirection;
	}

	public void setLastDirection(Direction lastDirection) {
		this.lastDirection = lastDirection;
	}
}
