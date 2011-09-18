package isnork.g9.utils.risk;

import isnork.g9.utils.BoardParams;
//import isnork.sim.SeaLifePrototype;

//import java.util.Set;

public interface RiskEvaluatorPrototype {
	public void setBoardParams(BoardParams b);
	public OverallRiskProfile getOverallRiskProfile();
}
