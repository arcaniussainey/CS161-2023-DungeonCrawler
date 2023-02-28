package game;

import java.io.Serializable;

public class SaveType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8742535424174024652L;
	Player player;
	int seconds_played;
	
	SaveType(Player play_in, int seconds){
		this.player = play_in;
		this.seconds_played = seconds;
	}
}
