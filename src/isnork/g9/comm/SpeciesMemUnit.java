package isnork.g9.comm;

import isnork.sim.SeaLifePrototype;

public class SpeciesMemUnit extends SeaLifePrototype {
	
	private int frequency = 0;
	
	public SpeciesMemUnit(SeaLifePrototype slp) {
		
	}

	public int getFrequency(){
		return frequency;
	}
	
	public Object clone(){
		SpeciesMemUnit spm = new SpeciesMemUnit((SeaLifePrototype)super.clone());
		spm.frequency = frequency;
		return spm;
	}

}
