package isnork.g9.utils;

import isnork.sim.SeaLifePrototype;

import java.util.HashSet;
import java.util.Set;

/**
 * mostly stubs, will implement later
 * @author yufeiliu
 *
 */
public class BoardParams {
	public int getDimension() {
		return 10;
	}
	
	public int getNumDivers() {
		return 20;
	}
	
	public Set<SeaLifePrototype> getSetOfSeaLifePrototypes() {
		return new HashSet<SeaLifePrototype>();
	}
}
