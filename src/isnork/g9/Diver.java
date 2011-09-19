package isnork.g9;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Set;

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

	
	private static int idCounter = 0;
	
	private static ArrayList<PlayerPrototype> allDivers = new ArrayList<PlayerPrototype>();
	
	private int id;
	
	private Strategy strategy;
	private Set<Observation> sighting;
	private Memory memory;
	private int timeElapsed;
	private Point2D location;
	
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

	@SuppressWarnings("unchecked")
	@Override
	public void newGame(Set<SeaLifePrototype> seaLifePossibilites, int penalty,
			int d, int r, int n) {
		//TODO use penalty and r for something
		BoardParams params = new BoardParams(seaLifePossibilites, d, n);
		
		allDivers.add(this);
		
		//Id is negative to match the scheme used by GameEngine
		id = -idCounter;
		idCounter++;
		
		
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
	public String tick(Point2D myPosition, Set<Observation> whatYouSee,
			Set<iSnorkMessage> incomingMessages,
			Set<Observation> playerLocations) {
		timeElapsed++;
		sighting = whatYouSee;
		location = myPosition;
		
		//TODO handle comm
		
		
		
		
		
		return null;
	}

	@Override
	public Direction getMove() {
		//TODO integration...
		return strategy.getDirection();
	}

}
