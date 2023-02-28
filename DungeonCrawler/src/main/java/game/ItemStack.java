package game;

import java.util.List;

public class ItemStack extends Item{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Item> internal_items; 
	private Item model;
	
	ItemStack(Item model_item, Item[] items){
		super(model_item.getName() + " Stack", model_item.getDesc(), model_item.getVal(), model_item.has_effect);
		model = model_item;
		for (Item thing: items) {
			if (model_item.equals(thing))
			internal_items.add(thing);
		}
		this.quantity = internal_items.size();
	}
	
	public void addItem(Item item_in) {
		if (model.equals(item_in)) {
			internal_items.add(item_in);
			this.quantity += 1;
		}
	}
	
	public void useItem(Entity affected) {
		if (this.quantity>0) {
			this.model.itemEffect.itemEffect(affected);
			this.quantity -= 1;
		}
	}
}
