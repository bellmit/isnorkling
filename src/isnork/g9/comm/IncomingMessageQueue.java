package isnork.g9.comm;

import isnork.sim.GameObject;
import isnork.sim.Observation;
import isnork.sim.iSnorkMessage;
import isnork.sim.GameObject.Direction;

import java.awt.geom.Point2D;
import java.util.Iterator;
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
		
		return new Suggestion(choices[choice],
				((double)msg.getEstValue())/SimpleEncoding.getMaxVal());
		
	}
	
	public void load(Point2D myPosition, Set<Observation> whatYouSee, Set<iSnorkMessage> incomingMessages,
			Set<Observation> playerLocations, Encoding encoding){
		
		Iterator<iSnorkMessage> iter = incomingMessages.iterator();
		
		while(iter.hasNext()){
			iSnorkMessage iMsg = iter.next();
			SimpleMessage sMsg = (SimpleMessage) encoding.decode(iMsg.getMsg());
			sMsg.setDiverCoord(iMsg.getLocation());
			msgHeap.add(sMsg);
			
		}
		
	}
	
	public IncomingMessageQueue tick(){
		
		Iterator<SimpleMessage> iter = msgHeap.iterator();
		while(iter.hasNext()){
			SimpleMessage msg = iter.next();
			msg.age();
			if(msg.die())msgHeap.remove(msg);
		}
		
		return this;
	}

}
