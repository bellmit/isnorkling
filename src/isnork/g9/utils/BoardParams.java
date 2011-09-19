package isnork.g9.utils;

import isnork.sim.SeaLifePrototype;

import java.util.ArrayList;
import java.util.Set;

/**
 * mostly stubs, will implement later
 * @author yufeiliu
 *
 */
public class BoardParams {
	
	private int d, n;
	private Set<SeaLifePrototype> sealife;
	
	public BoardParams(Set<SeaLifePrototype> sealife, int d, int n) {
		this.sealife = sealife;
		this.d = d;
		this.n = n;
	}
	
	public int getDimension() {
		return d;
	}
	
	public int getNumDivers() {
		return n;
	}
	
	// I need to sort the prototypes to calculate the Gini Coefficient in RiskEvaluator.java,
	// so let's assume this will be an arraylist.
	public ArrayList<SeaLifePrototype> getArrayListOfSeaLifePrototypes() {
		ArrayList<SeaLifePrototype> slpList = new ArrayList<SeaLifePrototype>();
		
		//TODO sort this
		slpList.addAll(sealife);
		return slpList;
	}
}
