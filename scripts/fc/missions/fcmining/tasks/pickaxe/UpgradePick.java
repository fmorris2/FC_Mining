package scripts.fc.missions.fcmining.tasks.pickaxe;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Login;
import org.tribot.api2007.Login.STATE;

import scripts.fc.api.generic.FCConditions;
import scripts.fc.api.items.ItemUtils;
import scripts.fc.api.skills.GatheringMode;
import scripts.fc.api.skills.mining.MiningUtils;
import scripts.fc.api.skills.mining.data.Pickaxe;
import scripts.fc.missions.fcmining.FCMining;

public class UpgradePick extends GetPick
{	
	private static final long serialVersionUID = 3795009226394128664L;
	
	private Pickaxe appropriate;
	private Pickaxe current;
	
	private boolean needsBankCheck;
	private boolean hasUpgradeInBank;
	
	public UpgradePick(FCMining script)
	{
		super(script);
	}
	
	public boolean execute()
	{
		script.isCurrentlyUpgrading = true;
		super.execute();
		if(!script.isCurrentlyUpgrading && Banking.close() && ItemUtils.equipItem(appropriate.getItemId())) //means successful pick withdraw
		{
			//Deposit old pick
			final int INV_SIZE = Inventory.getAll().length;
			if(Banking.openBank() && Timing.waitCondition(FCConditions.BANK_LOADED_CONDITION, 3500) && Banking.deposit(1, current.getItemId()))
				Timing.waitCondition(FCConditions.inventoryChanged(INV_SIZE), 3500);
		}
		
		return false;
	}
	
	@Override
	public boolean shouldExecute()
	{
		if(Login.getLoginState() != STATE.INGAME || !script.isUpgradingPick)
			return false;
		
		appropriate = MiningUtils.currentAppropriatePick();
		current = MiningUtils.getBestUsablePick(false);
		
		
		needsBankCheck = (script.fcScript.BANK_OBSERVER.getItemArray().length == 0 && appropriate != current &&
									(script.location.isDepositBox() || script.mode != GatheringMode.BANK)) && 
									(current == null || current.ordinal() < Pickaxe.RUNE.ordinal());
		
		hasUpgradeInBank = script.fcScript.BANK_OBSERVER.containsItem(appropriate.getItemId(), 1);
		
		if(needsBankCheck && !script.hasNoPick)
			General.println("Going to bank to check for pickaxe upgrade...");
		
		if(hasUpgradeInBank)
			General.println("Has pick upgrade in bank!");
		
		return !script.hasNoPick && 
				((appropriate != current && hasUpgradeInBank) || needsBankCheck);
	}

	@Override
	public String getStatus()
	{
		return "Upgrade pick";
	}
}
