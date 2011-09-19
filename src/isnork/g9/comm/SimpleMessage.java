package isnork.g9.comm;

import java.awt.geom.Point2D;
import java.util.Comparator;

public class SimpleMessage implements Message,Comparator<SimpleMessage> {
	
	private String rawMsg;
	private Point2D diverCoord;
	private int estValue;
	
	
	@Override
	public int compare(SimpleMessage msg1, SimpleMessage msg2) {
		if(msg1.estValue < msg2.estValue) return -1;
		if(msg1.estValue > msg2.estValue) return 1;
		return 0;
	}

}
