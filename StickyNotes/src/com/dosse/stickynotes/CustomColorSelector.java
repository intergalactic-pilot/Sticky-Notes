/*
 * Copyright (C) 2017-2024 Federico Dossena
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
package com.dosse.stickynotes;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * Windows 11 style custom color selector with modern gradient picker
 * @author Federico
 */
public abstract class CustomColorSelector extends JPanel {

    private static final int DEFAULT_WIDTH = (int) (292 * Main.SCALE);  // Match color selector width
    private static final int DEFAULT_HEIGHT = (int) (80 * Main.SCALE);
    private static final int GRAYSCALE_HEIGHT = (int) (12 * Main.SCALE);
    private static final int CORNER_RADIUS = (int) (6 * Main.SCALE);
    private static final int PADDING = (int) (4 * Main.SCALE);

    private static final float BASE_SATURATION = 0.7f;  // More vibrant colors

    public CustomColorSelector() {
        setLayout(null);
        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        setOpaque(false);
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                try {
                    // Read the color clicked by the user
                    BufferedImage b = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
                    paintColorPicker(b.getGraphics());
                    int color = b.getRGB(e.getX(), e.getY());
                    onColorSelected(new Color(color));
                } catch (Throwable t) {
                    // Coordinates can be out of bounds if the user drags the cursor around
                }
            }
        });
    }
    
    private void paintColorPicker(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        float width = getWidth() - PADDING * 2;
        float height = getHeight() - GRAYSCALE_HEIGHT - PADDING * 3;
        float fx, fy;
        
        // Color gradient
        for (float y = 0; y < height; y++) {
            for (float x = 0; x < width; x++) {
                fx = x / width;
                fy = y / height;
                g2.setColor(Color.getHSBColor(fx, 
                    fy < 0.5f ? (BASE_SATURATION - BASE_SATURATION * 2 * (0.5f - fy)) : BASE_SATURATION, 
                    fy < 0.5f ? 1f : (1 - (2 * (fy - 0.5f)))));
                g2.fillRect((int) (x + PADDING), (int) (y + PADDING), 1, 1);
            }
        }
        
        // Grayscale bar
        for (float x = 0; x < width; x++) {
            g2.setColor(Color.getHSBColor(0, 0, x / width));
            g2.fillRect((int) (x + PADDING), (int) (height + PADDING * 2), 1, GRAYSCALE_HEIGHT);
        }
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw rounded background
        g2.setColor(new Color(250, 250, 250));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), CORNER_RADIUS, CORNER_RADIUS);
        
        // Draw border
        g2.setColor(new Color(220, 220, 220));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, CORNER_RADIUS, CORNER_RADIUS);
        
        g2.dispose();
        
        paintColorPicker(g);
    }

    public abstract void onColorSelected(Color c);

}
