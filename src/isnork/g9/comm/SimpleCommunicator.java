package isnork.g9.comm;

import isnork.sim.Observation;
import isnork.sim.SeaLifePrototype;
import isnork.sim.iSnorkMessage;

import java.awt.geom.Point2D;
import java.util.Set;

public class SimpleCommunicator implements CommPrototype {
	
	protected RecentlyCommunicatedSightings recentSightings = new RecentlyCommunicatedSightings();
	protected Encoding encoding;
	protected IncomingMessageQueue queuedMessages = new IncomingMessageQueue();

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
		queuedMessages.load(myPosition, whatYouSee,  incomingMessages, playerLocations,encoding);
		
	}

	@Override
	public Suggestion getDirection(Point2D myPosition) {
		return queuedMessages.tick().getHVTDirection(myPosition);
	}
	
	
	
	@Override
	public void init(Set<SeaLifePrototype> seaLifePossibilites, int penalty,
			int d, int r, int n) {
		
		encoding = new SimpleEncoding();
		encoding.init(seaLifePossibilites, penalty, d, r, n);
		
	}
	

}
