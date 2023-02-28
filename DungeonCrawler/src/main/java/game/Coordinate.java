package game;

import java.io.Serializable;

public class Coordinate implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3852177980206932549L;
	int x;
	int y;
	
	int getX() { return this.x;}
	int getY() { return this.y;}
	
	Coordinate(int x_in, int y_in){
		this.x = x_in;
		this.y = y_in;
	}
	
	int[] getPos() {
		return new int[] {this.x, this.y};
	}
	
	Coordinate Forward() {
		return new Coordinate(this.x, this.y - 1);
	}
	
	Coordinate Backward() {
		return new Coordinate(this.x, this.y + 1);
	}
	
	Coordinate Left() {
		return new Coordinate(this.x - 1, this.y);
	}
	
	Coordinate Right() {
		return new Coordinate(this.x + 1, this.y);
	}
	
	@Override 
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		else if (o.getClass() != this.getClass()) {
			return false;
		}
		if (o.getClass() == this.getClass()) {
			if (((Coordinate) o).getX() == this.getX() && ((Coordinate) o).getY() == this.getY()) {
				return true;
			}
		}
		return false;
	}
	
	@Override 
	public int hashCode() {
		int first_half = this.x * 17 + 12;
		int second_half = this.y * 20 + 5;
		return ((first_half + second_half) + (first_half+second_half)%16 + this.x - this.y);
	}
}
