package game;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Entity extends Actor implements Serializable {
	// These are NPCs, Player, and Enemies
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int max_health; 
	public int health;
	public int regen_amount;
	
	public int attack = 1; // could very easily add a map of stats, but its not in the wishlist sooo? 
	
	Entity(String name_in, Coordinate pos, int max_health_in, int health_in, int regen_amount_in){
		this.name = name_in;
		this.Position = pos;
		this.max_health = max_health_in;
		this.health = health_in;
		this.regen_amount = regen_amount_in;
		// basic initialization
		
		
		// makes entity regen every frame
		frameActs = new ArrayList<Act>();
		frameActs.add(new Act() {
			@Override
			public void perform() {
				regen(); // intend being to call this list every time we update game. 
			}
		});
	}
	
	Entity(){
		throw new java.lang.UnsupportedOperationException("Entities need a name, max_health, health, and regeneration amount.");
		// ensure an empty entity isn't created. 
	}
	
	
	
	// Entity standard behavior stuff
	@Override
	public Decision movedOn(Entity actor_in) {
		// defend themselves when moved on lol. 
		actor_in.damage(attack);
		this.damage(actor_in.attack);
		if (this.health>0) {
			return new Reject(" moved towards you!");
		} else {
			return new Accept(this.name + " Was killed");
			// accept and die 
		}
	}
	
	public void addFrameAct(Act to_perform) {
		frameActs.add(to_perform);
		// this will add to the list of actions to perform every frame;
	}
	
	public void removeFrameAct(Act to_remove) {
		frameActs.remove(to_remove);
		// this will add to the list of actions to perform every frame;
	}
	
	public void clearFrameAct() {
		frameActs.clear();
		// this will empty the list of actions to perform per frame
	}
	
	
	
	// Entity lifetime stuff 
	
	public void die() {
		DungeonCrawlerController.game_map.announceDead(this.Position);
		// have the tilemap remove them from the board. 
	}
	
	
	public void heal(int heal_amnt) {
		this.health =  (this.health+heal_amnt>this.max_health) ? this.max_health : this.health+heal_amnt;
		// ternery operator to cap health with max health. 
	}
	
	public void regen() {
		this.health =  (this.health+regen_amount>this.max_health) ? this.max_health : this.health+regen_amount;
	}
	
	public void damage(int damage_to_take) {
		this.health -= damage_to_take;
		
		if (this.health <= 0) {
			this.die(); // u dead bro 
		}
	}
	
	
	
}
