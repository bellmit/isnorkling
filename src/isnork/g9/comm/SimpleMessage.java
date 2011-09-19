package isnork.g9.comm;

import java.awt.geom.Point2D;
import java.util.Comparator;

public class SimpleMessage implements Message,Comparator<SimpleMessage> {
	
	private String rawMsg;
	private Point2D diverCoord;
	private int estValue;
	private boolean dynamic;
	private int age=0;
	private static int TTL = 12;
	
	
	public void age(){age++;}
	
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
	public String getRawMsg() {
		return rawMsg;
	}
	public void setRawMsg(String rawMsg) {
		this.rawMsg = rawMsg;
	}
	public Point2D getDiverCoord() {
		return diverCoord;
	}

	public void setDiverCoord(Point2D diverCoord) {
		this.diverCoord = diverCoord;
	}

	public int getEstValue() {
		return estValue;
	}

	public void setEstValue(int estValue) {
		this.estValue = estValue;
	}

	@Override
	public int compare(SimpleMessage msg1, SimpleMessage msg2) {
		if(msg1.estValue < msg2.estValue) return -1;
		if(msg1.estValue > msg2.estValue) return 1;
		return 0;
	}

}
