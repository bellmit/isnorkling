package isnork.g9.comm;

import java.awt.geom.Point2D;

public interface Message {
	
	public static final int TTL = 12;
	
	public void age();
	public boolean die();
	
	public String getRawMessage();
	public Point2D getSenderLocation();
	public int getSender();
	public double getEstimatedValue();
	public void setEstimatedValue(double val);
	
	
}
