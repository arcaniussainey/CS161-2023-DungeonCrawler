package game;

public class Item extends ItemBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ItemBase.itemAction itemEffect;
	Item(String name, String desc, Double val, Boolean doesstuff){
		super( name,  desc,  val,  doesstuff);
	}
	
	
	Item(String name, String desc, Double val, Boolean doesstuff, ItemBase.itemAction effect){
		super( name,  desc,  val,  doesstuff);
		this.itemEffect = effect;
	}
	
	public void useItem(Entity affected) {
		if (this.quantity>0) {
			this.itemEffect.itemEffect(affected);
			this.quantity -= 1;
		}
	}
}
