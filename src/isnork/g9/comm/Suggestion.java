package isnork.g9.comm;

import isnork.sim.GameObject;
import isnork.sim.GameObject.Direction;

public class Suggestion {
	
	private GameObject.Direction dir;
	private double confidence;
	
	public Suggestion(Direction dir, double confidence) {
		super();
		this.dir = dir;
		this.confidence = confidence;
	}
	public GameObject.Direction getDir() {
		return dir;
	}
	public void setDir(GameObject.Direction dir) {
		this.dir = dir;
	}
	public double getConfidence() {
		return confidence;
	}
	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}
	
	

}
