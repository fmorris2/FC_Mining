package scripts.fc.missions.fcmining.tasks;

import org.tribot.api.General;
import org.tribot.api2007.Player;
import org.tribot.api2007.Players;
import org.tribot.api2007.WorldHopper;
import org.tribot.api2007.ext.Filters;

import scripts.fc.api.utils.Utils;
import scripts.fc.api.worldhopping.FCInGameHopper;
import scripts.fc.framework.task.Task;
import scripts.fc.missions.fcmining.FCMining;

public class WorldHop extends Task
{
	private static final long serialVersionUID = 343528012996773996L;
	
	private FCMining script;
	private boolean isMember;
	private boolean playersInArea;
	private boolean stolenPerHour;
	private boolean noOreAvailable;
	
	public WorldHop(FCMining script)
	{
		this.script = script;
		this.isMember = Utils.isMember();
	}
	
	@Override
	public boolean execute()
	{
		General.println("Hopping worlds!");
		
		if(playersInArea)
			General.println("Too many players in area!");
		
		if(stolenPerHour)
			General.println("Too many ore stolen!");
		
		if(noOreAvailable)
			General.println("No ore available!");
		
		script.oreMined = 0;
		script.miningTask.resetMiningStats();
		
		FCInGameHopper.hop(WorldHopper.getRandomWorld(isMember, false));
		
		return false;
	}

	@Override
	public boolean shouldExecute()
	{
		if(!script.location.getArea().contains(Player.getPosition()))
			return false;
		
		playersInArea = script.hopSettings.playersInArea > 0 &&
										(Players.getAll(Filters.Players.inArea(script.location.getArea())).length - 1) >= script.hopSettings.playersInArea;
										
		stolenPerHour = script.hopSettings.resourceStolen > 0 && script.calculateOreStolen() >= script.hopSettings.resourceStolen;
		
		noOreAvailable = script.hopSettings.noResourceAvailable && script.ROCK_FINDER.getRocks().length == 0;
		
		return playersInArea || stolenPerHour || noOreAvailable;
	}

	@Override
	public String getStatus()
	{
		return "World hop";
	}

}
