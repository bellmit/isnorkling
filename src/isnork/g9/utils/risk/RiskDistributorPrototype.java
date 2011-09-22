package isnork.g9.utils.risk;

import isnork.g9.Diver;
import isnork.g9.utils.BoardParams;

import java.util.Set;

public interface RiskDistributorPrototype {
	public void setBoardParams(BoardParams b);
	public void setOverallRiskProfile(OverallRiskProfile r);
	public void distribute(Set<Diver> divers);
}
