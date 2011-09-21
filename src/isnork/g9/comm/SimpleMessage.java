package isnork.g9.comm;

import isnork.sim.iSnorkMessage;

import java.awt.geom.Point2D;
import java.util.Comparator;

public class SimpleMessage implements Message,Comparator<SimpleMessage>, Comparable<SimpleMessage> {
	
	private iSnorkMessage iMsg;
	
	public SimpleMessage(iSnorkMessage msg) {
		iMsg = msg;
	}
	
	public SimpleMessage(){}	
	
	private double estimatedValue;
	private boolean dynamic;
	private int age=0;
	
	
	@Override
	public void age(){age++;}
	
	@Override
	public boolean die(){
		if(dynamic) return age > TTL;
		return false;
		}
	
	public boolean isDynamic() {
		return dynamic;
	}
	
	
	public void setDynamic(boolean dynamic) {
		this.dynamic = dynamic;
	}

	@Override
	public int compare(SimpleMessage msg1, SimpleMessage msg2) {
		if(msg1.estimatedValue > msg2.estimatedValue) return -1;
		if(msg1.estimatedValue < msg2.estimatedValue) return 1;
		return 0;
	}

	@Override
	public int compareTo(SimpleMessage msg2) {
		if(estimatedValue > msg2.estimatedValue) return -1;
		if(estimatedValue < msg2.estimatedValue) return 1;
		return 0;
	}

	@Override
	public String getRawMessage() {
		return iMsg.getMsg();
	}

	@Override
	public Point2D getSenderLocation() {
		return iMsg.getLocation();
	}

	@Override
	public double getEstimatedValue() {
		return estimatedValue;
	}

	@Override
	public void setEstimatedValue(double val) {
		estimatedValue = val;
	}

	@Override
	public int getSender() {
		return iMsg.getSender();
	}

}
