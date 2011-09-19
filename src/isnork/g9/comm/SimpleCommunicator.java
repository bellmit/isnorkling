package isnork.g9.comm;

import isnork.sim.GameObject.Direction;
import isnork.sim.Observation;
import isnork.sim.SeaLifePrototype;
import isnork.sim.iSnorkMessage;

import java.awt.geom.Point2D;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class SimpleCommunicator implements CommPrototype {
	
	protected RecentlyCommunicatedSightings recentSightings = new RecentlyCommunicatedSightings();
	protected Encoding encoding;
	protected IncomingMessageQueue queuedMessages;

	@Override
	public String createMessage(Point2D myPosition,
			Set<Observation> whatYouSee, Set<iSnorkMessage> incomingMessages,
			Set<Observation> playerLocations) {
		
		/**
		 * 
		 * We should communicate more info about static creatures if divers are far away
		 * We can more communicate more about dynamic creatures if divers are nearby
		 * Relative value of the target should be a factor
		 * Relative density of the species can also be a factor
		 * 
		 * 
		 */
		
		processIncoming(myPosition, whatYouSee,  incomingMessages, playerLocations);
		//System.out.println(encoding.encode(recentSightings.tick().getNewHVT(whatYouSee), myPosition));
		return encoding.encode(recentSightings.tick().getNewHVT(whatYouSee), myPosition);
		
	}

	private void processIncoming(Point2D myPosition,
			Set<Observation> whatYouSee, Set<iSnorkMessage> incomingMessages,
			Set<Observation> playerLocations) {
		queuedMessages.load(myPosition, whatYouSee,  incomingMessages, playerLocations);
		
	}

	@Override
	public Direction getDirection() {
		return queuedMessages.getHVTDirection();
	}
	
	
	
	@Override
	public void init(Set<SeaLifePrototype> seaLifePossibilites, int penalty,
			int d, int r, int n) {
		
		encoding = new SimpleEncoding();
		encoding.init(seaLifePossibilites, penalty, d, r, n);
		
	}
	

}
