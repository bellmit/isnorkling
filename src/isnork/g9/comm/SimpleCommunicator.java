package isnork.g9.comm;

import isnork.g9.utils.GameParams;
import isnork.sim.Observation;
import isnork.sim.SeaLifePrototype;
import isnork.sim.iSnorkMessage;

import java.awt.geom.Point2D;
import java.util.Set;

public class SimpleCommunicator implements CommPrototype {
	
	protected RecentlyCommunicatedSightings recentSightings = new RecentlyCommunicatedSightings();
	protected Encoding encoding = new SimpleEncoding();
	protected IncomingMessageQueue queuedMessages = new IncomingMessageQueue();
	protected ObservationMemory memory = new ObservationMemory<Observation, SeaLifePrototype>();

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
		String msg = encoding.encode(recentSightings.getNewHVT(whatYouSee), myPosition);
		if(msg == null){
			System.out.println("COMM WARN: Sending null msg");
		}
		return msg;
		
	}

	private void processIncoming(Point2D myPosition,
			Set<Observation> whatYouSee, Set<iSnorkMessage> incomingMessages,
			Set<Observation> playerLocations) {
		memory.processObservations(whatYouSee);
		queuedMessages.load(myPosition, whatYouSee,  incomingMessages, playerLocations,
				memory, encoding);
		
		
	}

	@Override
	public Suggestion getDirection(Point2D myPosition) {
		return queuedMessages.getHVTDirection(myPosition);
	}

	@Override
	public void init() {
		memory.init(GameParams.getSeaLifePossibilites());
		
	}
	

}
