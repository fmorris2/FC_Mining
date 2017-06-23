package scripts.fc.fcmining.paint;

import java.awt.Color;

import javax.swing.JFrame;

import org.tribot.api.Timing;
import org.tribot.api2007.Skills;
import org.tribot.api2007.Skills.SKILLS;

import scripts.fc.fcmining.FCMiningScript;
import scripts.fc.framework.paint.FCDetailedPaint;
import scripts.fc.framework.paint.FCPaintable;
import scripts.fc.missions.fcmining.FCMining;

public class FCMiningPaint extends FCDetailedPaint
{
	public FCMiningPaint(FCPaintable paintable, JFrame gui, Color color, SKILLS[] skills)
	{
		super(paintable, gui, color, skills);
	}

	@Override
	public Color getRectColor()
	{
		return new Color(101, 190, 224, 60);
	}

	@Override
	public Color getHoverColor()
	{
		return new Color(6, 134, 232);
	}

	@Override
	public String getImageUrl()
	{
		return "http://i.imgur.com/aXROsT6.png?1";
	}

	@Override
	public void resetStatistics()
	{
		startTime = Timing.currentTimeMillis();
		
		FCMiningScript script = (FCMiningScript)paintable;
		script.currentMissionStart = startTime;
		FCMining mission = (FCMining)script.getCurrentMission();
		if(mission != null)
		{
			mission.oreMined = 0;
			mission.miningTask.resetMiningStats();
			mission.profit = 0;
			mission.startLevel = Skills.getActualLevel(SKILLS.MINING);
			mission.startXp = Skills.getXP(SKILLS.MINING);
		}
	}

}
