package game;

public class Accept extends Decision {
	public String message;
	public Actor decided_actor; // this may be unneeded, it's added in case I decide to use it for the tilemap
	
	Accept(String msg){
		this.message = msg;
		this.try_again = false; // If a move is accepted there's no reason to go again. 
	}
	
	public void acceptActor(Actor act_in) {
		this.decided_actor = act_in;
	}
}
