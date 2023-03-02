package game;

import java.io.Serializable;
import java.util.HashMap;

public class TileMap implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3673076360084136762L;
	public HashMap<Coordinate, Actor> Tiles;
	public Coordinate map_position;
	protected int x;
	protected int y;
	
	TileMap(int dim_x, int dim_y){
		this.x = dim_x;
		this.y = dim_y;
		Tiles = new HashMap<Coordinate, Actor>();
		// the tilemap must know the requested dimensions. 
		for (int i = 0; i < dim_x; i++) {
			for (int j=0; j<dim_y; j++) {
				Tiles.put(new Coordinate(i, j), new NullActor(new Coordinate(i, j)));
				// instantiate the tilemap and its actual values. 
			}
		}
		this.map_position = new Coordinate(0,0);
	}
	
	public Decision requestMove(Coordinate move_to, Entity requestor){
		// requestor should always be the one running the function. 
		
		Decision canMove;
		Actor tile_holder = null; // allows a null check very clearly
		tile_holder = Tiles.get(move_to);
		if (tile_holder != null) {
			canMove = tile_holder.movedOn(requestor);
			if (canMove instanceof Accept) {
				Tiles.put(requestor.Position, new NullActor(requestor.Position));
				Tiles.put(move_to, requestor);
				requestor.Position = move_to; // make sure the actor knows their new position
				// We make the old position of the requester null
				// we make the position of move_to occupied by the requester. I misspelled requestor. FRIG.  
			}
		} else {
			canMove = new Reject("You cannot move to a tile outside the field");
			// this could implement chunks pretty easily
			// because we could have each tilemap hold the four bordering it,
			// And see which it went into, and then convert it to a coordinate 
			// within that tilemap
		}
		
		return canMove;
		
	}
	
	public void announceDead(Coordinate pos) {
		Tiles.put(pos, new NullActor(pos));
		// announce the death of entity
	}
	
	public Actor getActor(Coordinate pos) {
		if (Tiles.get(pos) != null)
			return Tiles.get(pos);
		else return null;
	}
	
	public int[] getDimensions(){
		return new int {this.x, this.y};
		// added to assist in creating SaveType
	}
	
	public void setActor(Coordinate pos, Actor actor) {
		Tiles.put(pos, actor);
		actor.Position = pos;
	}
	// The tilemap has a simple purpose: keep track of the tiles and their contents. 
	
	/*
	 * 
	 * Ideally, the tilemap handles all accept and reject states of the tile, and 
	 * 		decides whether or not some entity even CAN move. Additionally, it should
	 * 		have the responsibility of forwarding the images on the board. 
	 * 
	 * The TileMap should get an Accept or Reject from the entity that is in a tile,
	 * 		and should pass the requesting actor as one of the arguments. This actor 
	 * 		may be modified by the entity within the tile, before it decides to return
	 * 		an accept or reject. A reject results in the tilemap NOT performing a move. 
	 * 		An accept results in the tilemap performing the move, possibly erasing the 
	 * 		entity in that tile. 
	 * 
	 * 		In the case of enemies, only death should result in an accept. 
	 * 		In the case of traps, they accept and damage the requester. 
	 */
	}
