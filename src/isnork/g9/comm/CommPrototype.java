package isnork.g9.comm;

import isnork.sim.Observation;
import isnork.sim.SeaLifePrototype;
import isnork.sim.iSnorkMessage;

import java.awt.geom.Point2D;
import java.util.Set;

public interface CommPrototype {
	
	public void init(Set<SeaLifePrototype> seaLifePossibilites, int penalty,
			int d, int r, int n);
	
	public String createMessage(Point2D myPosition, Set<Observation> whatYouSee,
			Set<iSnorkMessage> incomingMessages,
			Set<Observation> playerLocations);
	
	public Suggestion getDirection(Point2D myPosition);
}
