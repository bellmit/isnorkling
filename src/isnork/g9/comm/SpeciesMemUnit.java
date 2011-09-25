package isnork.g9.comm;

import isnork.sim.SeaLifePrototype;

public class SpeciesMemUnit{
	
	private int frequency = 0;
	private int id = 0;
	private SeaLifePrototype slp = null;
	
	public int getFrequency(){
		return frequency;
	}
	
	public SpeciesMemUnit(SeaLifePrototype slp, int id){
		this.slp = slp;
		this.id = id;
	}
	
	public void incrementFrequency(){
		frequency++;
	}
	
	public int getId(){
		return id;
	}

	public SeaLifePrototype getSlp() {
		return slp;
	}

}
