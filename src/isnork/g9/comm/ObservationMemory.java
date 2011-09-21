package isnork.g9.comm;

import isnork.g9.utils.GameParams;
import isnork.sim.Observation;
import isnork.sim.SeaLifePrototype;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ObservationMemory<K extends Observation, T extends SeaLifePrototype> {
	
	private Map<Integer, K> creatureMemory = new HashMap<Integer, K>();
	private Map<String, T> speciesMemory = new HashMap<String, T>();
	private Set<T> unseenSpecies = new HashSet<T>();
	private Set<Point2D> locationMemory = new HashSet<Point2D>();
	
	public void init(final Set<T> seaCreatures){
		unseenSpecies.addAll(seaCreatures);
	}
	
	public void addCreatureToMemory(K creature){
		creatureMemory.put(creature.getId(), creature);
	}
	
	public void addSpeciesToMemory(T species){
		if(!unseenSpecies.contains(species)){
			speciesMemory.put(species.getName(), species);
		}
		
	}
	
	public void addLocationToMemory(Point2D myLoc){
		locationMemory.add(myLoc);
	}
	
	public double closestDistanceToLocation(Point2D location){
		double minDistance = GameParams.getGridSize() * 2 * 1.5 +1;
		for(Point2D loc : locationMemory){
			double dist = loc.distance(location);
			if( dist <= GameParams.getVisibilityRadius())
				return 0;
			if(dist < minDistance) minDistance = dist;
		}
		return minDistance;
	}

	public void processObservations(Set<Observation> whatYouSee) {
	
		for(Observation obs : whatYouSee){
			
		}
		
	}

}
