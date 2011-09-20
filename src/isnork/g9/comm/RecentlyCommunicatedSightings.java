package isnork.g9.comm;


import isnork.sim.Observation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RecentlyCommunicatedSightings {
	
	private Map<Integer, CommunicatedSighting> recentlyCommunicatedSightings = new HashMap<Integer, CommunicatedSighting>();
	
	public RecentlyCommunicatedSightings tick(){
		
		List<CommunicatedSighting> deadSightings = new ArrayList<CommunicatedSighting>();
		for(CommunicatedSighting sighting : recentlyCommunicatedSightings.values()){
			sighting.age();
			if(sighting.die()) deadSightings.add(sighting);
		}
		
		for(CommunicatedSighting deadSighting : deadSightings){
			recentlyCommunicatedSightings.remove(deadSighting.getObs().getId());
		}
		
		return this;
	}
	
	
	public Observation getNewHVT(Set<Observation> whatYouSee){
		
		int max = -1;
		Observation newHVT = null;
		Iterator<Observation> iter = whatYouSee.iterator();
		while(iter.hasNext()){
			Observation obs = iter.next();
			if(!recentlyCommunicatedSightings.containsKey(obs.getId()) && obs.happiness()>max){
				newHVT = obs;
			}
		}
		
		if(newHVT != null){
			recentlyCommunicatedSightings.put(newHVT.getId(), new CommunicatedSighting(newHVT));			
		}
		
		return newHVT;			
	}
	
	protected class CommunicatedSighting{
		private int age;
		private Observation obs;
		private static final int TTL = 12;
		
		public CommunicatedSighting(Observation obs){
			this.obs = obs;
			this.age=0;
			}
		
		public void age(){age++;}
		public boolean die(){return age >= TTL;}

		public Observation getObs() {
			return obs;
		}
		
	}

}
