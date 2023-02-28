package game;

public class Reject extends Decision {
	// This is an abstract parent class. 
	// This class exists such that we can check if children derive from it, instead of checking both accept and reject. 
	// It also means we can make a function return a Decision rather than specifically a reject or accept
	public String message;
	public Actor decided_actor; // this may be unneeded, it's added in case I decide to use it for the tilemap
	
	Reject(String msg){
		this.message = msg;
	}
	
	public void acceptActor(Actor act_in) {
		this.decided_actor = act_in;
	}
}
