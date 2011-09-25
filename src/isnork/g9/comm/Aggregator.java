package isnork.g9.comm;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import isnork.sim.iSnorkMessage;

public class Aggregator {

	private MultiCharMessage msg;
	private String buffer = ""; 
	private List<Point2D> diverLocations = new ArrayList<Point2D>();
	
	public boolean processMessage(iSnorkMessage m) {
		diverLocations.add(m.getLocation());
		buffer += m.getMsg();
		
		if (buffer.length() == 3) {
			msg = MultiCharEncoding.decode(buffer);
			msg.diverLocations = diverLocations;
			
			diverLocations = new ArrayList<Point2D>();
			buffer = "";
			
			return true;
		}
		
		return false;
	}
	
	public MultiCharMessage getMessage() {
		return msg;
	}
}
