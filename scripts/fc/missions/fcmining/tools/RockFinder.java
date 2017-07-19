package scripts.fc.missions.fcmining.tools;

import java.io.Serializable;

import org.tribot.api.interfaces.Positionable;
import org.tribot.api.util.Sorting;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSObject;

import scripts.fc.api.abc.PersistantABCUtil;
import scripts.fc.api.skills.mining.MiningUtils;
import scripts.fc.framework.data.Vars;
import scripts.fc.missions.fcmining.FCMining;

public class RockFinder implements Serializable
{	
	private static final long serialVersionUID = 8114620990103275716L;
	
	private FCMining script;
	private transient RSObject[] rocks = new RSObject[0];
	
	public RockFinder(FCMining script)
	{
		this.script = script;
	}
	
	private void updateRocks()
	{
		if(script.location != null)
		{
			final Positionable TOP_LEFT = script.location.getCenterTile().getPosition().getPosition().translate(-script.location.getRadius(), script.location.getRadius());
			final Positionable BOTTOM_RIGHT = script.location.getCenterTile().getPosition().getPosition().translate(script.location.getRadius(), -script.location.getRadius());
			
			rocks = Objects.getAllIn(TOP_LEFT, BOTTOM_RIGHT, MiningUtils.rockFilter(script.rock, rocks));
			Sorting.sortByDistance(rocks, Player.getPosition(), true);
		}
	}
	
	public RSObject getCurrentRock(boolean abc)
	{
		PersistantABCUtil abc2 = Vars.get().get("abc2");
		updateRocks();
		
		if(rocks.length == 0)
			return null;
		
		else if(abc)
			return (RSObject)abc2.selectNextTarget((Positionable[])rocks);
		
		return rocks[0];
	}
	
	public RSObject[] getRocks()
	{
		updateRocks();
		return rocks;
	}
	
	public void resetRocks()
	{
		rocks = new RSObject[0];
	}
}
