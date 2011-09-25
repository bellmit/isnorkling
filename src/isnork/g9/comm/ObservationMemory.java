package isnork.g9.comm;

import isnork.g9.utils.GameParams;
import isnork.sim.Observation;
import isnork.sim.SeaLifePrototype;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ObservationMemory<K extends Observation> {
	
	private Set<Integer> creatureMemory = new HashSet<Integer>();
	private Map<String, SpeciesMemUnit> speciesMemory = new HashMap<String, SpeciesMemUnit>();
	private Set<Point2D> locationMemory = new HashSet<Point2D>();
	
	public void init(final Set<SeaLifePrototype> seaCreatures){
		int i=0;
		for(SeaLifePrototype slp : seaCreatures){
			speciesMemory.put(slp.getName(), new SpeciesMemUnit(slp, i++));
		}
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

	public void processObservations(Point2D myPosition, Set<Observation> whatYouSee) {
	
		locationMemory.add(myPosition);
		for(Observation obs : whatYouSee){
			speciesMemory.get(obs.getName()).incrementFrequency();
			creatureMemory.add(obs.getId());
		}
		
	}

}
