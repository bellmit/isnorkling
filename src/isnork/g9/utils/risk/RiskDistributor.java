package isnork.g9.utils.risk;

import isnork.g9.PlayerPrototype;
import isnork.g9.utils.BoardParams;
import java.util.Set;

public class RiskDistributor implements RiskDistributorPrototype {

	BoardParams b;
	OverallRiskProfile r;

	@Override
	public void setBoardParams(BoardParams b) {
		this.b = b;
	}

	@Override
	public void setOverallRiskProfile(OverallRiskProfile r) {
		this.r = r;
	}

	@Override
	public void distribute(Set<PlayerPrototype> divers) {
		
		// Common risk factor
		double risk;
		risk = r.getBadGini();
		if (r.getExpectation() <= 0) { 
			risk -= 0.5;
		}
		if (risk < 0) {
			risk = 0;
		}
		risk = 1 - risk;
			
		// v0: XXX there is no differentiation between divers - only the common risk factor used.
		for (PlayerPrototype diver : divers) {		
			IndividualRiskProfile prof = new IndividualRiskProfile();
			prof.setRiskAvoidance(risk);			
			diver.setIndividualRiskProfile(prof);
		}	
		
	}

}
