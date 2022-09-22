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

import ca.phon.util.PrefHelper;

import java.util.prefs.Preferences;

public class FormantSettings {
	
	public static final String NUM_FORMANTS_PROP = FormantSettings.class.getName() + ".numFormants";
	public static final int DEFAULT_NUM_FORMANTS = 5;
	private int numFormants = getDefaultNumFormants();
	
	public static final String WINDOW_LENGTH_PROP = FormantSettings.class.getName() + ".windowLength";
	public static final double DEFAULT_WINDOW_LENGTH = 0.025;
	private double windowLength = getDefaultWindowLength();
	
	public static final String MAX_FREQ_PROP = FormantSettings.class.getName()  + ".maxFrequency";
	public static final double DEFAULT_MAX_FREQ = 5500.0;
	private double maxFrequency = getDefaultMaxFrequency();
	
	public static final String TIME_STEP_PROP = FormantSettings.class.getName() + ".timeStep";
	public static final double DEFAULT_TIME_STEP = 0.0;
	private double timeStep = getDefaultTimeStep();
	
	public static final String PREEPHASIS_PROP = FormantSettings.class.getName() + ".preEmphasis";
	public static final double DEFAULT_PREEMPHASIS = 50.0f;
	private double preEmphasis = getDefaultPreEmphasis();
	
	public static final String DYNAMIC_RANGE_PROP = FormantSettings.class.getName() + ".dynamicRange";
	public static final double DEFAULT_DYNAMIC_RANGE = 30.0;
	private double dynamicRange = getDefaultDynamicRange();
	
	public static final String DOT_SIZE_PROP = FormantSettings.class.getName() + ".dotSize";
	public static final double DEFAULT_DOT_SIZE = 1.0;
	private double dotSize = getDefaultDotSize();
	
	public static final String INCLUDE_INTENSITY_PROP = FormantSettings.class.getName() + ".includeIntensity";
	public static final boolean DEFAULT_INCLUDE_INTENSITY = false;
	private boolean includeIntensity = getDefaultIncludeIntensity();
	
	public static final String INCLUDE_BANDWIDTHS_PROP = FormantSettings.class.getName() + ".includeBandwidths";
	public static final boolean DEFAULT_INCLUDE_BANDWIDTHS = false;
	private boolean includeBandwidths = getDefaultIncludeBandwidths();
	
	public static final String INCLUDE_NUMFORMANTS_PROP = FormantSettings.class.getName() + ".includeNumFormants";
	public static final boolean DEFAULT_INCLUDE_NUMFORMANTS = false;
	private boolean includeNumFormants = getDefaultIncludeNumFormants();
	
	public static double getDefaultDotSize() {
		return PrefHelper.getDouble(DOT_SIZE_PROP, DEFAULT_DOT_SIZE);
	}
	
	public static int getDefaultNumFormants() {
		return PrefHelper.getInt(NUM_FORMANTS_PROP, DEFAULT_NUM_FORMANTS);
	}
	
	public static double getDefaultWindowLength() {
		return PrefHelper.getDouble(WINDOW_LENGTH_PROP, DEFAULT_WINDOW_LENGTH);
	}
	
	public static double getDefaultMaxFrequency() {
		return PrefHelper.getDouble(MAX_FREQ_PROP, DEFAULT_MAX_FREQ);
	}
	
	public static double getDefaultTimeStep() {
		return PrefHelper.getDouble(TIME_STEP_PROP, DEFAULT_TIME_STEP);
	}
	
	public static double getDefaultPreEmphasis() {
		return PrefHelper.getDouble(PREEPHASIS_PROP, DEFAULT_PREEMPHASIS);
	}
	
	public static boolean getDefaultIncludeIntensity() {
		return PrefHelper.getBoolean(INCLUDE_INTENSITY_PROP, DEFAULT_INCLUDE_INTENSITY);
	}
	
	public static boolean getDefaultIncludeBandwidths() {
		return PrefHelper.getBoolean(INCLUDE_BANDWIDTHS_PROP, DEFAULT_INCLUDE_BANDWIDTHS);
	}
	
	public static boolean getDefaultIncludeNumFormants() {
		return PrefHelper.getBoolean(INCLUDE_NUMFORMANTS_PROP, DEFAULT_INCLUDE_NUMFORMANTS);
	}
	
	public static double getDefaultDynamicRange() {
		return PrefHelper.getDouble(DYNAMIC_RANGE_PROP, DEFAULT_DYNAMIC_RANGE);
	}

	public int getNumFormants() {
		return numFormants;
	}

	public void setNumFormants(int numFormants) {
		this.numFormants = numFormants;
	}

	public double getWindowLength() {
		return windowLength;
	}

	public void setWindowLength(double windowLength) {
		this.windowLength = windowLength;
	}

	public double getMaxFrequency() {
		return maxFrequency;
	}

	public void setMaxFrequency(double maxFrequency) {
		this.maxFrequency = maxFrequency;
	}

	public double getTimeStep() {
		return timeStep;
	}

	public void setTimeStep(double timeStep) {
		this.timeStep = timeStep;
	}

	public double getPreEmphasis() {
		return preEmphasis;
	}

	public void setPreEmphasis(double preEmphasis) {
		this.preEmphasis = preEmphasis;
	}
	
	public boolean isIncludeNumFormants() {
		return this.includeNumFormants;
	}
	
	public void setIncludeNumFormants(boolean v) {
		this.includeNumFormants = v;
	}
	
	public boolean isIncludeIntensity() {
		return this.includeIntensity;
	}
	
	public void setIncludeIntensity(boolean v) {
		this.includeIntensity = v;
	}
	
	public boolean isIncludeBandwidths() {
		return this.includeBandwidths;
	}
	
	public void setIncludeBandwidths(boolean v) {
		this.includeBandwidths = v;
	}
	
	public double getDynamicRange() {
		return dynamicRange;
	}

	public void setDynamicRange(double dynamicRange) {
		this.dynamicRange = dynamicRange;
	}

	public double getDotSize() {
		return dotSize;
	}

	public void setDotSize(double dotSize) {
		this.dotSize = dotSize;
	}

	public void loadDefaults() {
		setNumFormants(getDefaultNumFormants());
		setMaxFrequency(getDefaultMaxFrequency());
		setTimeStep(getDefaultTimeStep());
		setPreEmphasis(getDefaultPreEmphasis());
		setWindowLength(getDefaultWindowLength());
		setDynamicRange(getDefaultDynamicRange());
		setDotSize(getDefaultDotSize());
		setIncludeBandwidths(getDefaultIncludeBandwidths());
		setIncludeNumFormants(getDefaultIncludeNumFormants());
		setIncludeIntensity(getDefaultIncludeIntensity());
	}
	
	public void loadStandards() {
		setNumFormants(DEFAULT_NUM_FORMANTS);
		setMaxFrequency(DEFAULT_MAX_FREQ);
		setTimeStep(DEFAULT_TIME_STEP);
		setPreEmphasis(DEFAULT_PREEMPHASIS);
		setWindowLength(DEFAULT_WINDOW_LENGTH);
		setDynamicRange(DEFAULT_DYNAMIC_RANGE);
		setDotSize(DEFAULT_DOT_SIZE);
		setIncludeBandwidths(DEFAULT_INCLUDE_BANDWIDTHS);
		setIncludeNumFormants(DEFAULT_INCLUDE_NUMFORMANTS);
		setIncludeIntensity(DEFAULT_INCLUDE_INTENSITY);
	}
	
	public void saveToPreferences(Preferences prefs) {
		prefs.putInt(NUM_FORMANTS_PROP, getNumFormants());
		prefs.putDouble(MAX_FREQ_PROP, getMaxFrequency());
		prefs.putDouble(TIME_STEP_PROP, getTimeStep());
		prefs.putDouble(PREEPHASIS_PROP, getPreEmphasis());
		prefs.putDouble(WINDOW_LENGTH_PROP, getWindowLength());
		prefs.putDouble(DYNAMIC_RANGE_PROP, getDynamicRange());
		prefs.putDouble(DOT_SIZE_PROP, getDotSize());
		prefs.putBoolean(INCLUDE_NUMFORMANTS_PROP, isIncludeNumFormants());
		prefs.putBoolean(INCLUDE_INTENSITY_PROP, isIncludeIntensity());
		prefs.putBoolean(INCLUDE_BANDWIDTHS_PROP, isIncludeBandwidths());
	}
	
	public void saveAsDefaults() {
		final Preferences prefs = PrefHelper.getUserPreferences();
		saveToPreferences(prefs);
	}
	
}
