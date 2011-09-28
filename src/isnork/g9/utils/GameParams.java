package isnork.g9.utils;

import isnork.sim.SeaLifePrototype;

import java.util.Set;

public class GameParams {
	
	private static _GameParams _instance = null; 
	
	public synchronized static void init(Set<SeaLifePrototype> seaLifePossibilites, int penalty,
				int d, int r, int n){
		// BUG FIX: I'm not sure what purpose this if-stmt served, but a side effect is that
		// the game params are not updated with each new game.
		// Chris
		//if(_instance == null){
			_instance = new _GameParams(seaLifePossibilites, penalty, d, r, n);
		//}
	}
	
	
	protected static class _GameParams{
	
		final Set<SeaLifePrototype> seaLifePossibilites;
		final int penalty;
		final int numDivers;
		final int gridSize; //Actual size is 2*gridSize, from -d to +d
		final int visibilityRadius;
		final int overallHVT; // The maximum value creature on the board
		final int staticHVT;
		final int dynamicHVT;
		final double maxHappiness;
		
		_GameParams(Set<SeaLifePrototype> seaLifePossibilites, int penalty,
				int d, int r, int n){
			this.seaLifePossibilites = seaLifePossibilites;
			this.penalty = penalty;
			this.numDivers = n;
			this.gridSize = d;
			this.visibilityRadius = r;
			
			int maxd = -1;
			int maxs = -1;
			double maxH=0;
			
			for(SeaLifePrototype seaLifePrototype : seaLifePossibilites){
				if(seaLifePrototype.getSpeed()==0 && seaLifePrototype.getHappiness() > maxs)
					maxs = seaLifePrototype.getHappiness();
				if(seaLifePrototype.getSpeed()!=0 && seaLifePrototype.getHappiness() > maxd)
					maxd = seaLifePrototype.getHappiness();
				int minCount = seaLifePrototype.getMinCount();
				switch(minCount){
				case 0:	break;
				case 1: {
					maxH += seaLifePrototype.getHappinessD();
					break;
				}
				case 2: {
					maxH += seaLifePrototype.getHappinessD()*1.5;
					break;
				}
				default: {
					maxH += seaLifePrototype.getHappinessD()*1.75;
				}
				}
				
			}
			
			staticHVT = maxs < 0 ? 0 : maxs;
			dynamicHVT = maxd < 0 ? 0 : maxd;
			overallHVT = staticHVT > dynamicHVT ? staticHVT : dynamicHVT;
			maxHappiness = maxH;
			System.out.println("Max H: "+maxHappiness);
		}

		public Set<SeaLifePrototype> getSeaLifePossibilites() {
			return seaLifePossibilites;
		}

		public int getPenalty() {
			return penalty;
		}

		public int getNumDivers() {
			return numDivers;
		}

		public int getGridSize() {
			return gridSize;
		}

		public int getVisibilityRadius() {
			return visibilityRadius;
		}

		public int getOverallHVT() {
			return overallHVT;
		}

		public int getStaticHVT() {
			return staticHVT;
		}

		public int getDynamicHVT() {
			return dynamicHVT;
		}
		
		public double getMaxHappiness(){
			return maxHappiness;
		}
		
		public int getNumberOfHappy() {
			int total = 0;
			for (SeaLifePrototype slp : seaLifePossibilites) {
				if (slp.isDangerous()) continue;
				int count = (((slp.getMaxCount() + slp.getMinCount()) % 2 == 0) ? (slp.getMaxCount() + slp.getMinCount())/2 : (slp.getMaxCount() + slp.getMinCount())/2 + 1 );
				total+=count;
			}
			
			return total;
		}
		
		public double getAverageHappiness() {
			int total = 0;
			double totalHappiness = 0.0;
			for (SeaLifePrototype slp : seaLifePossibilites) {
				if (slp.isDangerous()) continue;
				int count = (((slp.getMaxCount() + slp.getMinCount()) % 2 == 0) ? (slp.getMaxCount() + slp.getMinCount())/2 : (slp.getMaxCount() + slp.getMinCount())/2 + 1 );
				total+=count;
				totalHappiness += count * slp.getHappiness();
			}
			
			if (total==0) return 0.0;
			
			return totalHappiness / total;
		}
		
	}
	
	public static Set<SeaLifePrototype> getSeaLifePossibilites() {
		return _instance.getSeaLifePossibilites();
	}
	
	public static SeaLifePrototype getSeaLife(String name) {
		for (SeaLifePrototype slp : _instance.getSeaLifePossibilites()) {
			if (slp.getName().equals(name)) return slp;
		}
		
		return null;
	}

	public static int getPenalty() {
		return _instance.getPenalty();
	}

	public static int getNumDivers() {
		return _instance.getNumDivers();
	}

	public static int getGridSize() {
		return _instance.getGridSize();
	}

	public static int getVisibilityRadius() {
		return _instance.getVisibilityRadius();
	}

	public static int getOverallHVT() {
		return _instance.getOverallHVT();
	}

	public static int getStaticHVT() {
		return _instance.getStaticHVT();
	}

	public static int getDynamicHVT() {
		return _instance.getDynamicHVT();
	}
	
	public static double getAverageHappiness() {
		return _instance.getAverageHappiness();
	}
	
	public static int getNumberOfHappy() {
		return _instance.getNumberOfHappy();
	}
	
	public static double getMaxHappinessPossible(){
		return _instance.getMaxHappiness();
	}

}
