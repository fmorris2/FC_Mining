package scripts.fc.missions.fcmining.tasks.pickaxe;

import org.tribot.api.Timing;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Login;
import org.tribot.api2007.Login.STATE;
import org.tribot.api2007.WebWalking;

import scripts.fc.api.generic.FCConditions;
import scripts.fc.api.skills.mining.MiningUtils;
import scripts.fc.api.skills.mining.data.Pickaxe;
import scripts.fc.framework.task.Task;
import scripts.fc.missions.fcmining.FCMining;

public class GetPick extends Task
{
	private static final long serialVersionUID = 8486499699564299701L;
	
	protected FCMining script;
	
	public GetPick(FCMining script)
	{
		this.script = script;
	}
	
	@Override
	public boolean execute()
	{
		if(Banking.isInBank())
			handleBanking();
		else if(WebWalking.walkToBank())
			Timing.waitCondition(FCConditions.IN_BANK_CONDITION, 3000);
		
		return false;
	}

	@Override
	public boolean shouldExecute()
	{
		if(Login.getLoginState() != STATE.INGAME || Game.getGameState() != 30)
			return false;
		
		return !script.hasNoPick && MiningUtils.getBestUsablePick(false) == null;
	}

	@Override
	public String getStatus()
	{
		return "Get pick";
	}
	
	private void handleBanking()
	{
		if(!Banking.isBankScreenOpen())
		{
			if(Banking.isDepositBoxOpen())
				Banking.close();
			
			if(Banking.openBank())
				Timing.waitCondition(FCConditions.BANK_LOADED_CONDITION, 5000);
		}
		else
		{
			Pickaxe bestUsable = MiningUtils.getBestUsablePick(true);
			
			int invSize = Inventory.getAll().length;
			
			if(Inventory.getAll().length > 0 && Banking.depositAll() > 0)
				Timing.waitCondition(FCConditions.inventoryChanged(invSize), 3500);
			
			invSize = Inventory.getAll().length;
			
			if(bestUsable != null)
			{
				if((Banking.withdraw(1, bestUsable.getItemId()) && Timing.waitCondition(FCConditions.inventoryChanged(invSize), 3500))
						|| MiningUtils.getBestUsablePick(false) == bestUsable)
					script.isCurrentlyUpgrading = false;
			}
			else
				script.hasNoPick = true;	
		}
	}

}
