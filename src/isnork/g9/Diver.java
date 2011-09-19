package isnork.g9;

import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import isnork.g9.comm.CommPrototype;
import isnork.g9.comm.SimpleCommunicator;
import isnork.g9.strategy.Strategy;
import isnork.g9.utils.BoardParams;
import isnork.g9.utils.risk.IndividualRiskProfile;
import isnork.g9.utils.risk.RiskDistributor;
import isnork.g9.utils.risk.RiskEvaluator;
import isnork.sim.Observation;
import isnork.sim.Player;
import isnork.sim.SeaLifePrototype;
import isnork.sim.iSnorkMessage;
import isnork.sim.GameObject.Direction;

public class Diver extends Player implements PlayerPrototype {
	private static HashSet<PlayerPrototype> allDivers = new HashSet<PlayerPrototype>();
	
	private Strategy strategy;
	private Set<Observation> sighting;
	private Memory memory;
	private int timeElapsed;
	private Point2D location;
	
	private CommPrototype commPrototype;
	
	public Diver() {
		strategy = new Strategy(this);
		timeElapsed = 0;
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
		
		commPrototype = new SimpleCommunicator();
		commPrototype.init(seaLifePossibilites, penalty, d, r, n);
		
		allDivers.add(this);
		
		if (allDivers.size() == n) {
			RiskDistributor rd = new RiskDistributor();
			RiskEvaluator re = new RiskEvaluator();
			re.setBoardParams(params);
			rd.setBoardParams(params);
			rd.setOverallRiskProfile(re.getOverallRiskProfile());
			
			rd.distribute((Set<PlayerPrototype>) allDivers);
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
		
		return commPrototype.createMessage(myPosition, whatYouSee, incomingMessages, playerLocations);
	}

	@Override
	public Direction getMove() {
		return strategy.getDirection();
	}

}
