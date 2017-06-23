package scripts.fc.fcmining.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.ListModel;

import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.types.RSTile;
import org.tribot.util.Util;

import scripts.fc.api.skills.mining.data.RockType;
import scripts.fc.api.skills.mining.data.locations.CustomMiningLocation;
import scripts.fc.api.skills.mining.data.locations.MiningLocHandler;
import scripts.fc.api.skills.mining.data.locations.MiningLocation;
import scripts.fc.api.wrappers.SerializablePositionable;
import scripts.fc.framework.mission.MissionList;

public class GUIHelper
{
	private final String DIRECTORY_PATH = Util.getWorkingDirectory() + "/FC_Scripts/FC_Mining/";
	private final String MISSION_LISTS = DIRECTORY_PATH + "MissionLists/";
	private final String CUSTOM_LOCATIONS = DIRECTORY_PATH + "CustomLocations/";
	private final String[] DIRECTORIES = {MISSION_LISTS, CUSTOM_LOCATIONS};
	
	public void checkDirs()
	{
		for(String path : DIRECTORIES)
		{
			File dir = new File(path);
			
			if(!dir.exists())
				dir.mkdirs();
		}
	}

	public void loadMissionLists(JComboBox<MissionList> comboBox)
	{
		comboBox.removeAllItems();
		final File MISSION_LIST_DIR = new File(MISSION_LISTS);
		final File[] MISSION_LISTS = MISSION_LIST_DIR.listFiles();
		
		for(File f : MISSION_LISTS)
		{
			try
			{
				FileInputStream fIn = new FileInputStream(f);
				ObjectInputStream objIn = new ObjectInputStream(fIn);
				Object obj = objIn.readObject();
	
				if (obj instanceof MissionList)
				{
					MissionList missionList = (MissionList) obj;
					comboBox.addItem(missionList);
				}
				
				objIn.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public void saveMissionList(MissionList toSave)
	{
		try
		{			
			FileOutputStream fOut = new FileOutputStream(MISSION_LISTS + toSave.getName() + ".ser");
			ObjectOutputStream objOut = new ObjectOutputStream (fOut);
			objOut.writeObject (toSave);
			objOut.close();	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void deleteMission(String fileName)
	{
		File toRemove = new File(MISSION_LISTS + fileName + ".ser");
		toRemove.delete();
	}

	public void loadCustomLocations(JComboBox<CustomMiningLocation> comboBox, List<CustomMiningLocation> locsList)
	{
		comboBox.removeAllItems();
		locsList.clear();
		final File CUSTOM_LOCS_DIR = new File(CUSTOM_LOCATIONS);
		final File[] CUSTOM_LOCATIONS = CUSTOM_LOCS_DIR.listFiles();
		
		for(File f : CUSTOM_LOCATIONS)
		{
			try
			{
				FileInputStream fIn = new FileInputStream(f);
				ObjectInputStream objIn = new ObjectInputStream(fIn);
				Object obj = objIn.readObject();
	
				if (obj instanceof CustomMiningLocation)
				{
					CustomMiningLocation customLoc = (CustomMiningLocation) obj;
					comboBox.addItem(customLoc);
					locsList.add(customLoc);
				}
				
				objIn.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public void saveCustomLocation(CustomMiningLocation loc)
	{
		try
		{			
			FileOutputStream fOut = new FileOutputStream(CUSTOM_LOCATIONS + loc.getName() + ".ser");
			ObjectOutputStream objOut = new ObjectOutputStream (fOut);
			objOut.writeObject(loc);
			objOut.close();	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void deleteCustomLocation(String fileName)
	{
		File toRemove = new File(CUSTOM_LOCATIONS + fileName + ".ser");
		toRemove.delete();
	}

	public SerializablePositionable[] convertBankPath(ListModel<Positionable> model)
	{
		SerializablePositionable[] converted = new SerializablePositionable[model.getSize()];
		
		for(int i = 0; i < model.getSize(); i++)
		{
			RSTile p = model.getElementAt(i).getPosition();
			converted[i] = new SerializablePositionable(p.getX(), p.getY(), p.getPlane());
		}
		
		return converted;
	}
	
	public RockType[] convertSupportedRocks(JCheckBox... boxes)
	{
		List<RockType> supported = new ArrayList<>();
		
		for(JCheckBox box : boxes)
		{
			if(!box.isSelected())
				continue;
			
			for(RockType r : RockType.values())
			{
				if(box.getText().toLowerCase().contains(r.name().toLowerCase()))
				{
					supported.add(r);
					break;
				}
			}
		}
		
		return supported.toArray(new RockType[supported.size()]);
	}
	
	public void loadBankPath(DefaultListModel<Positionable> model, List<Positionable> positions)
	{
		for(Positionable p : positions)
			model.addElement(p);
	}
	
	public void loadSupportedRocks(List<RockType> supportedRocks, JCheckBox... boxes)
	{
		if(supportedRocks == null)
			return;
		
		for(RockType type : supportedRocks)
		{	
			for(JCheckBox box : boxes)
			{
				if(box.getText().toLowerCase().contains(type.name().toLowerCase()))
				{
					box.setSelected(true);
					break;
				}
			}
		}
	}
	
	public void setSelectedCustomLoc(String name, JComboBox<CustomMiningLocation> comboBox)
	{
		for(int i = 0; i < comboBox.getItemCount(); i++)
		{
			CustomMiningLocation loc  = comboBox.getItemAt(i);
			
			if(loc.getName().equals(name))
			{
				comboBox.setSelectedItem(loc);
				break;
			}
		}
	}

	public void loadAppropriateLocs(RockType rock, JComboBox<MiningLocation> comboBox, List<CustomMiningLocation> customLocs)
	{
		for(MiningLocation l : MiningLocHandler.LOCATIONS)
			if(l.getSupported().contains(rock))
				comboBox.addItem(l);
		
		for(CustomMiningLocation custom : customLocs)
			if(custom.getSupported() != null && custom.getSupported().contains(rock))
				comboBox.addItem(custom);
	}
	
	public long parseTime(String str)
	{
		//hh:mm:ss format
		String[] parts = str.split(":");
		long total = 0;
		
		total += Integer.parseInt(parts[0]) * (60000 * 60);
		total += Integer.parseInt(parts[1]) * 60000;
		total += Integer.parseInt(parts[2]) * 1000;
		
		return total;
	}

	public void setSelectedMission(String name, JComboBox<MissionList> comboBox)
	{
		DefaultComboBoxModel<MissionList> model = (DefaultComboBoxModel<MissionList>)comboBox.getModel();
		for(int i = 0; i < comboBox.getItemCount(); i++)
		{
			if(((MissionList)model.getElementAt(i)).getName().equals(name))
			{
				comboBox.setSelectedIndex(i);
				break;
			}
		}
	}

}	
