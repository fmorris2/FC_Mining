package scripts.fc.missions.fcmining.data.bronzepick;

import java.io.Serializable;

import org.tribot.api.Timing;
import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.Player;

import scripts.fc.api.generic.FCConditions;
import scripts.fc.api.interaction.impl.grounditems.PickUpGroundItem;

public abstract class BronzePickLocation implements Comparable<BronzePickLocation>, Serializable
{
	protected static final long serialVersionUID = -1908636631513114234L;
	
	protected Positionable position;
	
	public BronzePickLocation()
	{
		this.position = getSpawnTile();
	}
	
	public abstract Positionable getSpawnTile();
	public abstract boolean goTo();
	public abstract int distanceThreshold();
	public abstract String getName();
	
	public boolean collectPick()
	{
		return new PickUpGroundItem("Bronze pickaxe").execute() 
				&& Timing.waitCondition(FCConditions.inventoryContains("Bronze pickaxe"), 7500);
	}
	
	public int compareTo(BronzePickLocation o)
	{
		return Player.getPosition().distanceTo(position) - Player.getPosition().distanceTo(o.getPosition());
	}
	
	public Positionable getPosition()
	{
		return position;
	}
}
