package game;

public class Reject extends Decision {
	public String message;
	public Actor decided_actor; // this may be unneeded, it's added in case I decide to use it for the tilemap
	
	Reject(String msg){
		this.message = msg;
	}
	
	public void acceptActor(Actor act_in) {
		this.decided_actor = act_in;
	}
}
