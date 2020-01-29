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

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jdesktop.swingx.VerticalLayout;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import ca.hedlund.jpraat.binding.fon.kSound_windowShape;
import ca.phon.ui.action.PhonUIAction;

public class SpectralMomentsSettingsPanel extends JPanel {
	
	private static final long serialVersionUID = -5900183536297104022L;

	private JComboBox<kSound_windowShape> windowShapeBox;
	
	private JFormattedTextField filterStartField;
	
	private JFormattedTextField filterEndField;
	
	private JFormattedTextField filterSmoothingField;
	
	private JCheckBox usePreemphasisBox;
	
	private JFormattedTextField preempFromField;
	
	private JButton saveDefaultsButton;
	
	private JButton loadDefaultsButton;
	
	private JButton loadStandardsButton;

	public SpectralMomentsSettingsPanel() {
		super();
		
		init();
	}
	
	public SpectralMomentsSettingsPanel(SpectralMomentsSettings settings) {
		super();
		init();
		loadSettings(settings);
	}
	
	private void init() {
		final FormLayout layout = new FormLayout(
				"right:pref, 3dlu, fill:pref:grow, pref",
				"pref, 3dlu, pref, 3dlu, pref, 3dlu, pref, 3dlu, pref, 3dlu, pref");
		final JPanel formPanel = new JPanel(layout);
		final CellConstraints cc = new CellConstraints();
		
		final NumberFormat numberFormat = NumberFormat.getNumberInstance();
		
		windowShapeBox = new JComboBox<>(kSound_windowShape.values());
		formPanel.add(new JLabel("Window shape:"), cc.xy(1, 1));
		formPanel.add(windowShapeBox, cc.xy(3,  1));
		
		filterStartField = new JFormattedTextField(numberFormat);
		formPanel.add(new JLabel("Bandpass filter start:"), cc.xy(1, 3));
		formPanel.add(filterStartField, cc.xy(3, 3));
		formPanel.add(new JLabel("Hz"), cc.xy(4, 3));
		
		filterEndField = new JFormattedTextField(numberFormat);
		formPanel.add(new JLabel("Bandpass filter end:"), cc.xy(1, 5));
		formPanel.add(filterEndField, cc.xy(3, 5));
		formPanel.add(new JLabel("Hz"), cc.xy(4, 5));
		
		filterSmoothingField = new JFormattedTextField(numberFormat);
		formPanel.add(new JLabel("Smoothing amount:"), cc.xy(1, 7));
		formPanel.add(filterSmoothingField, cc.xy(3, 7));
		formPanel.add(new JLabel("Hz"), cc.xy(4, 7));
		
		usePreemphasisBox = new JCheckBox("Use Pre-emphasis");
		formPanel.add(usePreemphasisBox, cc.xyw(1, 9, 3));
		
		preempFromField = new JFormattedTextField(numberFormat);
		formPanel.add(new JLabel("Pre-emphasis start:"), cc.xy(1, 11));
		formPanel.add(preempFromField, cc.xy(3, 11));
		formPanel.add(new JLabel("Hz"), cc.xy(4, 11));
		
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
		
		setLayout(new BorderLayout());
		add(formPanel, BorderLayout.CENTER);
		add(btnPanel, BorderLayout.EAST);
		
		loadSettings(new SpectralMomentsSettings());
	}
	
	public void loadSettings(SpectralMomentsSettings settings) {
		windowShapeBox.setSelectedItem(settings.getWindowShape());
		filterStartField.setValue(settings.getFilterStart());
		filterEndField.setValue(settings.getFilterEnd());
		filterSmoothingField.setValue(settings.getFilterSmoothing());
		usePreemphasisBox.setSelected(settings.isUsePreemphasis());
		preempFromField.setValue(settings.getPreempFrom());
	}
	
	public SpectralMomentsSettings getSettings() {
		final SpectralMomentsSettings retVal = new SpectralMomentsSettings();
		
		retVal.setWindowShape((kSound_windowShape)windowShapeBox.getSelectedItem());
		retVal.setFilterStart((double)filterStartField.getValue());
		retVal.setFilterEnd((double)filterEndField.getValue());
		retVal.setFilterSmoothing((double)filterSmoothingField.getValue());
		retVal.setUsePreemphasis(usePreemphasisBox.isSelected());
		retVal.setPreempFrom((double)preempFromField.getValue());
		
		return retVal;
	}
	
	public void saveSettingsAsDefaults() {
		getSettings().saveAsDefaults();
	}
	
	public void loadStandards() {
		final SpectralMomentsSettings settings = new SpectralMomentsSettings();
		settings.loadStandards();
		loadSettings(settings);
	}
	
	public void loadDefaults() {
		loadSettings(new SpectralMomentsSettings());
	}
	
}
