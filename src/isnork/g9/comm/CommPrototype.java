package isnork.g9.comm;

import java.util.Set;

import isnork.g9.PlayerPrototype;
import isnork.sim.GameObject.Direction;
import isnork.sim.iSnorkMessage;

public interface CommPrototype {
	public void setDiver(PlayerPrototype p);
	public void broadcast(iSnorkMessage m);
	public Set<iSnorkMessage> receive();
	
	//Make a recommendation of where to go
	public Direction getDirection();
	//Between 0 and 1
	public double getConfidence();
}
