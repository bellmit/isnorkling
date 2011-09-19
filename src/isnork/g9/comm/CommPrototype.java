package isnork.g9.comm;

import java.awt.geom.Point2D;
import java.util.PriorityQueue;
import java.util.Set;

import isnork.g9.PlayerPrototype;
import isnork.sim.GameObject;
import isnork.sim.GameObject.Direction;
import isnork.sim.Observation;
import isnork.sim.iSnorkMessage;

public interface CommPrototype {
	
	public String createMessage(Point2D myPosition, Set<Observation> whatYouSee,
			Set<iSnorkMessage> incomingMessages,
			Set<Observation> playerLocations);
	
	public GameObject.Direction getDirection();
}
