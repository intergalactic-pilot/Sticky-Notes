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
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

/**
 * Windows 11 style About Dialog
 * @author Federico
 */
public class AboutDialog extends JDialog {

    private static final int DEFAULT_WIDTH = (int) (420 * Main.SCALE),
            DEFAULT_HEIGHT = (int) (400 * Main.SCALE);
    private static final int CORNER_RADIUS = (int) (8 * Main.SCALE);

    private static final ResourceBundle locBundle = ResourceBundle.getBundle("com/dosse/stickynotes/locale/locale");

    /**
     * Creates new form AboutDialog - Windows 11 Modern Style
     */
    public AboutDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(null);
        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        setResizable(false);
        setTitle(locBundle.getString("ABOUT"));
        getContentPane().setBackground(new Color(251, 251, 251));
        
        // Main panel with padding
        JPanel main = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), CORNER_RADIUS, CORNER_RADIUS);
                g2.dispose();
            }
        };
        main.setLayout(null);
        main.setBackground(new Color(255, 255, 255));
        main.setOpaque(false);
        add(main);
        
        int padding = (int) (16 * Main.SCALE);
        main.setBounds(padding, padding, 
            (int) (DEFAULT_WIDTH - padding * 2 - getInsets().left - getInsets().right), 
            (int) (DEFAULT_HEIGHT - padding * 2 - getInsets().top - getInsets().bottom));
        
        // Icon and title section
        JLabel icon = new JLabel();
        icon.setIcon(loadScaled("/com/dosse/stickynotes/icon.png", 0.6f));
        icon.setHorizontalAlignment(SwingConstants.CENTER);
        icon.setBounds(0, (int)(20 * Main.SCALE), main.getWidth(), (int) (64 * Main.SCALE));
        main.add(icon);
        
        JLabel title = new JLabel();
        title.setFont(Main.BASE_FONT.deriveFont(Font.BOLD, 24f * Main.SCALE));
        title.setText(locBundle.getString("APPNAME"));
        title.setForeground(new Color(38, 38, 38));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBounds(0, (int) (90 * Main.SCALE), main.getWidth(), (int) (36 * Main.SCALE));
        main.add(title);
        
        // Separator line - Windows 11 style thin line
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(230, 230, 230));
        sep.setBackground(new Color(230, 230, 230));
        sep.setBounds((int)(20 * Main.SCALE), (int) (135 * Main.SCALE), main.getWidth() - (int)(40 * Main.SCALE), 1);
        main.add(sep);
        
        // Info section
        Font infoFont = Main.BASE_FONT.deriveFont(12f * Main.SCALE);
        Color textColor = new Color(96, 96, 96);
        
        JLabel ver = new JLabel();
        ver.setFont(infoFont);
        ver.setForeground(textColor);
        ver.setText(locBundle.getString("ABOUT_VERSION"));
        ver.setBounds((int)(20 * Main.SCALE), (int) (150 * Main.SCALE), main.getWidth() - (int)(40 * Main.SCALE), (int) (24 * Main.SCALE));
        main.add(ver);
        
        final JLabel url = new JLabel();
        url.setFont(infoFont);
        url.setText(locBundle.getString("ABOUT_URL"));
        url.setForeground(new Color(0, 102, 204));  // Windows 11 link blue
        url.setCursor(new Cursor(Cursor.HAND_CURSOR));
        url.setBounds((int)(20 * Main.SCALE), (int) (174 * Main.SCALE), main.getWidth() - (int)(40 * Main.SCALE), (int) (24 * Main.SCALE));
        url.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(url.getText()));
                } catch (Throwable t) {
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                url.setText("<html><u>" + locBundle.getString("ABOUT_URL") + "</u></html>");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                url.setText(locBundle.getString("ABOUT_URL"));
            }
        });
        main.add(url);
        
        JLabel copy = new JLabel();
        copy.setFont(infoFont);
        copy.setForeground(textColor);
        copy.setText("<html>" + locBundle.getString("ABOUT_COPYRIGHT") + "</html>");
        copy.setBounds((int)(20 * Main.SCALE), (int) (210 * Main.SCALE), main.getWidth() - (int)(40 * Main.SCALE), (int) (80 * Main.SCALE));
        main.add(copy);
        
        // Close button - Windows 11 style
        JButton ok = new JButton(locBundle.getString("ABOUT_CLOSE")) {
            private boolean isHovered = false;
            private boolean isPressed = false;
            {
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) { isHovered = true; repaint(); }
                    @Override
                    public void mouseExited(MouseEvent e) { isHovered = false; isPressed = false; repaint(); }
                    @Override
                    public void mousePressed(MouseEvent e) { isPressed = true; repaint(); }
                    @Override
                    public void mouseReleased(MouseEvent e) { isPressed = false; repaint(); }
                });
            }
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (isPressed) {
                    g2.setColor(new Color(0, 90, 158));
                } else if (isHovered) {
                    g2.setColor(new Color(0, 102, 180));
                } else {
                    g2.setColor(new Color(0, 120, 212));  // Windows accent blue
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                
                g2.dispose();
                super.paintComponent(g);
            }
        };
        ok.setFont(Main.BASE_FONT.deriveFont(12f * Main.SCALE));
        ok.setForeground(Color.WHITE);
        ok.setContentAreaFilled(false);
        ok.setBorderPainted(false);
        ok.setFocusPainted(false);
        ok.setCursor(new Cursor(Cursor.HAND_CURSOR));
        int buttonWidth = (int)(100 * Main.SCALE);
        int buttonHeight = (int)(32 * Main.SCALE);
        ok.setBounds((main.getWidth() - buttonWidth) / 2, main.getHeight() - buttonHeight - (int)(20 * Main.SCALE), buttonWidth, buttonHeight);
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        main.add(ok);
        
        pack();
        setLocationRelativeTo(null);  // Center on screen
    }

    private static final BufferedImage nullImage;

    static {
        nullImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        nullImage.setRGB(0, 0, 0);
    }

    /**
     * load image from classpath as ImageIcon and scales according to screen DPI
     */
    private static final ImageIcon loadScaled(String pathInClasspath, float customScale) {
        try {
            Image i = ImageIO.read(AboutDialog.class.getResource(pathInClasspath));
            return new ImageIcon(i.getScaledInstance((int) (i.getWidth(null) * Main.SCALE * customScale), (int) (i.getHeight(null) * Main.SCALE * customScale), Image.SCALE_SMOOTH));
        } catch (Throwable ex) {
            return new ImageIcon(nullImage);
        }
    }
}
