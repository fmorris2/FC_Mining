package scripts.fc.missions.fcmining.tasks.mining;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.ChooseOption;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSItem;

import scripts.fc.api.generic.FCConditions;
import scripts.fc.api.skills.mining.data.Pickaxe;
import scripts.fc.framework.task.Task;
import scripts.fc.missions.fcmining.FCMining;

public class M1D1 extends Task
{
	private static final long serialVersionUID = 7110081861109808905L;

	public static final int DROPPING_TRIGGER = 2;
	
	private FCMining script;
	
	public M1D1(FCMining script)
	{
		this.script = script;
	}
	
	@Override
	public boolean execute()
	{
		final int INV_SIZE = Inventory.getAll().length;
		boolean dropped = false;
		
		if(ChooseOption.select("Drop"))	
			dropped = true;
		else
		{
			RSItem[] items = Inventory.find(Filters.Items.idNotEquals(Pickaxe.getPickIds()));
			
			if(items.length > 0 && items[0].click("Drop"))
				dropped = true;
		}
		
		if(dropped)
			Timing.waitCondition(FCConditions.inventoryChanged(INV_SIZE), General.random(1500, 3000));
		
		return false;
	}

	@Override
	public boolean shouldExecute()
	{
		return Inventory.getCount(script.rock.getItemId()) > DROPPING_TRIGGER
				|| Inventory.isFull();
	}

	@Override
	public String getStatus()
	{
		return "M1D1";
	}

}
