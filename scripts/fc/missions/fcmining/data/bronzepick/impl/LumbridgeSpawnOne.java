package scripts.fc.missions.fcmining.data.bronzepick.impl;

import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.types.RSTile;

import scripts.fc.missions.fcmining.data.bronzepick.LumbridgePickLocation;

public class LumbridgeSpawnOne extends LumbridgePickLocation
{
	private static final long serialVersionUID = -1640904156998723816L;

	@Override
	public Positionable getSpawnTile()
	{
		return new RSTile(3229, 3223, 2);
	}
	
	@Override
	public int distanceThreshold()
	{
		return 2;
	}

	@Override
	public Positionable getLadderTile()
	{
		return new RSTile(3229, 3223, 0);
	}

	@Override
	public String getName()
	{
		return "Lumbridge spawn one";
	}

}
