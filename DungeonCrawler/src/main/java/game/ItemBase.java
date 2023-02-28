package game;

import java.io.Serializable;

public class ItemBase implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String ItemName;
	protected String ItemDescription;
	protected Double ItemValue; 
	protected int quantity = 1;
	
	ItemBase(String name, String desc, Double val, Boolean doesstuff){
		this.ItemName = name;
		this.ItemDescription = desc;
		this.ItemValue = val; 
		this.has_effect = doesstuff;
	}
	
	public Boolean has_effect;
	
	@FunctionalInterface
	public interface itemAction {
		public void itemEffect(Entity entity_in);
	}
	
	public String getLongName() {
		return String.format("%s %s, worth $%.2f", this.ItemName, this.ItemDescription, this.ItemValue);
	}
	
	public String getName() {
		return this.ItemName;
	}
	
	public String getDesc() {
		return this.ItemDescription;
	}
	
	public Double getVal() {
		return this.ItemValue;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o.getClass() == this.getClass()) {
			if (((Item) o).getLongName() == this.getLongName()) {
				return true;
			}
		}
		return false;
	}
}
