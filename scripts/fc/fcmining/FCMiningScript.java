package scripts.fc.fcmining;

import java.awt.Color;
import java.util.LinkedList;
import java.util.Queue;

import org.tribot.api.Timing;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Ending;
import org.tribot.script.interfaces.EventBlockingOverride;
import org.tribot.script.interfaces.Painting;
import org.tribot.script.interfaces.Starting;

import scripts.fc.fcmining.gui.FCMiningGUI;
import scripts.fc.fcmining.paint.FCMiningPaint;
import scripts.fc.framework.mission.GoalMission;
import scripts.fc.framework.mission.Mission;
import scripts.fc.framework.paint.FCDetailedPaint;
import scripts.fc.framework.paint.FCPaintable;
import scripts.fc.framework.script.FCPremiumScript;

@ScriptManifest(
		authors     = { 
		    "Final Calibur",
		}, 
		category    = "Mining", 
		name        = "FC Mining", 
		version     = 0.1, 
		description = "High quality AIO miner", 
		gameMode    = 1)

public class FCMiningScript extends FCPremiumScript implements FCPaintable, Painting, Starting, Ending, EventBlockingOverride
{
	private final FCMiningGUI GUI = (FCMiningGUI)this.paint.gui;
	
	public long currentMissionStart = Timing.currentTimeMillis();
	
	@Override	
	protected int mainLogic()
	{
		if(!GUI.hasFilledOut)
			return 100;
		
		if(currentMission != null && currentMission.hasReachedEndingCondition())
			currentMissionStart = Timing.currentTimeMillis();
			
		return super.mainLogic();
	}
	
	@Override
	protected Queue<Mission> getMissions()
	{
		return new LinkedList<>();
	}

	@Override
	protected String[] scriptSpecificPaint()
	{
		return new String[]{
				"Time ran: " + paint.getTimeRan(), 
				"Current mission time: " + Timing.msToString((Timing.timeFromMark(currentMissionStart))), 
				"Incomplete goals: " + ((GoalMission)(currentMission)).getGoals().size(), 
				"Missions in queue: " + missions.size(), 
				"Next mission: " + (missions.isEmpty() ? "none" : missions.peek().getMissionName() + " with " + missions.size() + " goals")
		};
	}

	@Override
	public FCDetailedPaint getPaint()
	{
		return new FCMiningPaint(this, new FCMiningGUI(this), Color.WHITE, new SKILLS[]{SKILLS.MINING});
	}
}
