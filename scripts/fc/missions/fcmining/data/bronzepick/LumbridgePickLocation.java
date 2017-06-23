package scripts.fc.missions.fcmining.data.bronzepick;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.Player;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.api2007.util.DPathNavigator;

import scripts.fc.api.generic.FCConditions;
import scripts.fc.api.interaction.impl.objects.ClickObject;

public abstract class LumbridgePickLocation extends BronzePickLocation
{
	private static final long serialVersionUID = 7317374668007439395L;
	
	private final int LADDER_THRESHOLD = 2;
	private final RSArea LADDER_AREA = new RSArea(getLadderTile(), LADDER_THRESHOLD);
	
	private final Positionable COURTYARD_CENTER = new RSTile(3222, 3218, 0);
	private final RSArea COURTYARD_AREA = new RSArea(COURTYARD_CENTER, 15);
	
	protected Positionable ladderTile = getLadderTile();
	
	public abstract Positionable getLadderTile();
	
	public boolean collectPick()
	{
		if(super.collectPick())
		{
			while(Player.getPosition().getPlane() > 0)
			{
				final int PLANE = Player.getPosition().getPlane();
				
				if(new ClickObject("Climb-down", "Ladder", 10).execute())
					Timing.waitCondition(FCConditions.planeChanged(PLANE), 3500);
				
				General.sleep(600, 1800);
			}
			
			return Player.getPosition().getPlane() == 0;
		}
		
		return false;
	}
	
	public boolean goTo()
	{
		final int PLANE = Player.getPosition().getPlane();
		
		if(Player.getPosition().distanceTo(ladderTile) > LADDER_THRESHOLD)
		{
			goToLadder();
		}
		else if(PLANE < 2)
		{
			if(new ClickObject("Climb-up", "Ladder", LADDER_THRESHOLD).execute())
				Timing.waitCondition(FCConditions.planeChanged(PLANE), 4000);
		}
		
		return Player.getPosition().distanceTo(position) <= distanceThreshold();
	}
	
	private void goToLadder()
	{
		if(!COURTYARD_AREA.contains(Player.getPosition()))
		{
			if(WebWalking.walkTo(COURTYARD_CENTER, FCConditions.inAreaCondition(COURTYARD_AREA), 600))
					Timing.waitCondition(FCConditions.inAreaCondition(COURTYARD_AREA), 3500);
		}
		else
		{
			if(new DPathNavigator().traverse(ladderTile))
				Timing.waitCondition(FCConditions.inAreaCondition(LADDER_AREA), 4000);
		}
	}
}
