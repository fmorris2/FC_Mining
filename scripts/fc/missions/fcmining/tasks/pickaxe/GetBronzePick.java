package scripts.fc.missions.fcmining.tasks.pickaxe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import scripts.fc.api.skills.mining.MiningUtils;
import scripts.fc.framework.task.Task;
import scripts.fc.missions.fcmining.FCMining;
import scripts.fc.missions.fcmining.data.bronzepick.BronzePickLocation;
import scripts.fc.missions.fcmining.data.bronzepick.impl.BarbarianVillageSpawn;
import scripts.fc.missions.fcmining.data.bronzepick.impl.LumbridgeSpawnOne;
import scripts.fc.missions.fcmining.data.bronzepick.impl.LumbridgeSpawnTwo;
import scripts.fc.missions.fcmining.data.bronzepick.impl.RimmingtonSpawn;

public class GetBronzePick extends Task
{
	private static final long serialVersionUID = 4652673045058944834L;

	private final List<BronzePickLocation> LOCATIONS = new ArrayList<>(Arrays.asList(
			new BarbarianVillageSpawn(), new LumbridgeSpawnOne(),
			new LumbridgeSpawnTwo(), new RimmingtonSpawn()));
	
	protected FCMining script;
	
	public GetBronzePick(FCMining script)
	{
		this.script = script;
	}
	
	@Override
	public boolean execute()
	{
		Collections.sort(LOCATIONS);
		BronzePickLocation closest = LOCATIONS.get(0);
		
		if(closest.goTo())
			closest.collectPick();
		
		return false;
	}

	@Override
	public boolean shouldExecute()
	{
		return script.hasNoPick && MiningUtils.getBestUsablePick(false) == null;
	}

	@Override
	public String getStatus()
	{
		return "Get bronze pick - " + LOCATIONS.get(0).getName();
	}

}
