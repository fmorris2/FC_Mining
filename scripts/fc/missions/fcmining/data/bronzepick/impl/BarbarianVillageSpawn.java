package scripts.fc.missions.fcmining.data.bronzepick.impl;

import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.Player;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSTile;

import scripts.fc.missions.fcmining.data.bronzepick.BronzePickLocation;

public class BarbarianVillageSpawn extends BronzePickLocation
{
	private static final long serialVersionUID = -599075592605992384L;

	@Override
	public Positionable getSpawnTile()
	{
		return new RSTile(3083, 3429, 0);
	}

	@Override
	public boolean goTo()
	{
		return Player.getPosition().distanceTo(position) < distanceThreshold() || WebWalking.walkTo(position);
	}

	@Override
	public int distanceThreshold()
	{
		return 2;
	}

	@Override
	public String getName()
	{
		return "Barbarian village";
	}

}
