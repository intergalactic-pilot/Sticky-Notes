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

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;

/**
 * Modern UI utilities for Windows 11 style design
 * @author Federico
 */
public class ModernUI {
    
    // Windows 11 style corner radius
    public static final int CORNER_RADIUS = (int)(8 * Main.SCALE);
    public static final int CORNER_RADIUS_SMALL = (int)(4 * Main.SCALE);
    
    // Padding and margins
    public static final int PADDING = (int)(8 * Main.SCALE);
    public static final int PADDING_SMALL = (int)(4 * Main.SCALE);
    
    // Button sizes
    public static final int BUTTON_SIZE = (int)(32 * Main.SCALE);
    public static final int BUTTON_ICON_SIZE = (int)(16 * Main.SCALE);
    
    // Header height
    public static final int HEADER_HEIGHT = (int)(40 * Main.SCALE);
    
    // Shadow settings
    public static final int SHADOW_SIZE = (int)(4 * Main.SCALE);
    public static final Color SHADOW_COLOR = new Color(0, 0, 0, 30);
    
    /**
     * Rounded border with optional shadow for Windows 11 style
     */
    public static class RoundedBorder extends AbstractBorder {
        private final int radius;
        private final Color borderColor;
        private final int thickness;
        private final boolean hasShadow;
        
        public RoundedBorder(int radius, Color borderColor, int thickness, boolean hasShadow) {
            this.radius = radius;
            this.borderColor = borderColor;
            this.thickness = thickness;
            this.hasShadow = hasShadow;
        }
        
        public RoundedBorder(int radius, Color borderColor) {
            this(radius, borderColor, 1, false);
        }
        
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            if (hasShadow) {
                // Draw shadow
                for (int i = 0; i < SHADOW_SIZE; i++) {
                    g2.setColor(new Color(0, 0, 0, 10 - i * 2));
                    g2.drawRoundRect(x + i, y + i, width - 1 - i * 2, height - 1 - i * 2, radius, radius);
                }
            }
            
            // Draw border
            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(thickness));
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            
            g2.dispose();
        }
        
        @Override
        public Insets getBorderInsets(Component c) {
            int inset = thickness + (hasShadow ? SHADOW_SIZE : 0);
            return new Insets(inset, inset, inset, inset);
        }
        
        public Color getBorderColor() {
            return borderColor;
        }
    }
    
    /**
     * Soft rounded panel with background
     */
    public static class RoundedPanel extends JPanel {
        private int radius;
        private Color borderColor;
        
        public RoundedPanel(int radius) {
            this.radius = radius;
            this.borderColor = null;
            setOpaque(false);
        }
        
        public void setBorderColor(Color c) {
            this.borderColor = c;
            repaint();
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Fill background
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            
            // Draw border if set
            if (borderColor != null) {
                g2.setColor(borderColor);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            }
            
            g2.dispose();
        }
    }
    
    /**
     * Modern flat button with hover effects - Windows 11 style
     */
    public static class ModernButton extends JButton {
        private Color normalColor;
        private Color hoverColor;
        private Color pressedColor;
        private Color foregroundColor;
        private int radius;
        private boolean isHovered = false;
        private boolean isPressed = false;
        
        public ModernButton(String text) {
            super(text);
            this.radius = CORNER_RADIUS_SMALL;
            
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setOpaque(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            // Default colors
            normalColor = new Color(0, 0, 0, 0);
            hoverColor = new Color(0, 0, 0, 20);
            pressedColor = new Color(0, 0, 0, 40);
            foregroundColor = new Color(80, 80, 80);
            
            setForeground(foregroundColor);
            
            addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    isHovered = true;
                    repaint();
                }
                
                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    isHovered = false;
                    isPressed = false;
                    repaint();
                }
                
                @Override
                public void mousePressed(java.awt.event.MouseEvent e) {
                    isPressed = true;
                    repaint();
                }
                
                @Override
                public void mouseReleased(java.awt.event.MouseEvent e) {
                    isPressed = false;
                    repaint();
                }
            });
        }
        
        public void setColors(Color foreground, Color hover, Color pressed) {
            this.foregroundColor = foreground;
            this.hoverColor = hover;
            this.pressedColor = pressed;
            setForeground(foreground);
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Background
            if (isPressed) {
                g2.setColor(pressedColor);
            } else if (isHovered) {
                g2.setColor(hoverColor);
            } else {
                g2.setColor(normalColor);
            }
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            
            g2.dispose();
            super.paintComponent(g);
        }
    }
    
    /**
     * Close button with red hover effect - Windows 11 style
     */
    public static class CloseButton extends ModernButton {
        public CloseButton() {
            super("×");
            setFont(Main.BUTTON_FONT.deriveFont(Main.BUTTON_TEXT_SIZE * 1.5f));
            setColors(
                new Color(100, 100, 100),
                new Color(232, 17, 35, 180),
                new Color(232, 17, 35, 220)
            );
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Red background on hover
            if (isPressed) {
                g2.setColor(new Color(232, 17, 35, 220));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), CORNER_RADIUS_SMALL, CORNER_RADIUS_SMALL);
                setForeground(Color.WHITE);
            } else if (isHovered) {
                g2.setColor(new Color(232, 17, 35, 180));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), CORNER_RADIUS_SMALL, CORNER_RADIUS_SMALL);
                setForeground(Color.WHITE);
            } else {
                setForeground(new Color(100, 100, 100));
            }
            
            g2.dispose();
            super.paintComponent(g);
        }
        
        private boolean isHovered = false;
        private boolean isPressed = false;
        
        {
            addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    isHovered = true;
                    repaint();
                }
                
                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    isHovered = false;
                    isPressed = false;
                    repaint();
                }
                
                @Override
                public void mousePressed(java.awt.event.MouseEvent e) {
                    isPressed = true;
                    repaint();
                }
                
                @Override
                public void mouseReleased(java.awt.event.MouseEvent e) {
                    isPressed = false;
                    repaint();
                }
            });
        }
    }
    
    /**
     * Add button with accent color hover - Windows 11 style
     */
    public static class AddButton extends ModernButton {
        private Color accentColor = new Color(0, 120, 212);
        
        public AddButton() {
            super("+");
            setFont(Main.BUTTON_FONT.deriveFont(Main.BUTTON_TEXT_SIZE * 1.5f));
        }
        
        public void setAccentColor(Color c) {
            this.accentColor = c;
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            if (isPressed) {
                g2.setColor(new Color(accentColor.getRed(), accentColor.getGreen(), accentColor.getBlue(), 60));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), CORNER_RADIUS_SMALL, CORNER_RADIUS_SMALL);
            } else if (isHovered) {
                g2.setColor(new Color(accentColor.getRed(), accentColor.getGreen(), accentColor.getBlue(), 40));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), CORNER_RADIUS_SMALL, CORNER_RADIUS_SMALL);
            }
            
            g2.dispose();
            super.paintComponent(g);
        }
        
        private boolean isHovered = false;
        private boolean isPressed = false;
        
        {
            addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    isHovered = true;
                    repaint();
                }
                
                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    isHovered = false;
                    isPressed = false;
                    repaint();
                }
                
                @Override
                public void mousePressed(java.awt.event.MouseEvent e) {
                    isPressed = true;
                    repaint();
                }
                
                @Override
                public void mouseReleased(java.awt.event.MouseEvent e) {
                    isPressed = false;
                    repaint();
                }
            });
        }
    }
    
    /**
     * Modern scrollbar UI - Windows 11 style thin scrollbar
     */
    public static class ModernScrollBarUI extends javax.swing.plaf.basic.BasicScrollBarUI {
        private final int scrollBarWidth = (int)(8 * Main.SCALE);
        private final Color thumbColor;
        private final Color trackColor;
        
        public ModernScrollBarUI(Color background) {
            // Calculate thumb color based on background brightness
            float[] hsb = Color.RGBtoHSB(background.getRed(), background.getGreen(), background.getBlue(), null);
            if (hsb[2] > 0.5f) {
                thumbColor = new Color(0, 0, 0, 60);
                trackColor = new Color(0, 0, 0, 0);
            } else {
                thumbColor = new Color(255, 255, 255, 80);
                trackColor = new Color(0, 0, 0, 0);
            }
        }
        
        @Override
        protected void configureScrollBarColors() {
            // Colors are already configured in constructor
        }
        
        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createZeroButton();
        }
        
        @Override
        protected JButton createIncreaseButton(int orientation) {
            return createZeroButton();
        }
        
        private JButton createZeroButton() {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(0, 0));
            button.setMinimumSize(new Dimension(0, 0));
            button.setMaximumSize(new Dimension(0, 0));
            return button;
        }
        
        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
            // Transparent track
        }
        
        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
                return;
            }
            
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            g2.setColor(thumbColor);
            
            int arc = scrollBarWidth - 2;
            if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
                g2.fillRoundRect(thumbBounds.x + 2, thumbBounds.y, thumbBounds.width - 4, thumbBounds.height, arc, arc);
            } else {
                g2.fillRoundRect(thumbBounds.x, thumbBounds.y + 2, thumbBounds.width, thumbBounds.height - 4, arc, arc);
            }
            
            g2.dispose();
        }
        
        @Override
        public Dimension getPreferredSize(JComponent c) {
            if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
                return new Dimension(scrollBarWidth, super.getPreferredSize(c).height);
            } else {
                return new Dimension(super.getPreferredSize(c).width, scrollBarWidth);
            }
        }
    }
    
    /**
     * Apply rounded corners to a window (Windows 11 style)
     */
    public static void applyRoundedCorners(Window window, int radius) {
        try {
            window.setShape(new RoundRectangle2D.Double(0, 0, window.getWidth(), window.getHeight(), radius, radius));
        } catch (Exception e) {
            // Fallback for systems that don't support window shaping
        }
    }
    
    /**
     * Create a subtle shadow border
     */
    public static Border createShadowBorder(Color baseColor) {
        float[] hsb = Color.RGBtoHSB(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), null);
        Color borderColor = new Color(Color.HSBtoRGB(hsb[0], hsb[1], Math.max(0, hsb[2] - 0.1f)));
        return new RoundedBorder(CORNER_RADIUS, borderColor, 1, false);
    }
}
