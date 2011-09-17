package isnork.g9.utils.risk;

import isnork.g9.PlayerPrototype;
import isnork.g9.utils.BoardParams;

import java.util.Set;

public interface RiskDistributorPrototype {
	public void setBoardParams(BoardParams b);
	public void setOverallRiskProfile(OverallRiskProfile r);
	public void distribute(Set<PlayerPrototype> divers);
}
