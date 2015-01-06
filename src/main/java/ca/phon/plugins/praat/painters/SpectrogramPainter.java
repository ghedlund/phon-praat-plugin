package ca.phon.plugins.praat.painters;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.text.NumberFormat;

import ca.hedlund.jpraat.binding.fon.Spectrogram;
import ca.phon.plugins.praat.ColorMap;
import ca.phon.plugins.praat.SpectrogramSettings;
import ca.phon.ui.painter.BufferedPainter;

/**
 * Spectrogram painter.
 *
 */
public class SpectrogramPainter extends BufferedPainter<Spectrogram> implements PraatPainter<Spectrogram> {
	
	private final static double NUMln2 = 0.6931471805599453094172321214581765680755;
	private final static double NUMln10 = 2.3025850929940456840179914546843642076011;
	
	private SpectrogramSettings settings;
	
	private ColorMap colorMap = ColorMap.getGreyscale(255);
	
	public SpectrogramPainter() {
		this(new SpectrogramSettings());
	}
	
	public SpectrogramPainter(SpectrogramSettings settings) {
		super();
		setResizeMode(ResizeMode.SCALE_ON_RESIZE);
		this.settings = settings;
	}
	
	public SpectrogramSettings getSettings() {
		return settings;
	}

	public void setSettings(SpectrogramSettings settings) {
		this.settings = settings;
	}

	public ColorMap getColorMap() {
		return colorMap;
	}

	public void setColorMap(ColorMap colorMap) {
		this.colorMap = colorMap;
	}
	
	private double preEmphasis(double dy, int ifreq) {
		return (settings.getPreEmphasis() / NUMln2) * Math.log(ifreq * dy / 1000.0);
	}
	
	private double dbValue(double preemphasis, double value) {
		return (10.0/NUMln10) * Math.log((value + 1e-30) / 4.0e-10) + preemphasis;  // dB
	}

	@Override
	public void paintGarnish(Spectrogram spectrogram, Graphics2D g2d, Rectangle2D bounds, int location) {
		final double startFreq = 0;
		final double endFreq = settings.getMaxFrequency();
		
		// draw start freq
		g2d.setColor(Color.black);
		final FontMetrics fm = g2d.getFontMetrics();
		
		final NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setGroupingUsed(false);
		nf.setMinimumFractionDigits(1);
		nf.setMaximumFractionDigits(1);
		
		final String startFreqTxt = nf.format(startFreq) + " Hz";
		final Rectangle2D startBounds = fm.getStringBounds(startFreqTxt, g2d);
		int y = 
				(int)Math.round((bounds.getY() + bounds.getHeight())) - fm.getDescent();
		int x = 
				(int)Math.round((bounds.getX() + bounds.getWidth()) - startBounds.getWidth());
		g2d.drawString(startFreqTxt, x, y);
		
		final String endFreqTxt = nf.format(endFreq) + " Hz";
		final Rectangle2D endBounds = fm.getStringBounds(endFreqTxt, g2d);
		y = 
				(int)Math.round(bounds.getY() + endBounds.getHeight()) - fm.getDescent();
		x = 
				(int)Math.round((bounds.getX() + bounds.getWidth()) - endBounds.getWidth());
		g2d.drawString(endFreqTxt, x, y);
	}
	
	@Override
	protected void paintBuffer(Spectrogram spectrogram, Graphics2D g2d,
			Rectangle2D bounds) {
		g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		
		if(spectrogram == null) return;
		
		final int numFrames = (int)spectrogram.getNx();
    	final int numBins = (int)spectrogram.getNy();
    	final double[] preemphasisFactor = new double[numBins];
    	final double[] dynamicFactor = new double[numFrames];
    	
    	final double[][] dbData = new double[numFrames][];
    	
    	for(int ifreq = 0; ifreq < numBins; ifreq++) {
			preemphasisFactor[ifreq] = preEmphasis(spectrogram.getDy(), ifreq);
			for(int itime = 0; itime < numFrames; itime++) {
				if(dbData[itime] == null) {
					dbData[itime] = new double[numBins];
				}
				double value = spectrogram.getZ(itime+1, ifreq+1);
				value = dbValue(preemphasisFactor[ifreq], value);
				if(value > dynamicFactor[itime]) dynamicFactor[itime] = value;
				dbData[itime][ifreq] = value;
			}
    	}
    	
    	// autoscaling
    	double maximum = 0.0;
		for (int itime = 0; itime < numFrames; itime ++)
			if (dynamicFactor [itime] > maximum) maximum = dynamicFactor [itime];
		
		// dynamic compression
		for (int itime = 0; itime < numFrames; itime ++) {
			dynamicFactor [itime] = settings.getDynamicCompression() * (maximum - dynamicFactor [itime]);
			for (int ifreq = 0; ifreq < numBins; ifreq ++)
				dbData [itime] [ifreq] += dynamicFactor [itime];
		}
		
		double minIntensity = maximum - settings.getDynamicRange();
		
		double cellWidth = bounds.getWidth() / numFrames;
		double cellHeight = bounds.getHeight() / numBins;
		
		 double scaleFactor = (colorMap.size() / settings.getDynamicRange());
         for (int i = 0; i < numFrames; i++) {                
             for (int j = numBins-1; j >= 0; j--) {
             	double dataVal = dbData[i][j];
             	if(dataVal < minIntensity)
             		dataVal = minIntensity;
             	if(dataVal > maximum)
             		dataVal = maximum;
                 int grey = (int)Math.round( (dataVal - minIntensity) * scaleFactor);
                 if(grey >= colorMap.size())
                 	grey = colorMap.size()-1;
                 
                 int cVal = colorMap.getColor(grey);
                 
                 Color c = new Color(cVal >> 16 & 0xff, cVal >> 8 & 0xff, cVal & 0xff);
                 g2d.setColor(c);
                 
                 // TODO interpolate (i.e., calculate transparency of each cell)
                 
                 final Rectangle2D cellRect = new Rectangle2D.Double(
                		 bounds.getX() + i * cellWidth, (bounds.getY() + bounds.getHeight()) - ( (j+1) * cellHeight),
                		 cellWidth, cellHeight);
                 g2d.fill(cellRect);
             }
         }
	}
	
}
