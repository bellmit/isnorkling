package isnork.g9.comm;

import java.awt.geom.Point2D;

public interface MessageEvaluator<T extends Message> {
	
	public double evaluate(Point2D myLocation, ObservationMemory memory, T msg);

}
