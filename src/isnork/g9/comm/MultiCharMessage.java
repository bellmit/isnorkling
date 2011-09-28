package isnork.g9.comm;

import isnork.g9.utils.GameParams;
import isnork.g9.utils.Parameter;
import isnork.sim.GameObject.Direction;

import java.awt.geom.Point2D;
import java.util.Comparator;
import java.util.List;

public class MultiCharMessage implements Comparable<MultiCharMessage>, Comparator<MultiCharMessage>{
	public int octant;
	public int distance;
	public Direction dir;
	public String species;
	public int id;
	public Point2D estimatedLocation;
	private double estimatedValue;
	
	public List<Point2D> diverLocations;
	
	public int getRawValue(){
		return 0; 
	}
	
	public double getEstimatedValue() {
		return estimatedValue;
	}
	
	public void setEstimatedValue(double e) {
		estimatedValue = e;
	}
	
	private boolean isStatic() {
		return (GameParams.getSeaLife(species).getSpeed()==0);
	}
	
	private double getDx() {
		double adjustedTargetDistance = GameParams.getVisibilityRadius() * distance / 4.0 ;
		
		Direction tempDir = Parameter.ALL_DIRS[octant];
		
		double growthFactor = adjustedTargetDistance / Math.sqrt(Math.pow(tempDir.dx, 2.0) + Math.pow(tempDir.dy, 2.0));
		
		return tempDir.dx * growthFactor;
	}
	
	private double getDy() {
		double adjustedTargetDistance = GameParams.getVisibilityRadius() * distance / 4.0 ;

		Direction tempDir = Parameter.ALL_DIRS[octant];
		
		double growthFactor = adjustedTargetDistance / Math.sqrt(Math.pow(tempDir.dx, 2.0) + Math.pow(tempDir.dy, 2.0));
		
		return tempDir.dy * growthFactor;
	}
	
	public void setEstimatedLocation() {
		Point2D senderLoc = diverLocations.get(0);
		
		double dy = getDy();
		double dx = getDx();
		
		Point2D initial = new Point2D.Double(senderLoc.getX() + dx, senderLoc.getY() + dy);
		
		if (this.isStatic()) {
			estimatedLocation = initial;
			return;
		}
		
		//Predict the movement of creature during 3 turns it took to send the message
		for (int i = 0; i < 3; i++) {
			initial.setLocation(initial.getX() + dir.dx, initial.getY() + dir.dy);
		}
		
		estimatedLocation = initial;
	}
	
	public void age() {
		//TODO could be smarter
		if (!this.isStatic()) {
			estimatedValue *= Parameter.COMMUNICATION_MESSAGE_VALUE_DECAY_FACTORY;
			estimatedLocation.setLocation(estimatedLocation.getX() + dir.dx, estimatedLocation.getY() + dir.dy);
		}
	}
	
	public boolean die() {
		return false;
	}

	@Override
	public int compare(MultiCharMessage o1, MultiCharMessage o2) {
		Double wrapped = new Double(o1.estimatedValue);
		return wrapped.compareTo(o2.estimatedValue);
	}

	@Override
	public int compareTo(MultiCharMessage o) {
		Double wrapped = new Double(estimatedValue);
		return wrapped.compareTo(o.getEstimatedValue());
	}
	
	public String toString() {
		return "octant:"+octant+",distance:"+distance+",dir:"+dir+",species:"+species+",id:"+id;
	}
}
