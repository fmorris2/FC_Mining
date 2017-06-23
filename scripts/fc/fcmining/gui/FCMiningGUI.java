package scripts.fc.fcmining.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

import org.tribot.api.General;
import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills.SKILLS;

import scripts.fc.api.skills.GatheringMode;
import scripts.fc.api.skills.ProgressionType;
import scripts.fc.api.skills.mining.data.RockType;
import scripts.fc.api.skills.mining.data.locations.CustomMiningLocation;
import scripts.fc.api.skills.mining.data.locations.MiningLocation;
import scripts.fc.api.utils.Utils;
import scripts.fc.api.wrappers.SerializablePositionable;
import scripts.fc.fcmining.FCMiningScript;
import scripts.fc.framework.WorldHopSettings;
import scripts.fc.framework.goal.Goal;
import scripts.fc.framework.goal.impl.InfiniteGoal;
import scripts.fc.framework.goal.impl.ResourceGoal;
import scripts.fc.framework.goal.impl.SkillGoal;
import scripts.fc.framework.goal.impl.TimeGoal;
import scripts.fc.framework.mission.Mission;
import scripts.fc.framework.mission.MissionList;
import scripts.fc.missions.fcmining.FCMining;

public class FCMiningGUI extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private JCheckBox[] rockCheckboxes;
	private transient FCMiningScript script;
	private GUIHelper helper;
	private List<Mission> missions = new ArrayList<Mission>();
	private List<CustomMiningLocation> customLocs = new ArrayList<CustomMiningLocation>();
	private DefaultListModel<Positionable> customBankPathModel = new DefaultListModel<>();
	private Positionable customLocCenter;
	
	public boolean hasFilledOut;
	
	public FCMiningGUI(FCMiningScript script) 
	{
		initComponents();
		this.script = script;
		this.helper = new GUIHelper();
        helper.checkDirs();
        loadThings();
        customLocBankPathList.setModel(customBankPathModel);
        Utils.handleGui(this);
    }
	
	private void loadThings()
	{
		helper.loadMissionLists(missionComboBox);
        helper.loadCustomLocations(customLocationLoadBox, customLocs);
        rockTypeComboBox.setModel(new DefaultComboBoxModel<RockType>(RockType.values()));
        helper.loadAppropriateLocs((RockType)rockTypeComboBox.getSelectedItem(), locationComboBox, customLocs);
        rockCheckboxes = new JCheckBox[]{customLocClay, customLocCopper, customLocTin, customLocIron, 
        		customLocSilver, customLocCoal, customLocGold, customLocMithril, customLocAdamantite, customLocRunite};
	}
	
	public void dispose()
	{
		this.missionFrame.dispose();
		super.dispose();
	}
                   
	@SuppressWarnings({ "serial", "unchecked", "rawtypes" })
	private void initComponents() {

        missionFrame = new javax.swing.JFrame();
        missionSettingsPane = new javax.swing.JTabbedPane();
        settingsTab = new javax.swing.JPanel();
        rockTypeLabel = new javax.swing.JLabel();
        rockTypeComboBox = new javax.swing.JComboBox();
        locationLabel = new javax.swing.JLabel();
        locationComboBox = new javax.swing.JComboBox();
        bankOreButton = new javax.swing.JRadioButton();
        modeLabel = new javax.swing.JLabel();
        dropInventoryButton = new javax.swing.JRadioButton();
        m1d1Button = new javax.swing.JRadioButton();
        progressionLabel = new javax.swing.JLabel();
        experienceButton = new javax.swing.JRadioButton();
        moneyButton = new javax.swing.JRadioButton();
        abcl10CheckBox = new javax.swing.JCheckBox();
        pickUpgradingCheckBox = new javax.swing.JCheckBox();
        customLocTab = new javax.swing.JPanel();
        customLocNameLabel = new javax.swing.JLabel();
        customLocNameTextBox = new javax.swing.JTextField();
        customLocCenterLabel = new javax.swing.JLabel();
        currentCenterTileLabel = new javax.swing.JLabel();
        grabTileButton = new javax.swing.JButton();
        customLocEnabledBox = new javax.swing.JCheckBox();
        customLocRadiusLabel = new javax.swing.JLabel();
        customLocRadiusSpinner = new javax.swing.JSpinner();
        customLocBankPathLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        customLocBankPathList = new javax.swing.JList();
        customLocSupportedRocksLabel = new javax.swing.JLabel();
        customBankPathAddButton = new javax.swing.JButton();
        customBankPathClearButton = new javax.swing.JButton();
        customBankPathRemoveButton = new javax.swing.JButton();
        customLocClay = new javax.swing.JCheckBox();
        customLocCopper = new javax.swing.JCheckBox();
        customLocTin = new javax.swing.JCheckBox();
        customLocIron = new javax.swing.JCheckBox();
        customLocSilver = new javax.swing.JCheckBox();
        customLocCoal = new javax.swing.JCheckBox();
        customLocMithril = new javax.swing.JCheckBox();
        customLocAdamantite = new javax.swing.JCheckBox();
        customLocRunite = new javax.swing.JCheckBox();
        customLocationLoadBox = new javax.swing.JComboBox();
        customLocLoadButton = new javax.swing.JButton();
        customLocSaveButton = new javax.swing.JButton();
        customLocDeleteButton = new javax.swing.JButton();
        customLocGold = new javax.swing.JCheckBox();
        isDepositBoxCheckBox = new javax.swing.JCheckBox();
        muleTab = new javax.swing.JPanel();
        muleEnabledBox = new javax.swing.JCheckBox();
        muleUsernameLabel = new javax.swing.JLabel();
        muleUsernameInput = new javax.swing.JTextField();
        withdrawOreFromBankLabel = new javax.swing.JLabel();
        beforeMuleButton = new javax.swing.JRadioButton();
        afterMuleButton = new javax.swing.JRadioButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        worldHoppingTab = new javax.swing.JPanel();
        worldHoppingEnabledCheckBox = new javax.swing.JCheckBox();
        playersInAreaCheckBox = new javax.swing.JCheckBox();
        playersInAreaSpinner = new javax.swing.JSpinner();
        oreStolenCheckBox = new javax.swing.JCheckBox();
        oreStolenSpinner = new javax.swing.JSpinner();
        noOreAvailableCheckBox = new javax.swing.JCheckBox();
        jScrollPane5 = new javax.swing.JScrollPane();
        worldHoppingTextArea = new javax.swing.JTextArea();
        goalsPanel = new javax.swing.JPanel();
        miningLevelCheckBox = new javax.swing.JCheckBox();
        miningLevelSpinner = new javax.swing.JSpinner();
        timeElapsedCheckBox = new javax.swing.JCheckBox();
        timeElapsedTextField = new javax.swing.JTextField();
        oreGainedCheckBox = new javax.swing.JCheckBox();
        oreGainedSpinner = new javax.swing.JSpinner();
        infiniteCheckBox = new javax.swing.JCheckBox();
        jScrollPane3 = new javax.swing.JScrollPane();
        goalsTextArea = new javax.swing.JTextArea();
        finalizeMissionButton = new javax.swing.JButton();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        contentPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        missionTable = new javax.swing.JTable();
        addMissionButton = new javax.swing.JButton();
        removeMissionButton = new javax.swing.JButton();
        titleLabel = new javax.swing.JLabel();
        startButton = new javax.swing.JButton();
        loadMissionsButton = new javax.swing.JButton();
        missionComboBox = new javax.swing.JComboBox();
        missionSaveButton = new javax.swing.JButton();
        missionProfileName = new javax.swing.JTextField();
        missionDeleteButton = new javax.swing.JButton();

        missionFrame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        missionFrame.setTitle("Add Mission");
        missionFrame.setAlwaysOnTop(true);
        missionFrame.setMinimumSize(new java.awt.Dimension(500, 580));
        missionFrame.setName("Add Mission"); // NOI18N
        missionFrame.setPreferredSize(new java.awt.Dimension(500, 580));
        missionFrame.setResizable(false);

        missionSettingsPane.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        missionSettingsPane.setFont(new java.awt.Font("GungsuhChe", 0, 12)); // NOI18N

        rockTypeLabel.setFont(new java.awt.Font("GungsuhChe", 0, 12)); // NOI18N
        rockTypeLabel.setText("Rock Type:");

        rockTypeComboBox.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        rockTypeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rockTypeComboBoxActionPerformed(evt);
            }
        });

        locationLabel.setFont(new java.awt.Font("GungsuhChe", 0, 12)); // NOI18N
        locationLabel.setText("Location:");

        locationComboBox.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N

        buttonGroup1.add(bankOreButton);
        bankOreButton.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        bankOreButton.setSelected(true);
        bankOreButton.setText("Bank Ore");

        modeLabel.setFont(new java.awt.Font("GungsuhChe", 0, 12)); // NOI18N
        modeLabel.setText("Mode:");

        buttonGroup1.add(dropInventoryButton);
        dropInventoryButton.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        dropInventoryButton.setText("Drop Inventory");

        buttonGroup1.add(m1d1Button);
        m1d1Button.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        m1d1Button.setText("M1D1");

        progressionLabel.setFont(new java.awt.Font("GungsuhChe", 0, 12)); // NOI18N
        progressionLabel.setText("Progression Type:");

        buttonGroup2.add(experienceButton);
        experienceButton.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        experienceButton.setSelected(true);
        experienceButton.setText("Experience");

        buttonGroup2.add(moneyButton);
        moneyButton.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        moneyButton.setText("Money");

        abcl10CheckBox.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        abcl10CheckBox.setSelected(true);
        abcl10CheckBox.setText("ABC2L 10");

        pickUpgradingCheckBox.setSelected(true);
        pickUpgradingCheckBox.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        pickUpgradingCheckBox.setText("Pick upgrading?");

        javax.swing.GroupLayout settingsTabLayout = new javax.swing.GroupLayout(settingsTab);
        settingsTab.setLayout(settingsTabLayout);
        settingsTabLayout.setHorizontalGroup(
            settingsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingsTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(settingsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(settingsTabLayout.createSequentialGroup()
                        .addGroup(settingsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(locationLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rockTypeLabel, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(10, 10, 10)
                        .addGroup(settingsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rockTypeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(locationComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(settingsTabLayout.createSequentialGroup()
                        .addComponent(modeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(49, 49, 49))
                    .addGroup(settingsTabLayout.createSequentialGroup()
                        .addGroup(settingsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pickUpgradingCheckBox)
                            .addComponent(moneyButton)
                            .addComponent(experienceButton)
                            .addComponent(progressionLabel)
                            .addComponent(abcl10CheckBox)
                            .addComponent(m1d1Button)
                            .addComponent(dropInventoryButton)
                            .addComponent(bankOreButton))
                        .addGap(0, 78, Short.MAX_VALUE)))
                .addContainerGap())
        );
        settingsTabLayout.setVerticalGroup(
            settingsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingsTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(settingsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rockTypeLabel)
                    .addComponent(rockTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(settingsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(locationLabel)
                    .addComponent(locationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(modeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bankOreButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dropInventoryButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(m1d1Button)
                .addGap(26, 26, 26)
                .addComponent(progressionLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(experienceButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(moneyButton)
                .addGap(18, 18, 18)
                .addComponent(abcl10CheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pickUpgradingCheckBox)
                .addContainerGap(58, Short.MAX_VALUE))
        );

        missionSettingsPane.addTab("Settings", settingsTab);

        customLocNameLabel.setFont(new java.awt.Font("GungsuhChe", 0, 12)); // NOI18N
        customLocNameLabel.setText("Name:");

        customLocCenterLabel.setFont(new java.awt.Font("GungsuhChe", 0, 12)); // NOI18N
        customLocCenterLabel.setText("Center Tile:");

        currentCenterTileLabel.setFont(new java.awt.Font("GungsuhChe", 0, 8)); // NOI18N
        currentCenterTileLabel.setText("(0, 0, 0)");

        grabTileButton.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        grabTileButton.setText("Grab tile");
        grabTileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grabTileButtonActionPerformed(evt);
            }
        });

        customLocEnabledBox.setFont(new java.awt.Font("GungsuhChe", 0, 14)); // NOI18N
        customLocEnabledBox.setText("Enabled");

        customLocRadiusLabel.setFont(new java.awt.Font("GungsuhChe", 0, 12)); // NOI18N
        customLocRadiusLabel.setText("Radius:");

        customLocRadiusSpinner.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        customLocRadiusSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 40, 1));

        customLocBankPathLabel.setFont(new java.awt.Font("GungsuhChe", 0, 12)); // NOI18N
        customLocBankPathLabel.setText("Custom Bank Path (Optional):");

        customLocBankPathList.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        jScrollPane2.setViewportView(customLocBankPathList);

        customLocSupportedRocksLabel.setFont(new java.awt.Font("GungsuhChe", 0, 12)); // NOI18N
        customLocSupportedRocksLabel.setText("Supported Rocks:");

        customBankPathAddButton.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        customBankPathAddButton.setText("Add");
        customBankPathAddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customBankPathAddButtonActionPerformed(evt);
            }
        });

        customBankPathClearButton.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        customBankPathClearButton.setText("Clear");
        customBankPathClearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customBankPathClearButtonActionPerformed(evt);
            }
        });

        customBankPathRemoveButton.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        customBankPathRemoveButton.setText("Remove");
        customBankPathRemoveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customBankPathRemoveButtonActionPerformed(evt);
            }
        });

        customLocClay.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        customLocClay.setText("Clay");

        customLocCopper.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        customLocCopper.setText("Copper");

        customLocTin.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        customLocTin.setText("Tin");

        customLocIron.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        customLocIron.setText("Iron");

        customLocSilver.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        customLocSilver.setText("Silver");

        customLocCoal.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        customLocCoal.setText("Coal");

        customLocMithril.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        customLocMithril.setText("Mithril");

        customLocAdamantite.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        customLocAdamantite.setText("Adamantite");

        customLocRunite.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        customLocRunite.setText("Runite");

        customLocationLoadBox.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N

        customLocLoadButton.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        customLocLoadButton.setText("Load");
        customLocLoadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customLocLoadButtonActionPerformed(evt);
            }
        });

        customLocSaveButton.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        customLocSaveButton.setText("Save");
        customLocSaveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customLocSaveButtonActionPerformed(evt);
            }
        });

        customLocDeleteButton.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        customLocDeleteButton.setText("Delete");
        customLocDeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customLocDeleteButtonActionPerformed(evt);
            }
        });

        customLocGold.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        customLocGold.setText("Gold");

        isDepositBoxCheckBox.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        isDepositBoxCheckBox.setText("Bank is deposit box?");

        javax.swing.GroupLayout customLocTabLayout = new javax.swing.GroupLayout(customLocTab);
        customLocTab.setLayout(customLocTabLayout);
        customLocTabLayout.setHorizontalGroup(
            customLocTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customLocTabLayout.createSequentialGroup()
                .addGroup(customLocTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(customLocTabLayout.createSequentialGroup()
                        .addGroup(customLocTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(customLocTabLayout.createSequentialGroup()
                                .addGap(56, 56, 56)
                                .addComponent(customLocEnabledBox))
                            .addGroup(customLocTabLayout.createSequentialGroup()
                                .addGroup(customLocTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(customLocationLoadBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(customLocDeleteButton, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(customLocTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(customLocSaveButton, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                                    .addComponent(customLocLoadButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, customLocTabLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(customLocTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(customLocSupportedRocksLabel)
                            .addGroup(customLocTabLayout.createSequentialGroup()
                                .addComponent(customLocClay)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(customLocCopper)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(customLocTin)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(customLocIron))
                            .addGroup(customLocTabLayout.createSequentialGroup()
                                .addGroup(customLocTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(customLocSilver)
                                    .addComponent(customLocMithril))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(customLocTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(customLocTabLayout.createSequentialGroup()
                                        .addComponent(customLocAdamantite)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(customLocRunite, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(customLocTabLayout.createSequentialGroup()
                                        .addComponent(customLocCoal)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(customLocGold)
                                        .addGap(0, 0, Short.MAX_VALUE)))))))
                .addContainerGap())
            .addGroup(customLocTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(customLocTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(customLocTabLayout.createSequentialGroup()
                        .addComponent(isDepositBoxCheckBox)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, customLocTabLayout.createSequentialGroup()
                        .addGroup(customLocTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, customLocTabLayout.createSequentialGroup()
                                .addComponent(customLocNameLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(customLocNameTextBox))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, customLocTabLayout.createSequentialGroup()
                                .addComponent(customLocCenterLabel)
                                .addGap(2, 2, 2)
                                .addComponent(currentCenterTileLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(customLocRadiusLabel)
                                .addGap(10, 10, 10)))
                        .addContainerGap())
                    .addGroup(customLocTabLayout.createSequentialGroup()
                        .addComponent(customLocBankPathLabel)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, customLocTabLayout.createSequentialGroup()
                        .addComponent(grabTileButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(customLocRadiusSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, customLocTabLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(customLocTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(customBankPathAddButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(customBankPathRemoveButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(customBankPathClearButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18))))
        );
        customLocTabLayout.setVerticalGroup(
            customLocTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customLocTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(customLocEnabledBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(customLocTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(customLocNameLabel)
                    .addComponent(customLocNameTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(customLocTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(customLocCenterLabel)
                    .addComponent(currentCenterTileLabel)
                    .addComponent(customLocRadiusLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(customLocTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(grabTileButton)
                    .addComponent(customLocRadiusSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(customLocBankPathLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(customLocTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(customLocTabLayout.createSequentialGroup()
                        .addComponent(customBankPathAddButton)
                        .addGap(5, 5, 5)
                        .addComponent(customBankPathRemoveButton)
                        .addGap(5, 5, 5)
                        .addComponent(customBankPathClearButton))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(isDepositBoxCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
                .addComponent(customLocSupportedRocksLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(customLocTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(customLocClay)
                    .addComponent(customLocCopper)
                    .addComponent(customLocTin)
                    .addComponent(customLocIron))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(customLocTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(customLocSilver)
                    .addComponent(customLocCoal)
                    .addComponent(customLocGold))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(customLocTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(customLocAdamantite)
                    .addComponent(customLocRunite)
                    .addComponent(customLocMithril))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(customLocTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(customLocationLoadBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customLocLoadButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(customLocTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(customLocSaveButton)
                    .addComponent(customLocDeleteButton)))
        );

        missionSettingsPane.addTab("Custom Location", customLocTab);

        muleEnabledBox.setFont(new java.awt.Font("GungsuhChe", 0, 14)); // NOI18N
        muleEnabledBox.setText("Enabled");
        muleEnabledBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                muleEnabledBoxActionPerformed(evt);
            }
        });

        muleUsernameLabel.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        muleUsernameLabel.setText("Mule username:");

        muleUsernameInput.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N

        withdrawOreFromBankLabel.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        withdrawOreFromBankLabel.setText("Withdraw ore from bank:");

        buttonGroup3.add(beforeMuleButton);
        beforeMuleButton.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        beforeMuleButton.setText("Before going to mule");

        buttonGroup3.add(afterMuleButton);
        afterMuleButton.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        afterMuleButton.setText("After going to mule");

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText("TIPS:\n\nMuling will be triggered AFTER completion of the mission.\n\nALL types of ores will be withdrawn from the bank and traded.\n\nMake sure you enter in the mule username correctly (case insensitive).\n\nWithdraw after going to mule if path to mule is dangerous / mule is located at a bank.\n\nYou have to use the FC Mule script for the mule.");
        jTextArea1.setWrapStyleWord(true);
        jScrollPane4.setViewportView(jTextArea1);

        javax.swing.GroupLayout muleTabLayout = new javax.swing.GroupLayout(muleTab);
        muleTab.setLayout(muleTabLayout);
        muleTabLayout.setHorizontalGroup(
            muleTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, muleTabLayout.createSequentialGroup()
                .addGroup(muleTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, muleTabLayout.createSequentialGroup()
                        .addGroup(muleTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(muleTabLayout.createSequentialGroup()
                                .addGap(56, 56, 56)
                                .addComponent(muleEnabledBox))
                            .addGroup(muleTabLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(muleTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(withdrawOreFromBankLabel)
                                    .addGroup(muleTabLayout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addGroup(muleTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(afterMuleButton)
                                            .addComponent(beforeMuleButton))))))
                        .addGap(0, 38, Short.MAX_VALUE))
                    .addGroup(muleTabLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(muleTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane4)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, muleTabLayout.createSequentialGroup()
                                .addComponent(muleUsernameLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(muleUsernameInput)))))
                .addContainerGap())
        );
        muleTabLayout.setVerticalGroup(
            muleTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(muleTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(muleEnabledBox)
                .addGap(18, 18, 18)
                .addGroup(muleTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(muleUsernameLabel)
                    .addComponent(muleUsernameInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(withdrawOreFromBankLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(beforeMuleButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(afterMuleButton)
                .addGap(59, 59, 59)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                .addContainerGap())
        );

        missionSettingsPane.addTab("Mule", muleTab);

        worldHoppingEnabledCheckBox.setFont(new java.awt.Font("GungsuhChe", 0, 14)); // NOI18N
        worldHoppingEnabledCheckBox.setText("Enabled");

        playersInAreaCheckBox.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        playersInAreaCheckBox.setText("Players in area");

        playersInAreaSpinner.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N

        oreStolenCheckBox.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        oreStolenCheckBox.setText("Ore stolen");

        oreStolenSpinner.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N

        noOreAvailableCheckBox.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        noOreAvailableCheckBox.setText("No ore available");

        worldHoppingTextArea.setEditable(false);
        worldHoppingTextArea.setColumns(20);
        worldHoppingTextArea.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        worldHoppingTextArea.setLineWrap(true);
        worldHoppingTextArea.setRows(5);
        worldHoppingTextArea.setText("Worldhopping is a feature that can help with efficiency and antiban. \n\nAfraid of player reports? Set it to hop when one player is around. \n\nMining runite? Hop when no ore is available. \n\nThis utilizes the in game world hopper for maximium efficiency.");
        worldHoppingTextArea.setWrapStyleWord(true);
        jScrollPane5.setViewportView(worldHoppingTextArea);

        javax.swing.GroupLayout worldHoppingTabLayout = new javax.swing.GroupLayout(worldHoppingTab);
        worldHoppingTab.setLayout(worldHoppingTabLayout);
        worldHoppingTabLayout.setHorizontalGroup(
            worldHoppingTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(worldHoppingTabLayout.createSequentialGroup()
                .addGroup(worldHoppingTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(worldHoppingTabLayout.createSequentialGroup()
                        .addGroup(worldHoppingTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(worldHoppingTabLayout.createSequentialGroup()
                                .addGap(54, 54, 54)
                                .addComponent(worldHoppingEnabledCheckBox))
                            .addGroup(worldHoppingTabLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(worldHoppingTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(worldHoppingTabLayout.createSequentialGroup()
                                        .addGap(21, 21, 21)
                                        .addComponent(playersInAreaSpinner))
                                    .addComponent(playersInAreaCheckBox)))
                            .addGroup(worldHoppingTabLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(worldHoppingTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(worldHoppingTabLayout.createSequentialGroup()
                                        .addGap(21, 21, 21)
                                        .addComponent(oreStolenSpinner))
                                    .addComponent(oreStolenCheckBox)))
                            .addGroup(worldHoppingTabLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(noOreAvailableCheckBox)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(worldHoppingTabLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)))
                .addContainerGap())
        );
        worldHoppingTabLayout.setVerticalGroup(
            worldHoppingTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(worldHoppingTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(worldHoppingEnabledCheckBox)
                .addGap(18, 18, 18)
                .addComponent(playersInAreaCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(playersInAreaSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(oreStolenCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(oreStolenSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(noOreAvailableCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                .addContainerGap())
        );

        missionSettingsPane.addTab("Worldhopping", worldHoppingTab);

        goalsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Goals", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("GungsuhChe", 1, 12))); // NOI18N

        miningLevelCheckBox.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        miningLevelCheckBox.setText("Mining Level");

        miningLevelSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 99, 1));

        timeElapsedCheckBox.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        timeElapsedCheckBox.setText("Time Elapsed");

        timeElapsedTextField.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        timeElapsedTextField.setText("hh:mm:ss");

        oreGainedCheckBox.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        oreGainedCheckBox.setText("Ore Gained");

        oreGainedSpinner.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        oreGainedSpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, 1000000, 1));

        infiniteCheckBox.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        infiniteCheckBox.setText("Run Mission Infinitely");

        goalsTextArea.setEditable(false);
        goalsTextArea.setColumns(20);
        goalsTextArea.setFont(new java.awt.Font("GungsuhChe", 2, 13)); // NOI18N
        goalsTextArea.setLineWrap(true);
        goalsTextArea.setRows(5);
        goalsTextArea.setText("If you choose multiple goals for a single mission, they all must be completed before the script moves on to the next mission.");
        goalsTextArea.setWrapStyleWord(true);
        jScrollPane3.setViewportView(goalsTextArea);

        javax.swing.GroupLayout goalsPanelLayout = new javax.swing.GroupLayout(goalsPanel);
        goalsPanel.setLayout(goalsPanelLayout);
        goalsPanelLayout.setHorizontalGroup(
            goalsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(goalsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(goalsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(goalsPanelLayout.createSequentialGroup()
                        .addGroup(goalsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(goalsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(oreGainedSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, goalsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(oreGainedCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, goalsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(timeElapsedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, goalsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(goalsPanelLayout.createSequentialGroup()
                                                .addGap(21, 21, 21)
                                                .addComponent(miningLevelSpinner))
                                            .addComponent(miningLevelCheckBox))
                                        .addComponent(timeElapsedCheckBox, javax.swing.GroupLayout.Alignment.LEADING))))
                            .addComponent(infiniteCheckBox))
                        .addGap(0, 51, Short.MAX_VALUE)))
                .addContainerGap())
        );
        goalsPanelLayout.setVerticalGroup(
            goalsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(goalsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(miningLevelCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(miningLevelSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(timeElapsedCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(timeElapsedTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(oreGainedCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(oreGainedSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(infiniteCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3)
                .addContainerGap())
        );

        finalizeMissionButton.setFont(new java.awt.Font("GungsuhChe", 1, 16)); // NOI18N
        finalizeMissionButton.setText("Add Mission");
        finalizeMissionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                finalizeMissionButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout missionFrameLayout = new javax.swing.GroupLayout(missionFrame.getContentPane());
        missionFrame.getContentPane().setLayout(missionFrameLayout);
        missionFrameLayout.setHorizontalGroup(
            missionFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, missionFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(missionFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(finalizeMissionButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(missionFrameLayout.createSequentialGroup()
                        .addComponent(missionSettingsPane, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(goalsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        missionFrameLayout.setVerticalGroup(
            missionFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(missionFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(missionFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(goalsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(missionSettingsPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(finalizeMissionButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("FC Mining");
        setAlwaysOnTop(true);
        setName("FC Mining"); // NOI18N
        setResizable(false);

        contentPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Missions", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("GungsuhChe", 0, 18))); // NOI18N

        missionTable.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        missionTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Location", "Rock Type", "Goals"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        missionTable.setToolTipText("");
        jScrollPane1.setViewportView(missionTable);

        addMissionButton.setFont(new java.awt.Font("GungsuhChe", 1, 14)); // NOI18N
        addMissionButton.setText("Add Mission");
        addMissionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addMissionButtonActionPerformed(evt);
            }
        });

        removeMissionButton.setFont(new java.awt.Font("GungsuhChe", 1, 14)); // NOI18N
        removeMissionButton.setText("Remove Mission");
        removeMissionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeMissionButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout contentPanelLayout = new javax.swing.GroupLayout(contentPanel);
        contentPanel.setLayout(contentPanelLayout);
        contentPanelLayout.setHorizontalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(contentPanelLayout.createSequentialGroup()
                        .addComponent(addMissionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(removeMissionButton)))
                .addContainerGap())
        );
        contentPanelLayout.setVerticalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addMissionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(removeMissionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        contentPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {addMissionButton, removeMissionButton});

        titleLabel.setFont(new java.awt.Font("GungsuhChe", 0, 48)); // NOI18N
        titleLabel.setText("FC Mining");

        startButton.setFont(new java.awt.Font("GungsuhChe", 1, 14)); // NOI18N
        startButton.setText("Start");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        loadMissionsButton.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        loadMissionsButton.setText("Load");
        loadMissionsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadMissionsButtonActionPerformed(evt);
            }
        });

        missionComboBox.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        missionComboBox.setMaximumRowCount(100);
        missionComboBox.setEditor(null);

        missionSaveButton.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        missionSaveButton.setText("Save");
        missionSaveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                missionSaveButtonActionPerformed(evt);
            }
        });

        missionProfileName.setFont(new java.awt.Font("GungsuhChe", 0, 11)); // NOI18N
        missionProfileName.setText("profileName");

        missionDeleteButton.setText("Delete");
        missionDeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                missionDeleteButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(160, 164, Short.MAX_VALUE)
                .addComponent(titleLabel)
                .addGap(160, 160, 160))
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(missionSaveButton, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                    .addComponent(missionComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(loadMissionsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(missionProfileName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(missionDeleteButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(contentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(contentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(loadMissionsButton)
                            .addComponent(missionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(missionSaveButton)
                            .addComponent(missionProfileName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(missionDeleteButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>      

	private void addMissionButtonActionPerformed(java.awt.event.ActionEvent evt)
	{
		missionFrame.setVisible(true);
		clearCustomLocTab();
	}

	private void finalizeMissionButtonActionPerformed(java.awt.event.ActionEvent evt)
	{
		boolean isUsingAbc = abcl10CheckBox.isSelected();
		boolean isUpgradingPick = pickUpgradingCheckBox.isSelected();
		RockType selected = (RockType)rockTypeComboBox.getSelectedItem();
		RockType rock = selected == RockType.AUTO_SELECT ? null : selected;
		GatheringMode mode = bankOreButton.isSelected() ? GatheringMode.BANK : dropInventoryButton.isSelected() 
				? GatheringMode.DROP_INVENTORY : GatheringMode.G1D1;
		MiningLocation loc = !customLocEnabledBox.isSelected() ? (MiningLocation)locationComboBox.getSelectedItem() : getCustomMiningLoc();
		ProgressionType prog = experienceButton.isSelected() ? ProgressionType.EXPERIENCE : ProgressionType.MONEY;
		Goal[] goals = getGoals();
		WorldHopSettings hopSettings = null;
		if(worldHoppingEnabledCheckBox.isSelected())
		{
			hopSettings = new WorldHopSettings(-1, -1, false);
			
			if(playersInAreaCheckBox.isSelected())
				hopSettings.playersInArea = (int)playersInAreaSpinner.getValue();
			
			if(oreStolenCheckBox.isSelected())
				hopSettings.resourceStolen = (int)oreStolenSpinner.getValue();
			
			hopSettings.noResourceAvailable = noOreAvailableCheckBox.isSelected();
		}
		
		FCMining mission = new FCMining(script, isUsingAbc, isUpgradingPick, hopSettings, rock, mode, loc, prog, goals);
		mission.locHandler.customLocations.addAll(customLocs);
		missions.add(mission);
		((DefaultTableModel)missionTable.getModel()).addRow(getTableInfo(mission));
		missionFrame.setVisible(false);
	}  

	private void muleEnabledBoxActionPerformed(java.awt.event.ActionEvent evt)
	{}

	private void rockTypeComboBoxActionPerformed(java.awt.event.ActionEvent evt)
	{
		locationComboBox.removeAllItems();
		helper.loadAppropriateLocs((RockType)rockTypeComboBox.getSelectedItem(), locationComboBox, customLocs);
	}

	private void grabTileButtonActionPerformed(java.awt.event.ActionEvent evt)
	{
		customLocCenter = Player.getPosition();
		currentCenterTileLabel.setText(customLocCenter.toString());
	}

	private void customBankPathAddButtonActionPerformed(java.awt.event.ActionEvent evt)
	{
		if(!customBankPathModel.contains(Player.getPosition()))
			customBankPathModel.addElement(Player.getPosition());
	}

	private void customBankPathRemoveButtonActionPerformed(java.awt.event.ActionEvent evt)
	{
		customBankPathModel.removeElement(customLocBankPathList.getSelectedValue());
	}

	private void customBankPathClearButtonActionPerformed(java.awt.event.ActionEvent evt)
	{
		customBankPathModel.clear();
	}

	private void customLocLoadButtonActionPerformed(java.awt.event.ActionEvent evt)
	{
		CustomMiningLocation toLoad = (CustomMiningLocation)customLocationLoadBox.getSelectedItem();
		
		if(toLoad != null)
		{
			clearCustomLocTab();
			customLocNameTextBox.setText(toLoad.getName());
			currentCenterTileLabel.setText(toLoad.getCenterTile().toString());
			customLocCenter = toLoad.getCenterTile();
			customLocRadiusSpinner.setValue(toLoad.getRadius());
			isDepositBoxCheckBox.setSelected(toLoad.isDepositBox());
			helper.loadBankPath((DefaultListModel<Positionable>)customLocBankPathList.getModel(), Arrays.asList(toLoad.getBankPath()));
			helper.loadSupportedRocks(toLoad.getSupported(), rockCheckboxes);
		}
	}

	private void customLocDeleteButtonActionPerformed(java.awt.event.ActionEvent evt)
	{
		CustomMiningLocation toRemove = (CustomMiningLocation)customLocationLoadBox.getSelectedItem();
		if(toRemove != null)
		{
			helper.deleteCustomLocation(toRemove.getName());
			customLocationLoadBox.removeItem(toRemove);
			clearCustomLocTab();
			General.println("Deleted custom location: " + toRemove);
		}
	}

	private void customLocSaveButtonActionPerformed(java.awt.event.ActionEvent evt)
	{
		CustomMiningLocation custom = getCustomMiningLoc();
		if(custom.getName().length() > 0)
		{
			helper.saveCustomLocation(custom);
			helper.loadCustomLocations(customLocationLoadBox, customLocs);
			helper.setSelectedCustomLoc(custom.getName(), customLocationLoadBox);
			helper.loadAppropriateLocs((RockType)rockTypeComboBox.getSelectedItem(), locationComboBox, customLocs);
			General.println("Saved location: " + custom.getName());
		}
	}

	private void loadMissionsButtonActionPerformed(java.awt.event.ActionEvent evt)
	{
		MissionList toLoad = (MissionList)missionComboBox.getSelectedItem();
		
		for(int i = missionTable.getRowCount() - 1; i >= 0; i--)
			((DefaultTableModel)missionTable.getModel()).removeRow(i);
		
		missions.clear();
		
		if(toLoad != null)
		{
			for(Mission m : toLoad.getMissions())
			{
				FCMining miningMission = (FCMining)m;
				((DefaultTableModel)missionTable.getModel()).addRow(getTableInfo(miningMission));
				missions.add(m);
			}
		}
	}

	private void missionSaveButtonActionPerformed(java.awt.event.ActionEvent evt)
	{
		MissionList toSave = new MissionList(missionProfileName.getText(), missions);
		helper.saveMissionList(toSave);
		helper.loadMissionLists(missionComboBox);
		helper.setSelectedMission(toSave.getName(), missionComboBox);
		General.println("Saved profile: " + toSave);
	} 
	
	 private void missionDeleteButtonActionPerformed(java.awt.event.ActionEvent evt) 
	 {                      
		 MissionList toRemove = (MissionList)missionComboBox.getSelectedItem();
		 helper.deleteMission(toRemove.getName());
		 helper.loadMissionLists(missionComboBox);
		 General.println("Deleted profile: " + toRemove);
	 }
	 
	 private void removeMissionButtonActionPerformed(java.awt.event.ActionEvent evt) 
	 {   
		 int selected = missionTable.getSelectedRow();
		 if(selected != -1)
		 {
			 ((DefaultTableModel)missionTable.getModel()).removeRow(selected);
			 missions.remove(selected);
		 }
	 }    
	 
	 private void startButtonActionPerformed(java.awt.event.ActionEvent evt) 
	 {           
		 script.getSetMissions().clear();
		 hasFilledOut = true;
		 for(Mission mission : missions)
		 {
			 FCMining m = (FCMining)mission;
			 if(m.getGoals() != null)
				 m.getGoals().resetTimeGoals();
			 script.getSetMissions().add(new FCMining(script, m.isUsingAbc, m.isUpgradingPick, m.hopSettings, m.rock, m.mode, m.location, 
					 m.progressionType, m.getGoals().toArray(new Goal[m.getGoals().size()])));
		 }
		 dispose();
	 }  
	 
	 private CustomMiningLocation getCustomMiningLoc()
	 {
		 	String name = customLocNameTextBox.getText();
		 	boolean isDepositBox = isDepositBoxCheckBox.isSelected();
			int radius = (int)customLocRadiusSpinner.getValue();
			SerializablePositionable center = new SerializablePositionable(customLocCenter.getPosition().getX(), 
					customLocCenter.getPosition().getY(), customLocCenter.getPosition().getPlane());
			SerializablePositionable[] bankPath = helper.convertBankPath(customLocBankPathList.getModel());
			RockType[] supportedRocks = helper.convertSupportedRocks(rockCheckboxes);
			
			return new CustomMiningLocation(name, isDepositBox, center, radius, bankPath, supportedRocks);
	 }
	 
	 private void clearCustomLocTab()
	 {
		 customLocEnabledBox.setSelected(false);
		 customLocNameTextBox.setText("");
		 customLocRadiusSpinner.setValue(1);
		 ((DefaultListModel<Positionable>)customLocBankPathList.getModel()).clear();
		 for(JCheckBox box : rockCheckboxes)
			 box.setSelected(false);	 
	 }
	 
	 private Goal[] getGoals()
	 {
		 List<Goal> goals = new ArrayList<>();
		 
		 if(miningLevelCheckBox.isSelected())
			 goals.add(new SkillGoal(SKILLS.MINING, (int)miningLevelSpinner.getValue()));
		 if(timeElapsedCheckBox.isSelected())
		 {
			 String time = timeElapsedTextField.getText();
			 long ms = helper.parseTime(time);
			 goals.add(new TimeGoal(ms));
		 }
		 if(oreGainedCheckBox.isSelected())
		 {
			 if((RockType)rockTypeComboBox.getSelectedItem() == RockType.AUTO_SELECT)
				 goals.add(new ResourceGoal((int)oreGainedSpinner.getValue(), RockType.getAllItemIds()));
			 else
				 goals.add(new ResourceGoal((int)oreGainedSpinner.getValue(), ((RockType)rockTypeComboBox.getSelectedItem()).getItemId()));
		 }
		 if(infiniteCheckBox.isSelected())
			 goals.add(new InfiniteGoal());
		 
		 return goals.toArray(new Goal[goals.size()]);
	 }
	 
	 private Object[] getTableInfo(FCMining mission)
	 {
		 return new Object[]{mission.location == null ? "Auto-select" : mission.location.getName(), 
					mission.rock == null ? "Auto-select" : mission.rock, mission.getGoals()};
	 }
    
	 private javax.swing.JCheckBox abcl10CheckBox;
	    private javax.swing.JButton addMissionButton;
	    private javax.swing.JRadioButton afterMuleButton;
	    private javax.swing.JRadioButton bankOreButton;
	    private javax.swing.JRadioButton beforeMuleButton;
	    private javax.swing.ButtonGroup buttonGroup1;
	    private javax.swing.ButtonGroup buttonGroup2;
	    private javax.swing.ButtonGroup buttonGroup3;
	    private javax.swing.JPanel contentPanel;
	    private javax.swing.JLabel currentCenterTileLabel;
	    private javax.swing.JButton customBankPathAddButton;
	    private javax.swing.JButton customBankPathClearButton;
	    private javax.swing.JButton customBankPathRemoveButton;
	    private javax.swing.JCheckBox customLocAdamantite;
	    private javax.swing.JLabel customLocBankPathLabel;
	    private javax.swing.JList<Positionable> customLocBankPathList;
	    private javax.swing.JLabel customLocCenterLabel;
	    private javax.swing.JCheckBox customLocClay;
	    private javax.swing.JCheckBox customLocCoal;
	    private javax.swing.JCheckBox customLocCopper;
	    private javax.swing.JButton customLocDeleteButton;
	    private javax.swing.JCheckBox customLocEnabledBox;
	    private javax.swing.JCheckBox customLocGold;
	    private javax.swing.JCheckBox customLocIron;
	    private javax.swing.JButton customLocLoadButton;
	    private javax.swing.JCheckBox customLocMithril;
	    private javax.swing.JLabel customLocNameLabel;
	    private javax.swing.JTextField customLocNameTextBox;
	    private javax.swing.JLabel customLocRadiusLabel;
	    private javax.swing.JSpinner customLocRadiusSpinner;
	    private javax.swing.JCheckBox customLocRunite;
	    private javax.swing.JButton customLocSaveButton;
	    private javax.swing.JCheckBox customLocSilver;
	    private javax.swing.JLabel customLocSupportedRocksLabel;
	    private javax.swing.JPanel customLocTab;
	    private javax.swing.JCheckBox customLocTin;
	    private javax.swing.JComboBox<CustomMiningLocation> customLocationLoadBox;
	    private javax.swing.JRadioButton dropInventoryButton;
	    private javax.swing.JRadioButton experienceButton;
	    private javax.swing.JButton finalizeMissionButton;
	    private javax.swing.JPanel goalsPanel;
	    private javax.swing.JTextArea goalsTextArea;
	    private javax.swing.JButton grabTileButton;
	    private javax.swing.JCheckBox infiniteCheckBox;
	    private javax.swing.JCheckBox isDepositBoxCheckBox;
	    private javax.swing.JScrollPane jScrollPane1;
	    private javax.swing.JScrollPane jScrollPane2;
	    private javax.swing.JScrollPane jScrollPane3;
	    private javax.swing.JScrollPane jScrollPane4;
	    private javax.swing.JScrollPane jScrollPane5;
	    private javax.swing.JTextArea jTextArea1;
	    private javax.swing.JButton loadMissionsButton;
	    private javax.swing.JComboBox<MiningLocation> locationComboBox;
	    private javax.swing.JLabel locationLabel;
	    private javax.swing.JRadioButton m1d1Button;
	    private javax.swing.JCheckBox miningLevelCheckBox;
	    private javax.swing.JSpinner miningLevelSpinner;
	    private javax.swing.JComboBox<MissionList> missionComboBox;
	    private javax.swing.JButton missionDeleteButton;
	    private javax.swing.JFrame missionFrame;
	    private javax.swing.JTextField missionProfileName;
	    private javax.swing.JButton missionSaveButton;
	    private javax.swing.JTabbedPane missionSettingsPane;
	    private javax.swing.JTable missionTable;
	    private javax.swing.JLabel modeLabel;
	    private javax.swing.JRadioButton moneyButton;
	    private javax.swing.JCheckBox muleEnabledBox;
	    private javax.swing.JPanel muleTab;
	    private javax.swing.JTextField muleUsernameInput;
	    private javax.swing.JLabel muleUsernameLabel;
	    private javax.swing.JCheckBox noOreAvailableCheckBox;
	    private javax.swing.JCheckBox oreGainedCheckBox;
	    private javax.swing.JSpinner oreGainedSpinner;
	    private javax.swing.JCheckBox oreStolenCheckBox;
	    private javax.swing.JSpinner oreStolenSpinner;
	    private javax.swing.JCheckBox playersInAreaCheckBox;
	    private javax.swing.JSpinner playersInAreaSpinner;
	    private javax.swing.JLabel progressionLabel;
	    private javax.swing.JButton removeMissionButton;
	    private javax.swing.JComboBox<RockType> rockTypeComboBox;
	    private javax.swing.JLabel rockTypeLabel;
	    private javax.swing.JPanel settingsTab;
	    private javax.swing.JButton startButton;
	    private javax.swing.JCheckBox timeElapsedCheckBox;
	    private javax.swing.JTextField timeElapsedTextField;
	    private javax.swing.JLabel titleLabel;
	    private javax.swing.JLabel withdrawOreFromBankLabel;
	    private javax.swing.JCheckBox worldHoppingEnabledCheckBox;
	    private javax.swing.JPanel worldHoppingTab;
	    private javax.swing.JTextArea worldHoppingTextArea;
	    private javax.swing.JCheckBox pickUpgradingCheckBox;
}
