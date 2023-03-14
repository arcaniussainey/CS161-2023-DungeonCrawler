package game;

import java.io.Serializable;

public class SaveType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8742535424174024652L;
	// player fields
	private int player_hp;
	private int player_max;
	private int player_regen;
	private int player_attack;
	private List<Item> player_inv; 
	private Coordinate player_pos;
	private String player_name;
	private String image_addr;
	private List<Act> frame_acts;
	
	// Tilemap fields
	private int[2] table_dim;
	private Set<Map.Entry<Coordinate, Actor>> tile_data; // actual tiles
	private Coordinate screen_pos;
	
	int seconds_played;
	
	SaveType(Player play_in, int seconds TileMap game_map){
		this.player_hp = play_in.health;
		this.player_max = play_in.max_health;
		this.player_regen = play_in.regen_amount_in;
		this.player_attack = play_in.attack;
		this.player_hp = play_in.health;
		this.player_inv = play_in.inventory;
		this.player_name = play_in.name;
		this.image_addr = play_in.name;
		this.frame_acts = play_in.frameActs;
		// saves all player data
		// Honestly just because I don't wanna work with making Player class genuinely serializeable. 
		
		// Tilemap SHOULD be serializeable?? But we'll see if data restoration like this is better
		this.table_dim = game_map.getDimensions(); 
		this.tile_data = game_map.entrySet(); 
		this.screen_pos = game_map.map_position;
	}
	
	public Player unpackPlayer(){
		Player ret_play = new Player(this.player_name, 
					     this.player_pos,
					     this.player_max,
					     this.player_hp,
					     this.player_regen,
					     this.player_attack); 
		ret_play.img_url = this.image_addr; 
		this.sprite = new Image(this.image_addr);
					     
		return ret_play;	     
	}
	
	public TileMap unpackTileMap(){
	// check that map is valid
		if ((this.table_dim[0]*this.table_dim[1]) == tile_data.size()){
			// ensure that the number of tiles matches the dimensions of the tilemap
			try {
			HashMap<Coordinate, Actor> ret_map = set.stream()
								.collect(Collectors.toMap(Entry::getKey, Entry::getValue));
			} catch (IllegalStateException ISE){
				ISE.printStackTrace()
			} catch (Throwable e){
				System.out.print("Unexpected Error?");
				e.printStackTrace();
			}
			
			return ret_map;// return the map
		}
		
		return null; // if none of above pass
	} 
}
