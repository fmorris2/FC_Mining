package scripts.fc.missions.fcmining.tasks.autoselect;

import org.tribot.api.General;

import scripts.fc.api.skills.mining.data.locations.MiningLocation;
import scripts.fc.framework.task.Task;
import scripts.fc.missions.fcmining.FCMining;

public class AutoSelectLocation extends Task
{
	private static final long serialVersionUID = -6054924929800755721L;
	
	private FCMining script;
	private MiningLocation appropriate;
	
	public AutoSelectLocation(FCMining script)
	{
		this.script = script;
	}
	
	@Override
	public boolean execute()
	{
		script.location = appropriate;
		General.println(appropriate.getName() + " has been chosen as the appropriate mining location.");
		
		return false;
	}

	@Override
	public boolean shouldExecute()
	{
		appropriate = script.locHandler.getAppropriateLocations(script.rock).get(0);
		return script.autoSelectingLocation && script.location != appropriate;
	}

	@Override
	public String getStatus()
	{
		return "Auto-select location";
	}

}
