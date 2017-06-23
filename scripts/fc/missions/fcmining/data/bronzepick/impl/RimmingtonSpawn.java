package scripts.fc.missions.fcmining.data.bronzepick.impl;

import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.Player;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSTile;

import scripts.fc.missions.fcmining.data.bronzepick.BronzePickLocation;

public class RimmingtonSpawn extends BronzePickLocation
{
	private static final long serialVersionUID = 62559827308575064L;

	@Override
	public Positionable getSpawnTile()
	{
		return new RSTile(2965, 3213, 0);
	}

	@Override
	public boolean goTo()
	{
		return Player.getPosition().distanceTo(position) < distanceThreshold() || WebWalking.walkTo(position);
	}

	@Override
	public int distanceThreshold()
	{
		return 3;
	}

	@Override
	public String getName()
	{
		return "Rimmington";
	}

}
