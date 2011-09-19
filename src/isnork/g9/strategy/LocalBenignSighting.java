package isnork.g9.strategy;

import isnork.g9.PlayerPrototype;
import isnork.sim.Observation;
import isnork.sim.GameObject.Direction;

import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;

public class LocalBenignSighting implements LocalBenignSightingPrototype {

	private Set<Observation> sighting;
	private PlayerPrototype player;
	
	@Override
	public void setObservations(Set<Observation> observations) {
		sighting = observations;
		
		Point2D loc = player.getLocation();
		
		HashSet<ObservationWrapper> nextSighting = new HashSet<ObservationWrapper>();
		
		for (Observation ob : sighting) {
			
			if (ob.getName().equals("Diver by G9")) {
				continue;
			}
			
			ObservationWrapper o = new ObservationWrapper();
			o.happiness = ob.happiness();
			o.direction = ob.getDirection();
			o.id = ob.getId();
			
			double dx = (o.direction == null ? 0 : o.direction.dx);
			double dy = (o.direction == null ? 0 : o.direction.dy);
			
			double x = ob.getLocation().getX();
			double y = ob.getLocation().getY();
			
			
			o.location = new Point2D.Double(x + dx, y + dy);
			nextSighting.add(o);
		}
	}

	@Override
	public Direction getDirection() {
		return Direction.allBut(Direction.STAYPUT).get( (int)(Math.random() * 8) );
	}

	@Override
	public double getConfidence() {
		// TODO Auto-generated method stub
		return Math.random();
	}

	@Override
	public void setPlayer(PlayerPrototype p) {
		player = p;
	}

}
