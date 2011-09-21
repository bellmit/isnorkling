package isnork.g9.comm;

import isnork.sim.Observation;
import isnork.sim.SeaLifePrototype;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ObservationMemory<K extends Observation, T extends SeaLifePrototype> {
	
	private Map<Integer, K> creatureMemory = new HashMap<Integer, K>();
	private Map<String, T> speciesMemory = new HashMap<String, T>();
	private Set<T> unseenSpecies = new HashSet<T>();
	
	public void ObservationMemory(final Set<T> seaCreatures){
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
	
	

}
