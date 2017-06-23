package scripts.fc.missions.fcmining.tasks.mining;

import org.tribot.api.Timing;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;

import scripts.fc.api.generic.FCConditions;
import scripts.fc.api.skills.mining.MiningUtils;
import scripts.fc.framework.task.Task;
import scripts.fc.missions.fcmining.FCMining;

public class GoToLocation extends Task
{
	private static final long serialVersionUID = -5438733888687247434L;
	
	private FCMining script;
	
	public GoToLocation(FCMining script)
	{
		this.script = script;
	}
	
	@Override
	public boolean execute()
	{	
		script.needsAbcDelay = false;
		
		//reset rock finder rocks
		script.ROCK_FINDER.resetRocks();
		
		if(script.location.goTo())
			Timing.waitCondition(FCConditions.inAreaCondition(script.location.getArea()), 200);
		
		return false;
	}

	@Override
	public boolean shouldExecute()
	{
		return !script.location.getArea().contains(Player.getPosition()) && !Inventory.isFull()
					&& MiningUtils.getBestUsablePick(false) != null && !script.isCurrentlyUpgrading;
	}

	@Override
	public String getStatus()
	{
		return "Go to location";
	}

}
