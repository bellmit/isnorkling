package isnork.g9.utils.risk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.lang.Integer;
import isnork.g9.utils.BoardParams;
import isnork.sim.SeaLifePrototype;

/**
 * @author chrishaum
 *
 */

public class RiskEvaluator implements RiskEvaluatorPrototype {
	
	private BoardParams b;
	
	@Override
	public void setBoardParams(BoardParams b) {
		// TODO Auto-generated method stub
		this.b = b;
	}

	@Override
	public OverallRiskProfile getOverallRiskProfile() {
		// TODO Auto-generated method stub
		calcGrossExpec();
		calcGinis();
		return new OverallRiskProfile(grossExpec, goodGini, badGini);
	}
	
	// Most informative metrics
	private int grossExpec;
	private double goodGini, badGini;
	private ArrayList<SeaLifePrototype> slps;
	
	// Calculates the Gini coefficient, which indicates how much the happiness is skewed towards
	// the most happy creatures. The result should range from 0 (happiness is distributed perfectly
	// evenly) to 1 (one creature has all the happiness).
	private double calcGiniCoeff(ArrayList<SeaLifePrototype> slps, boolean isDangerous){
		int i = 1;
		int sum = 0;
		int g = 0;
		for (SeaLifePrototype slp : slps) {
			if (isDangerous == slp.isDangerous()) {
				sum += slp.getHappiness();
				g += i * slp.getHappiness();
				i += 1;
			}
		}
		int u = sum / i;
		
		//TODO remove monkey patch
		if (i==1) i = 2;
		if (u==0) u = 1;
		return (i+1)/(i-1) - 2 * g / (i * (i-1) * u);
	}
	
	// Calculate the Gini Coefficients for good and bad creatures
	private void calcGinis() {
		slps = b.getArrayListOfSeaLifePrototypes();
		// Sort into descending order by getHappiness()
		// XXX This is possibly buggy:
		// 		1. need to verify the comparator ordering
		Collections.sort(slps, new Comparator<Object>(){
			public int compare(Object o1, Object o2) {
				SeaLifePrototype p1 = (SeaLifePrototype) o1;
				SeaLifePrototype p2 = (SeaLifePrototype) o2;
				Integer i = new Integer(p1.getHappiness());
				return i.compareTo(p2.getHappiness());
			}
		});
		goodGini = calcGiniCoeff(slps, false);
		badGini = calcGiniCoeff(slps, true);
	}
	
	// Calculate the "Gross Expectation", a.k.a. the expected points if all the divers
	// were to collide with all the creatures.
	private void calcGrossExpec(){
		int expec = 0;
		slps = b.getArrayListOfSeaLifePrototypes();
		for (SeaLifePrototype slp : slps) {
			int meanCount = (slp.getMaxCount() - slp.getMinCount()) / 2;
			int creatureExpec = meanCount * slp.getHappiness();
			if (slp.isDangerous()) {creatureExpec *= -1;}
			expec += creatureExpec;
		}
		grossExpec = expec;
	}

}
