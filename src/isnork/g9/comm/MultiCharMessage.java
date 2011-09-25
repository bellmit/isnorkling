package isnork.g9.comm;

import isnork.sim.GameObject.Direction;

import java.awt.geom.Point2D;
import java.util.Comparator;
import java.util.List;

public class MultiCharMessage implements Comparable<MultiCharMessage>, Comparator<MultiCharMessage>{
	public int octant;
	public int distance;
	public Direction dir;
	public Point2D senderLoc;
	public String species;
	public int id;
	
	public List<Point2D> diverLocations;
	
	public int getRawValue(){
		return 0; 
	}

	@Override
	public int compare(MultiCharMessage o1, MultiCharMessage o2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int compareTo(MultiCharMessage o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
