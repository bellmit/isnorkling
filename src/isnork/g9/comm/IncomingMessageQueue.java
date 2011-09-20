package isnork.g9.comm;

import isnork.sim.GameObject;
import isnork.sim.GameObject.Direction;
import isnork.sim.Observation;
import isnork.sim.iSnorkMessage;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class IncomingMessageQueue {
	
	private PriorityQueue<SimpleMessage> msgHeap = new PriorityQueue<SimpleMessage>();
	private Direction[] choices = new Direction[] { Direction.N, Direction.NW, 
			Direction.W, Direction.SW, Direction.S, 
            Direction.SE, Direction.E, Direction.NE, 
            Direction.STAYPUT }; 
	
	
	public Suggestion getHVTDirection(Point2D myPosition){
		
		if(msgHeap.isEmpty()){
			return new Suggestion(GameObject.Direction.STAYPUT,1);
		}
		
		SimpleMessage msg = msgHeap.remove();
		Point2D dest = msg.getDiverCoord();
		
		double thetaRad = Math.atan2(dest.getY()-myPosition.getY(), dest.getX()-myPosition.getX());
		double thetaDeg = thetaRad * Math.PI / 180;
		if(thetaDeg < 0 ) thetaDeg += 360;
		
		int choice = (int) thetaDeg/45;
		
		// TODO: remove hard coded 500 and put sensible call
		return new Suggestion(choices[choice],
				((double)msg.getEstValue())/500);
		
	}
	
	public void load(Point2D myPosition, Set<Observation> whatYouSee, Set<iSnorkMessage> incomingMessages,
			Set<Observation> playerLocations, Encoding encoding){
		
		for(iSnorkMessage iMsg : incomingMessages){
			SimpleMessage sMsg = (SimpleMessage) encoding.decode(iMsg.getMsg());
			sMsg.setDiverCoord(iMsg.getLocation());
			msgHeap.add(sMsg);
			
		}
		
	}
	
	public IncomingMessageQueue tick(){
		
		List<SimpleMessage> deadMessages = new ArrayList<SimpleMessage>();
		
		for(SimpleMessage msg : msgHeap){
			msg.age();
			if(msg.die())deadMessages.add(msg);
		}
		
		for(SimpleMessage msg : deadMessages)
			msgHeap.remove(msg);
				
		return this;
	}

}
