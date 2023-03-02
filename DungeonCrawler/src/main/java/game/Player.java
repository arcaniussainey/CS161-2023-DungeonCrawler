package game;

import java.io.Serializable;

import javafx.scene.image.Image;

public class Player extends Entity implements Serializable{
	public List<Item> inventory;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Player(		String name_in, 
			Coordinate pos, 
			int max_health_in, 
			int health_in, 
			int regen_amount_in, 
			int attack_strength){
		
		super(name_in, pos, max_health_in, health_in, regen_amount_in);
		/* this.name = name_in;
		this.Position = pos;
		this.max_health = max_health_in;
		this.health = health_in;
		this.regen_amount = regen_amount_in;*/
		this.img_url = "https://www.giantbomb.com/a/uploads/scale_medium/3/34651/1475064-gandalf.jpg"; // to allow reset after serialization
		this.sprite = new Image("https://www.giantbomb.com/a/uploads/scale_medium/3/34651/1475064-gandalf.jpg");
		
		/*
		// basic initialization
		
		
		// makes entity regen every frame
		frameActs = new ArrayList<Act>();
		frameActs.add(new Act() {
			@Override
			public void perform() {
				regen(); // intend being to call this list every time we update game. 
			}
		}); */
		
		// All of the above could be substituted with the function "super()", which calls the parent constructor. 
		
		this.attack = attack_strength;
	}
	
	@Override
	public void die() {
		// TODO
		
		// set the actual death screen
		DungeonCrawlerController.current_stage = StageState.DEATH;
	}

	@Override 
	public String toString() {
		return String.format("%s has %d/%d health", this.name, this.health, this.max_health);
	}
	
}
