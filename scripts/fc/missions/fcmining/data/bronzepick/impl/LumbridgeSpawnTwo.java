package scripts.fc.missions.fcmining.data.bronzepick.impl;

import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.types.RSTile;

import scripts.fc.missions.fcmining.data.bronzepick.LumbridgePickLocation;

public class LumbridgeSpawnTwo extends LumbridgePickLocation
{
	private static final long serialVersionUID = -5529267884469226665L;

	@Override
	public Positionable getSpawnTile()
	{
		return new RSTile(3229, 3215, 2);
	}
	
	@Override
	public int distanceThreshold()
	{
		return 2;
	}

	@Override
	public Positionable getLadderTile()
	{
		return new RSTile(3229, 3214, 0);
	}

	@Override
	public String getName()
	{
		return "Lumbridge spawn two";
	}

}
