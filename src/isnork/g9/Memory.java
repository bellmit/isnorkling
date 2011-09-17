package isnork.g9;

import java.util.List;

public class Memory {
	
	//I have no idea what the interface will be for content,
	//  using Object for now, which is bad
	public void insertMemory(Type t, Object content) {
		//stub
	}
	
	//Get a list of memory objects of a particular type
	public List<Object> getMemoryOfType(Type t) {
		//stub
		return null;
	}
	
	public enum Type{
		MY_LOCATION, OTHERS_LOCATION, MY_SIGHTING, COMM
	}
}
