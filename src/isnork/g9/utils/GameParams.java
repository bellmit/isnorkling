package isnork.g9.utils;

import isnork.sim.SeaLifePrototype;

import java.util.Set;

public class GameParams {
	
	private static _GameParams _instance = null; 
	
	public synchronized static void init(Set<SeaLifePrototype> seaLifePossibilites, int penalty,
				int d, int r, int n){
		if(_instance == null){
			_instance = new _GameParams(seaLifePossibilites, penalty, d, r, n);
		}
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
		
		_GameParams(Set<SeaLifePrototype> seaLifePossibilites, int penalty,
				int d, int r, int n){
			this.seaLifePossibilites = seaLifePossibilites;
			this.penalty = penalty;
			this.numDivers = n;
			this.gridSize = d;
			this.visibilityRadius = r;
			
			int maxd = -1;
			int maxs = -1;
			
			for(SeaLifePrototype seaLifePrototype : seaLifePossibilites){
				if(seaLifePrototype.getSpeed()==0 && seaLifePrototype.getHappiness() > maxs)
					maxs = seaLifePrototype.getHappiness();
				if(seaLifePrototype.getSpeed()!=0 && seaLifePrototype.getHappiness() > maxd)
					maxd = seaLifePrototype.getHappiness();
			}
			
			staticHVT = maxs;
			dynamicHVT = maxd;
			overallHVT = staticHVT > dynamicHVT ? staticHVT : dynamicHVT;
	
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
		
		
	}
	
	public static Set<SeaLifePrototype> getSeaLifePossibilites() {
		return _instance.getSeaLifePossibilites();
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
	
	

}
