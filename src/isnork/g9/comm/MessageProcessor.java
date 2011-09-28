package isnork.g9.comm;

import isnork.g9.utils.GameParams;
import isnork.sim.GameObject;
import isnork.sim.GameObject.Direction;
import isnork.sim.Observation;
import isnork.sim.iSnorkMessage;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class MessageProcessor {
	
	private int ticker = 0;
	private MultiCharMessage currentDest = null;
	private static final int NUM_TURNS_TO_FOLLOW_TARGET = 3;
	
	private Aggregator aggregator = new Aggregator();
	
	private PriorityQueue<MultiCharMessage> msgHeap = new PriorityQueue<MultiCharMessage>();
	private static final Direction[] choices = new Direction[] {Direction.E, Direction.NE,
		Direction.N, Direction.NW, Direction.W, Direction.SW, Direction.S, Direction.SE,
		Direction.STAYPUT}; 
	
	
	public Suggestion getHVTDirection(Point2D myPosition){
		
		if(msgHeap.isEmpty()){
			System.out.println("Heap is empty");
			return new Suggestion(GameObject.Direction.STAYPUT, 0);
		}
		
		if(currentDest == null || currentDest.diverLocations.get(0).distance(myPosition)==0
				|| (ticker % NUM_TURNS_TO_FOLLOW_TARGET == 0
				&& currentDest.getEstimatedValue() < msgHeap.peek().getEstimatedValue())){
			currentDest = msgHeap.remove();
		}
		
		//TODO may want to use newer location
		Point2D dest = currentDest.diverLocations.get(0);
		
		
		double thetaRad = Math.atan2(myPosition.getY()-dest.getY(), dest.getX()-myPosition.getX());
		double thetaDeg = thetaRad * 180 / Math.PI;
		if(thetaDeg < 0 ) thetaDeg += 360;
		int dirChoice = ((int)thetaDeg)/45 + ( ((int) thetaDeg)%45 < 23 ? 0 : 1);
		
		System.out.println("raw value: " + currentDest.getEstimatedValue());
		System.out.println("normalizer: " + GameParams.getAverageHappiness());
		
		double confidence = normalize(currentDest.getEstimatedValue());
		
		if (choices[dirChoice].equals(Direction.STAYPUT)) {
			confidence = 0;
		}
		
		return new Suggestion(choices[dirChoice], confidence);
	}
	
	private double normalize(double val) {
		double before = val / GameParams.getAverageHappiness();
		
		double density = GameParams.getNumberOfHappy() / 4.0 / GameParams.getGridSize() / GameParams.getGridSize();
		
		double invertedDensity = 1.0 - density;
		if (invertedDensity < 0.01) invertedDensity = 0.01;
		
		invertedDensity *= 200;
		
		double temp = Math.max(Math.log(invertedDensity), 1.0);
		
		return Math.min(1.0, before * temp);
	}
	
	public void load(Point2D myPosition, Set<Observation> whatYouSee, Set<iSnorkMessage> incomingMessages,
			Set<Observation> playerLocations, ObservationMemory<Observation> memory){
		
		tick();
		
		Set<MultiCharMessage> parsedMsgs = aggregator.processMessage(incomingMessages);
		
		for(MultiCharMessage msg : parsedMsgs){
			msg.setEstimatedLocation();
			double val = MultiCharMessageEvaluator.evaluate(myPosition, memory, msg);
			msg.setEstimatedValue(val);
			
			if(val > 0){
				msgHeap.add(msg);
			}
			
		}
		
	}
	
	private void tick(){
		List<MultiCharMessage> deadMessages = new ArrayList<MultiCharMessage>();
		
		for(MultiCharMessage msg : msgHeap){
			msg.age();
			if(msg.die()) deadMessages.add(msg);
		}
		
		for(MultiCharMessage msg : deadMessages)
			msgHeap.remove(msg);
		
		if(currentDest!=null) currentDest.age();		
		ticker++;
	}

}
