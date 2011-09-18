package isnork.g9.utils.risk;

public class OverallRiskProfile {
	double expectation, goodGini, badGini;
	public OverallRiskProfile(double expectation, double goodGini, double badGini) {
		this.expectation = expectation;
		this.goodGini = goodGini;
		this.badGini = badGini;
	}
	public double getExpectation() { return expectation; }
	public double getGoodGini() { return goodGini; }
	public double getBadGini() { return badGini; }
}
