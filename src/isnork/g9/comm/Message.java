package isnork.g9.comm;

public interface Message {
	
	public static final int TTL = 12;
	
	public void age();
	public boolean die();

}
