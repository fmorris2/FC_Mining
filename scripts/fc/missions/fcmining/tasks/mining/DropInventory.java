package scripts.fc.missions.fcmining.tasks.mining;

import org.tribot.api2007.Inventory;

import scripts.fc.api.skills.mining.data.Pickaxe;
import scripts.fc.framework.task.Task;

public class DropInventory extends Task
{	
	private static final long serialVersionUID = 1888370287962112209L;

	@Override
	public boolean execute()
	{
		Inventory.dropAllExcept(Pickaxe.getPickIds());
		
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
		return "Drop inventory";
	}

}
