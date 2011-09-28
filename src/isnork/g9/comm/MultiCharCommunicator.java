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
		
		System.out.println("Current buffer: " + buffer);
		
		if (buffer.length() == 0) {
			Observation temp = recentSightings.getNewHVT(whatYouSee);
			if (temp != null && !temp.getName().equals("Diver by G9")) {
				buffer = MultiCharEncoding.encode(temp, myPosition);
				System.out.println("Want to send: " + buffer + ", will be buffered");
			}
			
			System.out.println("Returned null, didn't see anything interesting");
			return null;
			
		} else {
			String toReturn = Character.toString(buffer.charAt(0));
			buffer = buffer.substring(1);
			System.out.println("Sent: " + toReturn);
			return toReturn;
		}
	}

	private void processIncoming(Point2D myPosition,
			Set<Observation> whatYouSee, Set<iSnorkMessage> incomingMessages,
			Set<Observation> playerLocations) {
		
		//TODO remove later
		System.out.print("Received: ");
		for (iSnorkMessage m : incomingMessages)
			System.out.print(m.getSender() + ":" + m.getMsg() + ", ");
		System.out.println();
		
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

	@Override
	public ObservationMemory getObservationMemory() {
		return memory;
	}
	

}
