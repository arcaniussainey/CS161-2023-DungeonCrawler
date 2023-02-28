package game;


import java.io.Serializable;
import java.util.List;

import javafx.scene.image.Image;

public abstract class Actor implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String img_url;
	// The actor class represents actor entities on the map. 
	// This base class exists to standardize things that all actors will have. 
	@SuppressWarnings("exports")
	public transient Image sprite = null;
	public interface Act{
		public void perform();
	}
	
	// abstract methods need implemented in children classes. 
	public String name;
	public Coordinate Position;
	public List<Act> frameActs;
	
	public abstract Decision movedOn(Entity actor_in); // returns type Decision -> {Accept, Reject}
									// All actors can have a move attempted against them, but only entities move
}
