/*
 * Copyright (C) 2016-2024 Federico Dossena
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
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 * Windows 11 style color selector with rounded color swatches
 * @author Federico
 */
public abstract class ColorSelector extends JPanel {

    private static final int BUTTON_SIZE = (int) (28 * Main.SCALE);
    private static final int BUTTON_SPACING = (int) (4 * Main.SCALE);
    private static final int BUTTONS_PER_ROW = 9;  // 8 colors + 1 dark theme
    private static final int CORNER_RADIUS = (int) (6 * Main.SCALE);
    private static final Color HOVER_BORDER_COLOR = new Color(0, 120, 212);  // Windows accent blue
    private static final Color SELECTED_BORDER_COLOR = new Color(0, 90, 158);
    private final JLabel[] buttons;

    public ColorSelector(Color[][] colorSchemes) {
        setLayout(null);
        setOpaque(false);
        int rows = (int) Math.ceil((double) colorSchemes.length / BUTTONS_PER_ROW);
        int totalWidth = BUTTONS_PER_ROW * (BUTTON_SIZE + BUTTON_SPACING) + BUTTON_SPACING;
        int totalHeight = rows * (BUTTON_SIZE + BUTTON_SPACING) + BUTTON_SPACING;
        setPreferredSize(new Dimension(totalWidth, totalHeight));
        
        int x = BUTTON_SPACING, y = BUTTON_SPACING, i = 0, bi = 0;
        buttons = new JLabel[colorSchemes.length];
        
        for (final Color[] c : colorSchemes) {
            final JLabel l = new JLabel() {
                private boolean isHovered = false;
                
                {
                    addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            isHovered = true;
                            setCursor(new Cursor(Cursor.HAND_CURSOR));
                            repaint();
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            isHovered = false;
                            setCursor(Cursor.getDefaultCursor());
                            repaint();
                        }

                        @Override
                        public void mouseClicked(MouseEvent e) {
                            onColorSchemeSelected(c);
                        }
                    });
                }
                
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    // Draw rounded color swatch
                    g2.setColor(c[0]);
                    g2.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, CORNER_RADIUS, CORNER_RADIUS);
                    
                    // Draw border on hover - Windows 11 accent color
                    if (isHovered) {
                        g2.setColor(HOVER_BORDER_COLOR);
                        g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, CORNER_RADIUS, CORNER_RADIUS);
                        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, CORNER_RADIUS + 1, CORNER_RADIUS + 1);
                    } else {
                        // Subtle border for definition
                        float[] hsb = Color.RGBtoHSB(c[0].getRed(), c[0].getGreen(), c[0].getBlue(), null);
                        Color borderColor = new Color(Color.HSBtoRGB(hsb[0], Math.min(1f, hsb[1] + 0.1f), Math.max(0f, hsb[2] - 0.15f)));
                        g2.setColor(borderColor);
                        g2.drawRoundRect(2, 2, getWidth() - 5, getHeight() - 5, CORNER_RADIUS - 1, CORNER_RADIUS - 1);
                    }
                    
                    g2.dispose();
                }
            };
            
            l.setOpaque(false);
            add(l);
            l.setLocation(x, y);
            l.setSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
            buttons[bi++] = l;
            
            x += BUTTON_SIZE + BUTTON_SPACING;
            i++;
            if (i == BUTTONS_PER_ROW) {
                i = 0;
                x = BUTTON_SPACING;
                y += BUTTON_SIZE + BUTTON_SPACING;
            }
        }
        
        addHierarchyListener(new HierarchyListener() {
            @Override
            public void hierarchyChanged(HierarchyEvent e) {
                for (JLabel b : buttons) {
                    b.repaint();
                }
            }
        });
    }

    public abstract void onColorSchemeSelected(Color[] scheme);

}
