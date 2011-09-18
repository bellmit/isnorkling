package isnork.g9.utils;

import isnork.sim.SeaLifePrototype;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
	
	// I need to sort the prototypes to calculate the Gini Coefficient in RiskEvaluator.java,
	// so let's assume this will be an arraylist.
	public ArrayList<SeaLifePrototype> getArrayListOfSeaLifePrototypes() {
		ArrayList<SeaLifePrototype> slpList = new ArrayList<SeaLifePrototype>();
		return slpList;
	}
}
