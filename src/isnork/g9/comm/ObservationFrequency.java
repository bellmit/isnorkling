package isnork.g9.comm;

import java.awt.geom.Point2D;

import isnork.sim.Observation;
import isnork.sim.GameObject.Direction;

public class ObservationFrequency {
	
	Observation obs;
	
	public Point2D getLocation(){
		return obs.getLocation();
	}
	public int getId()
	{
		return obs.getId();
	}
	public String getName()
	{
		return obs.getName();
	}
	public boolean isDangerous()
	{
		return obs.isDangerous();
	}
	public double happinessD()
	{
		return obs.happinessD();
	}
	public int happiness()
	{
		return (int) obs.happiness();
	}
	public Direction getDirection() {
		return obs.getDirection();
	}

}
