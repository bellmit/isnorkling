package isnork.g9.comm;

import isnork.g9.utils.GameParams;
import isnork.sim.Observation;
import isnork.sim.iSnorkMessage;

import java.awt.geom.Point2D;
import java.util.Set;

public class MultiCharCommunicator implements CommPrototype {
	
	protected RecentlyCommunicatedSightings recentSightings = new RecentlyCommunicatedSightings();
	protected MessageProcessor queuedMessages = new MessageProcessor();
	protected ObservationMemory<Observation> memory = new ObservationMemory<Observation>();

	private String buffer = "";
	
	@Override
	public String createMessage(Point2D myPosition,
			Set<Observation> whatYouSee, Set<iSnorkMessage> incomingMessages,
			Set<Observation> playerLocations) {
		
		processIncoming(myPosition, whatYouSee,  incomingMessages, playerLocations);
		
		if (buffer.length() == 0) {
			Observation temp = recentSightings.getNewHVT(whatYouSee);
			if (temp != null) {
				buffer = MultiCharEncoding.encode(temp, myPosition);
			}
		}

		if (buffer.length() != 0) {
			String toReturn = Character.toString(buffer.charAt(0));
			buffer = buffer.substring(1);
			return toReturn;
		} else {
			return null;
		}
	}

	private void processIncoming(Point2D myPosition,
			Set<Observation> whatYouSee, Set<iSnorkMessage> incomingMessages,
			Set<Observation> playerLocations) {
		memory.processObservations(myPosition, whatYouSee);
		queuedMessages.load(myPosition, whatYouSee,  incomingMessages, playerLocations, memory);
		
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
