package isnork.g9.comm;

import isnork.sim.Observation;
import isnork.sim.SeaLifePrototype;

import java.awt.geom.Point2D;
import java.util.Set;

public interface Encoding {
	
	public String encode(Observation obs, Point2D myPosition);
	
	public Message decode(String str);

}
