package scripts.fc.missions.fcmining.tasks.autoselect;

import org.tribot.api.General;

import scripts.fc.api.skills.mining.data.RockType;
import scripts.fc.api.skills.mining.data.RockTypeUtils;
import scripts.fc.framework.task.Task;
import scripts.fc.missions.fcmining.FCMining;

public class AutoSelectRock extends Task
{
	private static final long serialVersionUID = 8094638218543588498L;
	
	private FCMining script;
	private RockType appropriate;
	
	public AutoSelectRock(FCMining script)
	{
		this.script = script;
	}
	
	@Override
	public boolean execute()
	{
		script.rock = RockType.valueOf(appropriate.name());
		General.println(appropriate + " has been chosen as the appropriate rock type");
		
		return false;
	}

	@Override
	public boolean shouldExecute()
	{		
		appropriate = RockTypeUtils.getAppropriate(script.progressionType);
		
		return script.autoSelectingRock && (script.rock == null || script.rock.getLevelReq() != appropriate.getLevelReq());
	}

	@Override
	public String getStatus()
	{
		return "Auto select rock";
	}

}
