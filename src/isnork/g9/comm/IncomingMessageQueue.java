package isnork.g9.comm;

import java.awt.geom.Point2D;
import java.util.Set;

import isnork.sim.GameObject;
import isnork.sim.Observation;
import isnork.sim.iSnorkMessage;

public class IncomingMessageQueue {
	
	public GameObject.Direction getHVTDirection(){
		return null;
	}
	
	public void load(Point2D myPosition, Set<Observation> whatYouSee, Set<iSnorkMessage> incomingMessages,
			Set<Observation> playerLocations){
		
	}
	
	public IncomingMessageQueue tick(){
		return this;
	}

}
