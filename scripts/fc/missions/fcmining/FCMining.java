package scripts.fc.missions.fcmining;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Login;
import org.tribot.api2007.Login.STATE;
import org.tribot.api2007.Skills;
import org.tribot.api2007.Skills.SKILLS;

import scripts.fc.api.inventory.FCInventoryListener;
import scripts.fc.api.skills.GatheringMode;
import scripts.fc.api.skills.ProgressionType;
import scripts.fc.api.skills.mining.data.RockType;
import scripts.fc.api.skills.mining.data.locations.MiningLocHandler;
import scripts.fc.api.skills.mining.data.locations.MiningLocation;
import scripts.fc.api.utils.PriceUtils;
import scripts.fc.framework.WorldHopSettings;
import scripts.fc.framework.goal.Goal;
import scripts.fc.framework.goal.GoalList;
import scripts.fc.framework.mission.GoalMission;
import scripts.fc.framework.mission.MissionManager;
import scripts.fc.framework.paint.FCPaint;
import scripts.fc.framework.script.FCMissionScript;
import scripts.fc.framework.statistic_tracking.StatTracking;
import scripts.fc.framework.task.Task;
import scripts.fc.missions.fcmining.tasks.WorldHop;
import scripts.fc.missions.fcmining.tasks.autoselect.AutoSelectLocation;
import scripts.fc.missions.fcmining.tasks.autoselect.AutoSelectRock;
import scripts.fc.missions.fcmining.tasks.mining.BankOre;
import scripts.fc.missions.fcmining.tasks.mining.DropInventory;
import scripts.fc.missions.fcmining.tasks.mining.GoToLocation;
import scripts.fc.missions.fcmining.tasks.mining.M1D1;
import scripts.fc.missions.fcmining.tasks.mining.Mine;
import scripts.fc.missions.fcmining.tasks.pickaxe.EquipPick;
import scripts.fc.missions.fcmining.tasks.pickaxe.GetBronzePick;
import scripts.fc.missions.fcmining.tasks.pickaxe.GetPick;
import scripts.fc.missions.fcmining.tasks.pickaxe.UpgradePick;
import scripts.fc.missions.fcmining.tools.RockFinder;

public class FCMining extends MissionManager implements GoalMission, Serializable, FCInventoryListener, StatTracking
{
	private static final long serialVersionUID = -5436266372045559180L;

	public final RockFinder ROCK_FINDER = new RockFinder(this);
	private final long ORE_STOLEN_UPDATE = 1000;
	
	//What's encapsulation anyway????
	public RockType rock;
	public GatheringMode mode;
	public MiningLocation location;
	public MiningLocHandler locHandler;
	public ProgressionType progressionType;
	public WorldHopSettings hopSettings;
	
	//config options
	public boolean isRunning = true;
	public boolean isCurrentlyUpgrading;
	public boolean isUpgradingPick;
	public boolean isUsingAbc;
	public boolean hasNoPick;
	public boolean autoSelectingLocation;
	public boolean autoSelectingRock;
	
	private GoalList serializableGoals;
	
	//shared data between certain tasks
	public boolean needsAbcDelay;
	
	//tasks
	public Mine miningTask = new Mine(this);
	
	//paint vars
	public int oreMined;
	public int profit;
	private int orePrice;
	public int startLevel = -1;
	public int startXp = -1;
	private int oreStolen;
	private long lastStolenUpdateTime;
	

	public FCMining(FCMissionScript script, boolean isUsingAbc, boolean isUpgradingPick, WorldHopSettings hopSettings, RockType rock, GatheringMode mode, 
			MiningLocation location, ProgressionType progressionType, Goal... goals)
	{
		super(script);
		this.isUsingAbc = isUsingAbc;
		this.isUpgradingPick = isUpgradingPick;
		this.hopSettings = hopSettings;
		this.rock = rock;
		this.mode = mode;
		this.location = location;
		this.progressionType = progressionType;
		this.goals = new GoalList(goals);
		this.serializableGoals = new GoalList(goals);
		this.autoSelectingLocation = location == null;
		this.autoSelectingRock = rock == null;
		this.locHandler = new MiningLocHandler();
		this.tasks = getTaskList();
		getOrePrice();
		this.missionScript.INV_OBSERVER.addListener(this);
	}
	
	public FCMining()
	{
		super();
	}
	
	@Override
	public boolean hasReachedEndingCondition()
	{
		return !isRunning || goals.hasReachedGoals();
	}

	@Override
	public String getMissionName()
	{
		return "FC Mining";
	}

	@Override
	public String getCurrentTaskName()
	{
		return currentTask == null ? "null" : currentTask.getStatus();
	}

	@Override
	public String getEndingMessage()
	{
		return "FC Mining with goals " + this.goals + " ended";
	}

	@Override
	public void execute()
	{
		if(Login.getLoginState() != STATE.INGAME)
			General.sleep(100);
		else
		{
			setStartingStats();
			executeTasks();
		}
	}
	
	private void setStartingStats()
	{
		if(startLevel == -1)
		{
			startLevel = Skills.getActualLevel(SKILLS.MINING);
			startXp = Skills.getXP(SKILLS.MINING);
		}
	}

	@Override
	public LinkedList<Task> getTaskList()
	{
		LinkedList<Task> tasks = new LinkedList<>(Arrays.asList(new GetPick(this), new UpgradePick(this), new GetBronzePick(this),
				new EquipPick(), new GoToLocation(this), miningTask));
		
		if(hopSettings != null)
			tasks.addFirst(new WorldHop(this));
		
		if(autoSelectingLocation) 
			tasks.addFirst(new AutoSelectLocation(this));
		
		if(autoSelectingRock)
			tasks.addFirst(new AutoSelectRock(this));
		
		if(mode == GatheringMode.BANK)
			tasks.add(new BankOre(this));
		else if(mode == GatheringMode.DROP_INVENTORY)
			tasks.add(new DropInventory());
		else
			tasks.add(new M1D1(this));
		
		return tasks;
	}

	@Override
	public GoalList getGoals()
	{
		if(goals == null)
			goals = serializableGoals;
		
		return goals;
	}

	@Override
	public String[] getMissionSpecificPaint()
	{
		int currentLvl = Skills.getActualLevel(SKILLS.MINING);
		int xpGain = Skills.getXP(SKILLS.MINING) - startXp;
		int oreStolen = calculateOreStolen();
		FCPaint paint = fcScript.paint;
		
		return new String[]
		{
				"Mining level: " + currentLvl + " (" + (currentLvl - startLevel) + ")",
				"Exp gained: " + PriceUtils.getCondensedNumber(xpGain) + " (" + PriceUtils.getCondensedNumber(paint.getPerHour(xpGain)) + ")",
				"Ore mined: " + oreMined + " (" + paint.getPerHour(oreMined) + ")",
				"Ore stolen: " + oreStolen + " (" + paint.getPerHour(oreStolen) + ")",
				"Profit: " + PriceUtils.getCondensedNumber(profit) + " (" + PriceUtils.getCondensedNumber(paint.getPerHour(profit)) + ")"
		};
	}
	
	public int calculateOreStolen()
	{
		int newOreStolen = (int)miningTask.getTotalMiningCount() - oreMined;
		
		if(newOreStolen < 0)
			newOreStolen = 0;
		
		if(Timing.timeFromMark(lastStolenUpdateTime) < ORE_STOLEN_UPDATE)
			return oreStolen;
		
		oreStolen = newOreStolen;
		lastStolenUpdateTime = Timing.currentTimeMillis();
		
		return oreStolen;
	}

	@Override
	public void inventoryAdded(int id, int count)
	{
		if(rock != null && id == rock.getItemId() && count == 1)
		{
			oreMined++;
			if(mode == GatheringMode.BANK)
				profit += orePrice;
		}
	}

	@Override
	public void inventoryRemoved(int id, int count)
	{}
	
	private void getOrePrice()
	{
		new Thread(new Runnable()
		{
			public void run()
			{
				try
				{
					while(FCMining.this.rock == null)
						Thread.sleep(100);
					
					FCMining.this.orePrice = PriceUtils.getPrice(FCMining.this.rock.getItemId());
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	public void resetStatistics()
	{
	}

	@Override
	public String getStatsArgs()
	{
		return "username="+General.getTRiBotUsername()+","
				+ "tableName=fc_mining,"
				+ "runtime="+(fcScript.getRunningTime()/1000)+","
				+ "ore_mined="+oreMined+","
				+ "ore_stolen="+oreStolen+","
				+ "xp_gained="+(startXp == -1 ? 0 : (Skills.getXP(SKILLS.MINING) - startXp))+","
				+ "profit="+profit;
	}

}
