package isnork.g9.utils.risk;

public class OverallRiskProfile {
	double median, stdDev;
	public OverallRiskProfile(double median, double stdDev) {
		this.median = median;
		this.stdDev = stdDev;
	}
	public double getMedian() { return median; }
	public double getStdDev() { return stdDev; }
}
