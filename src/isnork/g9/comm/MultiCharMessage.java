package isnork.g9.comm;

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
	
	public void age() {
		//TODO could be smarter
		estimatedValue *= 0.9;
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
		return "octant:"+octant+",distance:"+distance+",dir:"+dir+",species:"+species+"id:"+id;
	}
}
