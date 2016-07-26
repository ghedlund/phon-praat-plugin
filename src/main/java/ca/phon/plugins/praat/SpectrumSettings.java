package ca.phon.plugins.praat;

import java.util.prefs.Preferences;

import ca.hedlund.jpraat.binding.fon.kSound_windowShape;
import ca.phon.util.PrefHelper;

public class SpectrumSettings {
	
	private final kSound_windowShape DEFAULT_WINDOW_SHAPE = kSound_windowShape.KAISER_2;
	private final static String WINDOW_SHAPE_PROP = SpectrumSettings.class.getName() + ".windowShape";
	private kSound_windowShape windowShape = getDefaultWindowShape();
	
	public kSound_windowShape getDefaultWindowShape() {
		return kSound_windowShape.valueOf(
				PrefHelper.get(WINDOW_SHAPE_PROP, DEFAULT_WINDOW_SHAPE.toString()));
	}
	
	public kSound_windowShape getWindowShape() {
		return this.windowShape;
	}
	
	public void setWindowShape(kSound_windowShape windowShape) {
		this.windowShape = windowShape;
	}
	
	private final static double DEFAULT_FILTER_START = 500;
	private final static String FILTER_START_PROP = SpectrumSettings.class.getName() + ".filterStart";
	private double filterStart = getDefaultFilterStart();
	
	public double getDefaultFilterStart() {
		return PrefHelper.getDouble(FILTER_START_PROP, DEFAULT_FILTER_START);
	}
	
	public double getFilterStart() {
		return this.filterStart;
	}
	
	public void setFilterStart(double filterStart) {
		this.filterStart = filterStart;
	}
	
	private final static double DEFAULT_FILTER_END = 15000;
	private final static String FILTER_END_PROP = SpectrumSettings.class.getName() + ".filterEnd";
	private double filterEnd = getDefaultFilterEnd();
	
	public double getDefaultFilterEnd() {
		return PrefHelper.getDouble(FILTER_END_PROP, DEFAULT_FILTER_END);
	}
	
	public double getFilterEnd() {
		return this.filterEnd;
	}
	
	public void setFilterEnd(double filterEnd) {
		this.filterEnd = filterEnd;
	}
	
	private final static double DEFAULT_FILTER_SMOOTHING = 100;
	private final static String FILTER_SMOOTHING_PROP = SpectrumSettings.class.getName() + ".filterSmoothing";
	private double filterSmoothing = getDefaultFilterSmoothing();
	
	public double getDefaultFilterSmoothing() {
		return PrefHelper.getDouble(FILTER_SMOOTHING_PROP, DEFAULT_FILTER_SMOOTHING);
	}
	
	public double getFilterSmoothing() {
		return this.filterSmoothing;
	}
	
	public void setFilterSmoothing(double filterSmoothing) {
		this.filterSmoothing = filterSmoothing;
	}
	
	private final boolean DEFAULT_USE_PREEMPHASIS = true;
	private final static String USE_PREEMPHASIS_PROP = SpectrumSettings.class.getName() + ".usePreemphasis";
	private boolean usePreemphasis = getDefaultUsePreemphasis();
	
	public boolean getDefaultUsePreemphasis() {
		return PrefHelper.getBoolean(USE_PREEMPHASIS_PROP, DEFAULT_USE_PREEMPHASIS);
	}
	
	public boolean isUsePreemphasis() {
		return this.usePreemphasis;
	}
	
	public void setUsePreemphasis(boolean usePreemphasis) {
		this.usePreemphasis = usePreemphasis;
	}
	
	private final static double DEFAULT_PREEMP_FROM = 2000;
	private final static String PREEMP_FROM_PROP = SpectrumSettings.class.getName() + ".preempFrom";
	private double preempFrom = getDefaultPreempFrom();
	
	public double getDefaultPreempFrom() {
		return PrefHelper.getDouble(PREEMP_FROM_PROP, DEFAULT_PREEMP_FROM);
	}
	
	public double getPreempFrom() {
		return this.preempFrom;
	}
	
	public void setPreempFrom(double preempFrom) {
		this.preempFrom = preempFrom;
	}
	
	public void saveAsDefaults() {
		final Preferences prefs = PrefHelper.getUserPreferences();
		prefs.put(WINDOW_SHAPE_PROP, getWindowShape().toString());
		prefs.putDouble(FILTER_START_PROP, getFilterStart());
		prefs.putDouble(FILTER_END_PROP, getFilterEnd());
		prefs.putDouble(FILTER_SMOOTHING_PROP, getFilterSmoothing());
		prefs.putBoolean(USE_PREEMPHASIS_PROP, isUsePreemphasis());
		prefs.putDouble(PREEMP_FROM_PROP, getPreempFrom());
	}
	
	public void loadDefaults() {
		setWindowShape(getDefaultWindowShape());
		setFilterStart(getDefaultFilterStart());
		setFilterEnd(getDefaultFilterEnd());
		setFilterSmoothing(getDefaultFilterSmoothing());
		setUsePreemphasis(getDefaultUsePreemphasis());
		setPreempFrom(getDefaultPreempFrom());
	}
	
	public void loadStandards() {
		setWindowShape(DEFAULT_WINDOW_SHAPE);
		setFilterStart(DEFAULT_FILTER_START);
		setFilterEnd(DEFAULT_FILTER_END);
		setFilterSmoothing(DEFAULT_FILTER_SMOOTHING);
		setUsePreemphasis(DEFAULT_USE_PREEMPHASIS);
		setPreempFrom(DEFAULT_PREEMP_FROM);
	}

}
