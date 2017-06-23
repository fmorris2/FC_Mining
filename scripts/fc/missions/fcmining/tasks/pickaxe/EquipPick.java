package scripts.fc.missions.fcmining.tasks.pickaxe;

import org.tribot.api2007.Banking;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Skills;
import org.tribot.api2007.Skills.SKILLS;

import scripts.fc.api.items.ItemUtils;
import scripts.fc.api.skills.mining.MiningUtils;
import scripts.fc.api.skills.mining.data.Pickaxe;
import scripts.fc.framework.task.Task;

public class EquipPick extends Task
{
	private static final long serialVersionUID = 9034506199145534341L;
	
	private Pickaxe bestUsable;
	
	@Override
	public boolean execute()
	{
		ItemUtils.equipItem(bestUsable.getItemId());
		
		return false;
	}

	@Override
	public boolean shouldExecute()
	{
		bestUsable = MiningUtils.getBestUsablePick(false);
		
		return bestUsable != null && !Equipment.isEquipped(bestUsable.getItemId()) 
				&& Skills.getActualLevel(SKILLS.ATTACK) >= bestUsable.getAttackLevel() && !Banking.isBankScreenOpen();
	}

	@Override
	public String getStatus()
	{
		return "Equip pick";
	}

}
