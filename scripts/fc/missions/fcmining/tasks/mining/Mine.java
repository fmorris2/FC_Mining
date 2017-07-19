package scripts.fc.missions.fcmining.tasks.mining;

import java.util.LinkedList;
import java.util.Queue;

import org.tribot.api.General;
import org.tribot.api.Screen;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.interfaces.Positionable;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Camera;
import org.tribot.api2007.ChooseOption;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSMenuNode;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSObjectDefinition;

import scripts.fc.api.abc.PersistantABCUtil;
import scripts.fc.api.generic.FCConditions;
import scripts.fc.api.generic.FCFilters;
import scripts.fc.api.interaction.impl.objects.ClickObject;
import scripts.fc.api.skills.GatheringMode;
import scripts.fc.api.skills.mining.MiningUtils;
import scripts.fc.api.skills.mining.data.Pickaxe;
import scripts.fc.api.utils.Utils;
import scripts.fc.framework.data.Vars;
import scripts.fc.framework.task.Task;
import scripts.fc.missions.fcmining.FCMining;

public class Mine extends Task
{	
	private static final long serialVersionUID = -6041694363610156312L;

	private transient final Condition MINING_CONDITION = miningCondition();
	private final int ESTIMATED_MINING_TIME = 1000; //Only used at beginning of script
	
	private transient FCMining script;
	private transient RSObject currentRock;
	private boolean hoveredNext;
	private boolean hasCheckedMoveToAnticipated;
	private transient Queue<Positionable> rockPositions; //used for abc move to anticipated
	
	private long totalMiningTime;
	private long totalMiningCount;
	private long currentMiningActionStart = -1;
	private long currentMiningActionEnd = -1;
	
	public Mine(FCMining script)
	{
		this.script = script;
		this.rockPositions = new LinkedList<>();
	}

	@Override
	public boolean execute()
	{		
		if(!isMining(true))
		{			
			if(currentMiningActionStart != -1 && currentMiningActionEnd != -1)
			{
				totalMiningTime += currentMiningActionEnd - currentMiningActionStart;
				totalMiningCount++;
				currentMiningActionStart = -1;
				currentMiningActionEnd = -1;
				General.println("Total mining count: " + totalMiningCount + ", Total mining time: " + totalMiningTime);
			}
				
			if(mine())
			{
				currentMiningActionStart = Timing.currentTimeMillis();
				
				//successfully clicked ore, generate ABC2 tracker info
				abc2().generateTrackers(getEstimatedWait());
				resetAbc();
			}
			else
				script.needsAbcDelay = false;
		}
		else
		{
			script.needsAbcDelay = true;
			currentMiningActionEnd = Timing.currentTimeMillis();
			hasCheckedMoveToAnticipated = false;
			doActionsWhileMining();
		}
		
		return false;
	}

	@Override
	public boolean shouldExecute()
	{
		if(script.mode == GatheringMode.G1D1 && ChooseOption.isOpen()
				&& Inventory.getCount(script.rock.getItemId()) > M1D1.DROPPING_TRIGGER)
			return false;
		
		return script.location.getArea().contains(Player.getPosition()) && !Inventory.isFull() 
					&& MiningUtils.getBestUsablePick(false) != null;
	}

	@Override
	public String getStatus()
	{
		return "Mine " + script.rock.name().toLowerCase();
	}
	
	public void resetMiningStats()
	{
		totalMiningCount = 0;
		totalMiningTime = 0;
	}
	
	private PersistantABCUtil abc2()
	{
		return (PersistantABCUtil)Vars.get().get("abc2");
	}
	
	private int getEstimatedWait()
	{
		return totalMiningCount > 0 ? (int)Math.round(((double)totalMiningTime / totalMiningCount)) : ESTIMATED_MINING_TIME;
	}
	
	private void hoverNextRock()
	{
		if(Inventory.getAll().length < 27 && currentRock != null && abc2().shouldHover())
		{
			for(RSObject o : script.ROCK_FINDER.getRocks())
			{
				if(!o.getPosition().equals(currentRock.getPosition()) && !hoveredNext)
				{
					hoveredNext = o.hover();
					RSObjectDefinition def = o.getDefinition();
					
					if(hoveredNext && abc2().shouldOpenMenu() && def != null && Game.isUptext(def.getName()))
					{
						General.println("Open menu");
						Mouse.click(3);
						if(Timing.waitCondition(FCConditions.CHOOSE_OPTION_CONDITION, 1500) &&
								!ChooseOption.isOptionValid(FCFilters.optionContains("Mine")))
							Mouse.moveBox(Screen.getViewport());
					}
					
					break;
				}
			}
		}
	}

	private boolean isRockStolen()
	{
		RSObject[] rocks = Objects.getAt(currentRock);
		
		return rocks.length == 0 || !script.rock.getIds().contains(rocks[0].getID());
	}

	private boolean isMining(boolean checkIfStolen)
	{	
		return Player.getAnimation() != -1 && (!checkIfStolen || !isRockStolen());
	}
	
	private boolean mine()
	{	
		if(script.isUsingAbc && script.needsAbcDelay && script.ROCK_FINDER.getRocks().length > 0)
		{
			final int EST_WAIT = getEstimatedWait();
			final int REACTION_TIME = abc2().generateReactionTime(EST_WAIT);
			General.println("EST WAIT: " + EST_WAIT + ", REACTION TIME: " + REACTION_TIME);
			
			abc2().generateTrackers(EST_WAIT);
			abcLeaveGame();
			script.fcScript.sleep(REACTION_TIME);
			abc2().generateTrackers(EST_WAIT);
		}
		
		updateCurrentRock();
		hoveredNext = false;
		
		//if there is no next rock currently available
		if(currentRock == null)
		{
			checkMoveToAnticipated();
			return false;
		}
		
		//add the rock position to the positions cache if needed
		//this is used for abc move to anticipated
		if(!rockPositions.contains(currentRock))
			rockPositions.add(currentRock);
		
		if(ChooseOption.isOpen() && ChooseOption.isOptionValid(FCFilters.optionContains("Mine")))
			return ChooseOption.select(FCFilters.optionContains("Mine")) && Timing.waitCondition(MINING_CONDITION, 4000);
			
		return new ClickObject("Mine", currentRock).execute() && Timing.waitCondition(MINING_CONDITION, 4000);
	}
	
	private void checkMoveToAnticipated()
	{
		if(!hasCheckedMoveToAnticipated)
		{
			hasCheckedMoveToAnticipated = true;
			
			if(abc2().shouldMoveToAnticipated())
				moveToAnticipated();
		}
	}
	
	private void moveToAnticipated()
	{
		General.println("Moving to anticipated");
		
		Positionable p = rockPositions.poll();
		
		if(Player.getPosition().distanceTo(p) < 8)
		{
			if(!p.getPosition().isOnScreen())
				Camera.turnToTile(p);
			
			Walking.walkScreenPath(Walking.generateStraightScreenPath(p));
		}
		else if(Player.getPosition().distanceTo(p) < 13)
			Walking.blindWalkTo(p);
		
		rockPositions.add(p);
	}
	
	private void updateCurrentRock()
	{
		currentRock = script.ROCK_FINDER.getCurrentRock(script.isUsingAbc ? true : false);			
	}
	
	private void resetAbc()
	{
		abc2().resetShouldHover();	
		abc2().resetShouldOpenMenu();
	}
	
	private void abcLeaveGame()
	{
		if(abc2().shouldLeaveGame())
			abc2().leaveGame();
	}
	
	private void doActionsWhileMining()
	{
		if(script.isUsingAbc && script.mode != GatheringMode.G1D1)
		{
			abcLeaveGame();
			
			abc2().performTimedActions();
			
			hoverNextRock();
		}
		else if(script.mode == GatheringMode.G1D1)
			prepareM1D1();
	}
	
	private void prepareM1D1()
	{
		if(Inventory.getCount(script.rock.getItemId()) >= M1D1.DROPPING_TRIGGER && !ChooseOption.isOpen())
			prepareOreDrop();
	}
	
	private void prepareOreDrop()
	{
		RSItem[] ore = Inventory.find(Filters.Items.idNotEquals(Pickaxe.getPickIds()));
		
		if(ore.length > 0)
		{
			RSItem toDrop = ore[0];
			
			if(toDrop.hover())
			{
				Mouse.click(3);
				RSMenuNode node = Utils.getOption("Drop");
		
				if(node != null)
					Mouse.moveBox(node.getArea());
			}
		}
	}
	
	private Condition miningCondition()
	{
		return new Condition()
		{
			@Override
			public boolean active()
			{
				General.sleep(100);
				return isMining(false) || isRockStolen();
			}
			
		};	
	}
	
	public long getTotalMiningCount()
	{
		return totalMiningCount;
	}
}
