package game;

public abstract class Decision {
	// This is an abstract parent class. 
	// This class exists such that we can check if children derive from it, instead of checking both accept and reject. 
	// It also means we can make a function return a Decision rather than specifically a reject or accept
	public String message;
	public Actor decided_actor;
	public boolean try_again = true; // set this to false if a decision should be consumed
	// If a user moves into say a trap or enemy, this should set to false. But if they move into 
	// something like a wall and cant, they should go again lol. 
	
	Decision(String msg){
		this.message = msg;
	}
	
	Decision(){
		this("No message given");
	}
	
}
