package scripts.fc.missions.fcmining.tasks.mining;

import org.tribot.api.Timing;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;

import scripts.fc.api.generic.FCConditions;
import scripts.fc.api.skills.mining.MiningUtils;
import scripts.fc.api.skills.mining.data.Pickaxe;
import scripts.fc.framework.task.Task;
import scripts.fc.missions.fcmining.FCMining;

public class BankOre extends Task
{
	private static final long serialVersionUID = 2219431375840259810L;
	
	private FCMining script;
	
	public BankOre(FCMining script)
	{
		this.script = script;
	}
	
	@Override
	public boolean execute()
	{
		if(script.location.isInBank())
		{
			final int INV_SPACE = Inventory.getAll().length;
			
			if(script.location.isBankScreenOpen())
			{
				Pickaxe bestUsable = MiningUtils.getBestUsablePick(false);
				if(Banking.depositAllExcept(bestUsable == null ? Pickaxe.getPickIds() : new int[]{bestUsable.getItemId()}) > 0)
					Timing.waitCondition(FCConditions.inventoryChanged(INV_SPACE), 3000);
			}
			else
				script.location.openBank();
		}
		else
			script.location.goToBank();
		
		return false;
	}

	@Override
	public boolean shouldExecute()
	{
		return Inventory.isFull();
	}

	@Override
	public String getStatus()
	{
		return "Bank ore";
	}

}
