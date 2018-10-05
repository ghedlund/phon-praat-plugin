/*
 * Copyright (C) 2012-2018 Gregory Hedlund
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 *    http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ca.phon.plugins.praat;

import java.awt.BorderLayout;
import java.text.NumberFormat;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import org.jdesktop.swingx.VerticalLayout;

import ca.hedlund.jpraat.binding.fon.Intensity;
import ca.phon.ui.action.PhonUIAction;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class IntensitySettingsPanel extends JPanel {

	private static final long serialVersionUID = -1489628359030275475L;
	
	private JFormattedTextField minRangeField;
	
	private JFormattedTextField maxRangeField;
	
	private JRadioButton medianBox;
	
	private JRadioButton meanEnergyBox;
	
	private JRadioButton meanSonesBox;
	
	private JRadioButton meanDbBox;
	
	private JCheckBox subtractMeanBox;
	
	private JButton saveDefaultsButton;
	
	private JButton loadDefaultsButton;
	
	private JButton loadStandardsButton;
	
	public IntensitySettingsPanel() {
		this(new IntensitySettings());
	}
	
	public IntensitySettingsPanel(IntensitySettings settings) {
		super();
		init();
		loadSettings(settings);
	}
	
	private void init() {
		setLayout(new BorderLayout());
		
		final FormLayout formLayout = new FormLayout(
				"right:pref, 3dlu, fill:pref:grow, pref", "");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(formLayout);
		
		final NumberFormat numberFormat = NumberFormat.getNumberInstance();
		int rowIdx = 1;
		
		minRangeField = new JFormattedTextField(numberFormat);
		builder.appendRow("pref");
		builder.add(new JLabel("View range start"), cc.xy(1, rowIdx));
		builder.add(minRangeField, cc.xy(3, rowIdx));
		builder.add(new JLabel("(dB)"), cc.xy(4, rowIdx));
		builder.appendRow("3dlu");
		rowIdx += 2;
		
		maxRangeField = new JFormattedTextField(numberFormat);
		builder.appendRow("pref");
		builder.add(new JLabel("View range end"), cc.xy(1, rowIdx));
		builder.add(maxRangeField, cc.xy(3, rowIdx));
		builder.add(new JLabel("(dB)"), cc.xy(4, rowIdx));
		builder.appendRow("3dlu");
		rowIdx += 2;
		
		final ButtonGroup btnGrp = new ButtonGroup();
		medianBox = new JRadioButton("median");
		btnGrp.add(medianBox);
		meanEnergyBox = new JRadioButton("mean energy");
		btnGrp.add(meanEnergyBox);
		meanSonesBox = new JRadioButton("mean sones");
		btnGrp.add(meanSonesBox);
		meanDbBox = new JRadioButton("mean dB");
		btnGrp.add(meanDbBox);
		
		builder.appendRow("pref");
		builder.add(new JLabel("Averaging method"), cc.xy(1, rowIdx));
		builder.add(medianBox, cc.xy(3, rowIdx++));
		builder.appendRow("pref");
		builder.add(meanEnergyBox, cc.xy(3, rowIdx++));
		builder.appendRow("pref");
		builder.add(meanSonesBox, cc.xy(3, rowIdx++));
		builder.appendRow("pref");
		builder.add(meanDbBox, cc.xy(3, rowIdx++));
		builder.appendRow("3dlu");
		rowIdx++;
		
		subtractMeanBox = new JCheckBox("Subtract mean pressure");
		
		builder.appendRow("pref");
		builder.add(subtractMeanBox, cc.xy(3, rowIdx++));
		
		add(builder.getPanel(), BorderLayout.CENTER);
		
		final JPanel btnPanel = new JPanel(new VerticalLayout());
		
		final PhonUIAction loadDefaultsAct = new PhonUIAction(this, "loadDefaults");
		loadDefaultsAct.putValue(PhonUIAction.NAME, "Load defaults");
		loadDefaultsButton = new JButton(loadDefaultsAct);
		btnPanel.add(loadDefaultsButton);
		
		final PhonUIAction loadStandardsAct = new PhonUIAction(this, "loadStandards");
		loadStandardsAct.putValue(PhonUIAction.NAME, "Load standards");
		loadStandardsButton = new JButton(loadStandardsAct);
		btnPanel.add(loadStandardsButton);
		
		final PhonUIAction saveDefaultsAct = new PhonUIAction(this, "saveSettingsAsDefaults");
		saveDefaultsAct.putValue(PhonUIAction.NAME, "Save as defaults");
		saveDefaultsButton = new JButton(saveDefaultsAct);
		btnPanel.add(saveDefaultsButton);
		
		add(btnPanel, BorderLayout.EAST);
	}
	
	public void loadSettings(IntensitySettings settings) {
		minRangeField.setValue(settings.getViewRangeMin());
		maxRangeField.setValue(settings.getViewRangeMax());
		
		if(settings.getAveraging() == Intensity.AVERAGING_MEDIAN)
			medianBox.setSelected(true);
		else if(settings.getAveraging() == Intensity.AVERAGING_ENERGY)
			meanEnergyBox.setSelected(true);
		else if(settings.getAveraging() == Intensity.AVERAGING_SONES)
			meanSonesBox.setSelected(true);
		else if(settings.getAveraging() == Intensity.AVERAGING_DB)
			meanDbBox.setSelected(true);
		
		subtractMeanBox.setSelected(settings.getSubtractMean());
	}
	
	public IntensitySettings getSettings() {
		final IntensitySettings retVal = new IntensitySettings();
		
		final double viewRangeMin = ((Number)minRangeField.getValue()).doubleValue();
		retVal.setViewRangeMin(viewRangeMin);
		
		final double viewRangeMax = ((Number)maxRangeField.getValue()).doubleValue();
		retVal.setViewRangeMax(viewRangeMax);
		
		if(medianBox.isSelected())
			retVal.setAveraging(Intensity.AVERAGING_MEDIAN);
		else if(meanEnergyBox.isSelected())
			retVal.setAveraging(Intensity.AVERAGING_ENERGY);
		else if(meanSonesBox.isSelected())
			retVal.setAveraging(Intensity.AVERAGING_SONES);
		else if(meanDbBox.isSelected())
			retVal.setAveraging(Intensity.AVERAGING_DB);
		
		retVal.setSubtractMean(subtractMeanBox.isSelected());
		
		return retVal;
	}
	
	public void saveSettingsWithDefaults() {
		getSettings().saveAsDefaults();
	}
	
	public void loadStandards() {
		final IntensitySettings settings = new IntensitySettings();
		settings.loadStandards();
		loadSettings(settings);
	}

	public void loadDefaults() {
		loadSettings(new IntensitySettings());
	}
	
}
