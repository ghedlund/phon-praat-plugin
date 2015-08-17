/*
 * phon-textgrid-plugin
 * Copyright (C) 2015, Gregory Hedlund <ghedlund@mun.ca>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ca.phon.plugins.praat.painters;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.text.NumberFormat;
import java.util.concurrent.atomic.AtomicReference;

import ca.hedlund.jpraat.binding.fon.Function;
import ca.hedlund.jpraat.binding.fon.Pitch;
import ca.hedlund.jpraat.binding.fon.kPitch_unit;
import ca.hedlund.jpraat.binding.jna.Str32;
import ca.phon.plugins.praat.PitchSettings;
import ca.phon.ui.painter.BufferedPainter;

import com.sun.jna.WString;

public class PitchSpecklePainter extends BufferedPainter<Pitch> implements PraatPainter<Pitch> {
	
	private PitchSettings settings = new PitchSettings();
	
	public PitchSpecklePainter() {
		this(new PitchSettings());
	}
	
	public PitchSpecklePainter(PitchSettings settings) {
		super();
		super.setResizeMode(ResizeMode.REPAINT_ON_RESIZE);
		this.settings = settings;
	}
	
	public void setSettings(PitchSettings settings) {
		this.settings = settings;
	}
	
	public PitchSettings getSettings() {
		return this.settings;
	}
	
	protected void paintBuffer(Pitch pitch, Graphics2D g2d, Rectangle2D bounds) {
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		
		if(pitch == null) return;
		
		final double tmin = pitch.getXMin();
		final double tmax = pitch.getXMax();
		
		final double len = (tmax - tmin);
		if(len <= 0.0) return;
		
		double fmin = settings.getRangeStart();
		if(settings.getUnits().ordinal() != kPitch_unit.HERTZ.ordinal()) {
			fmin = pitch.convertStandardToSpecialUnit(fmin, Pitch.LEVEL_FREQUENCY,
					settings.getUnits().ordinal());
		}
		
		double fmax = settings.getRangeEnd();
		if(settings.getUnits().ordinal() > kPitch_unit.HERTZ.ordinal()) {
			fmax = pitch.convertStandardToSpecialUnit(fmax, Pitch.LEVEL_FREQUENCY,
					settings.getUnits().ordinal());
		}
		double range = Math.abs(fmax - fmin);
		
		final double unitsPerPixel = range / bounds.getHeight();
		final double pixelPerSec = bounds.getWidth() / len;
		
		final AtomicReference<Long> iminRef = new AtomicReference<Long>();
		final AtomicReference<Long> imaxRef = new AtomicReference<Long>();
		
		pitch.getWindowSamples(tmin, tmax, iminRef, imaxRef);
		
		final int firstFrame = iminRef.get().intValue();
		final int lastFrame = imaxRef.get().intValue();
		
		final double radius =
				Math.ceil(0.5 * settings.getDotSize() * Toolkit.getDefaultToolkit().getScreenResolution() / 25.4);
		
		for(int i = firstFrame; i <= lastFrame; i++) {
			double time = pitch.indexToX(i);
			double v = pitch.getValueAtSample(i, Pitch.LEVEL_FREQUENCY, settings.getUnits().ordinal());
			if(Double.isInfinite(v) || Double.isNaN(v) ) continue;
			if(!pitch.isUnitLogarithmic(Pitch.LEVEL_FREQUENCY, settings.getUnits().ordinal()))
				v = pitch.convertToNonlogarithmic(v, Pitch.LEVEL_FREQUENCY, settings.getUnits().ordinal());
			
			double x = bounds.getX() + ((time - tmin) * pixelPerSec);
			double y = (bounds.getY() + bounds.getHeight()) - ((v - fmin) / unitsPerPixel);
			
			final Ellipse2D circle = new Ellipse2D.Double();
			circle.setFrameFromCenter(x, y, x + radius, y + radius);
			
			g2d.setColor(Color.cyan);
			g2d.fill(circle);

			circle.setFrameFromCenter(x, y, x + (radius - 1), y + (radius - 1));
			g2d.setColor(Color.blue);
			g2d.fill(circle);
		}
	}

	@Override
	public void paintGarnish(Pitch pitch, Graphics2D g2d, Rectangle2D bounds, int location) {
		final Str32 unitText = pitch.getUnitText(Pitch.LEVEL_FREQUENCY, 
				settings.getUnits(), Function.UNIT_TEXT_SHORT);
		final double startValue = 
				pitch.convertStandardToSpecialUnit(settings.getRangeStart(), Pitch.LEVEL_FREQUENCY, 
						settings.getUnits().ordinal());
		final double endValue =
				pitch.convertStandardToSpecialUnit(settings.getRangeEnd(), Pitch.LEVEL_FREQUENCY,
						settings.getUnits().ordinal());
		
		final NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMinimumFractionDigits(1);
		nf.setMaximumFractionDigits(1);
		nf.setGroupingUsed(false);
		
		final FontMetrics fm = g2d.getFontMetrics();
		
		final String startTxt = nf.format(startValue) + " " + unitText.toString();
		int y = 
				(int)Math.round(
						(bounds.getY() + bounds.getHeight())) - fm.getDescent();
		int x = (int)Math.round(bounds.getX());
		g2d.setColor(Color.blue);
		g2d.drawString(startTxt, x, y);
		
		final String endTxt = nf.format(endValue) + " " + unitText.toString();
		final Rectangle2D endBounds = fm.getStringBounds(endTxt, g2d);
		y = 
				(int)Math.round(
						(bounds.getY()) + endBounds.getHeight()) - fm.getDescent();
		g2d.drawString(endTxt, x, y);
	}
	
}
