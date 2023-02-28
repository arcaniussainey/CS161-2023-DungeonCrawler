package game;

import javafx.scene.image.Image;

public class NullActor extends Actor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	NullActor(Coordinate pos){
		this.name = "Empty space";
		this.Position = pos;
		this.sprite = null;
	}
	public Decision movedOn(Entity actor_in) {
		return new Accept(actor_in.name + " moves.");
		// the empty actor always accepts. 
	}
}
