package com.dosse.stickynotes;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ResourceBundle;

/**
 * Compact modern formatting context menu for sticky notes
 * Windows 11 style with icon buttons and minimal spacing
 */
public class FormatMenu extends JPopupMenu {
    
    private final JTextPane textPane;
    private Color noteColor;
    private static final ResourceBundle locBundle = ResourceBundle.getBundle("com/dosse/stickynotes/locale/locale");
    
    // Compact sizing
    private static final int ICON_BTN_SIZE = 28;
    private static final int ITEM_HEIGHT = 24;
    private static final Font MENU_FONT = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font ICON_FONT = new Font("Segoe UI", Font.BOLD, 12);
    private static final Font FORMAT_FONT = new Font("Segoe UI", Font.BOLD, 12);
    
    public FormatMenu(JTextPane textPane, Color noteColor) {
        this.textPane = textPane;
        this.noteColor = noteColor;
        
        applyTheme();
        buildMenu();
    }
    
    public void updateTheme(Color noteColor) {
        this.noteColor = noteColor;
        removeAll();
        applyTheme();
        buildMenu();
    }
    
    private void applyTheme() {
        // Darken the note color for menu background
        Color menuBg = new Color(
            Math.max(0, noteColor.getRed() - 30),
            Math.max(0, noteColor.getGreen() - 30),
            Math.max(0, noteColor.getBlue() - 30)
        );
        Color borderColor = new Color(
            Math.max(0, noteColor.getRed() - 50),
            Math.max(0, noteColor.getGreen() - 50),
            Math.max(0, noteColor.getBlue() - 50)
        );
        
        setBackground(menuBg);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(borderColor, 1),
            BorderFactory.createEmptyBorder(4, 4, 4, 4)
        ));
        setOpaque(true);
    }
    
    private void buildMenu() {
        // Quick actions
        add(createCompactMenuItem(locBundle.getString("MENU_CUT"), this::cut));
        add(createCompactMenuItem(locBundle.getString("MENU_COPY"), this::copy));
        add(createCompactMenuItem(locBundle.getString("MENU_PASTE"), this::paste));
        
        addCompactSeparator();
        
        // Format options
        add(createCompactMenuItem(locBundle.getString("MENU_BOLD"), this::toggleBold));
        add(createCompactMenuItem(locBundle.getString("MENU_ITALIC"), this::toggleItalic));
        add(createCompactMenuItem(locBundle.getString("MENU_UNDERLINE"), this::toggleUnderline));
        add(createCompactMenuItem(locBundle.getString("MENU_STRIKE"), this::toggleStrikethrough));
        
        addCompactSeparator();
        
        // Text size submenu
        JMenu sizeMenu = createCompactMenu(locBundle.getString("MENU_SIZE"));
        int[] sizes = {8, 10, 12, 14, 16, 18, 20, 24, 28, 32};
        for (int size : sizes) {
            sizeMenu.add(createSizeItem(size));
        }
        add(sizeMenu);
        
        addCompactSeparator();
        
        // List options
        add(createCompactMenuItem(locBundle.getString("MENU_BULLET"), this::addBullet));
        add(createCompactMenuItem(locBundle.getString("MENU_NUMBERED"), this::addNumbered));
        
        addCompactSeparator();
        
        add(createCompactMenuItem(locBundle.getString("MENU_SELECT_ALL"), this::selectAll));
    }
    
    private JButton createIconButton(String icon, String tooltip, Runnable action) {
        JButton btn = new JButton(icon);
        btn.setFont(ICON_FONT);
        btn.setToolTipText(tooltip);
        btn.setPreferredSize(new Dimension(ICON_BTN_SIZE, ICON_BTN_SIZE));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setForeground(Color.WHITE);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setContentAreaFilled(true);
                btn.setBackground(new Color(65, 65, 65));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setContentAreaFilled(false);
            }
        });
        
        btn.addActionListener(e -> {
            action.run();
            setVisible(false);
        });
        
        return btn;
    }
    
    private JButton createFormatButton(String label, String tooltip, Runnable action) {
        JButton btn = new JButton(label);
        btn.setFont(FORMAT_FONT);
        btn.setToolTipText(tooltip);
        btn.setPreferredSize(new Dimension(ICON_BTN_SIZE, ICON_BTN_SIZE));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setForeground(Color.WHITE);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Special styling for format buttons
        if (label.equals("I")) {
            btn.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 12));
        } else if (label.equals("U")) {
            btn.setText("<html><u>U</u></html>");
        } else if (label.equals("S")) {
            btn.setText("<html><s>S</s></html>");
        }
        
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setContentAreaFilled(true);
                btn.setBackground(new Color(65, 65, 65));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setContentAreaFilled(false);
            }
        });
        
        btn.addActionListener(e -> {
            action.run();
            setVisible(false);
        });
        
        return btn;
    }
    
    private JMenuItem createCompactMenuItem(String text, Runnable action) {
        Color menuBg = new Color(
            Math.max(0, noteColor.getRed() - 30),
            Math.max(0, noteColor.getGreen() - 30),
            Math.max(0, noteColor.getBlue() - 30)
        );
        Color hoverBg = new Color(
            Math.max(0, noteColor.getRed() - 15),
            Math.max(0, noteColor.getGreen() - 15),
            Math.max(0, noteColor.getBlue() - 15)
        );
        
        JMenuItem item = new JMenuItem(text);
        item.setFont(MENU_FONT);
        item.setForeground(Color.WHITE);
        item.setBackground(menuBg);
        item.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));
        item.setPreferredSize(new Dimension(140, ITEM_HEIGHT));
        
        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                item.setBackground(hoverBg);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                item.setBackground(menuBg);
            }
        });
        
        item.addActionListener(e -> {
            action.run();
            setVisible(false);
        });
        
        return item;
    }
    
    private JMenu createCompactMenu(String text) {
        Color menuBg = new Color(
            Math.max(0, noteColor.getRed() - 30),
            Math.max(0, noteColor.getGreen() - 30),
            Math.max(0, noteColor.getBlue() - 30)
        );
        Color hoverBg = new Color(
            Math.max(0, noteColor.getRed() - 15),
            Math.max(0, noteColor.getGreen() - 15),
            Math.max(0, noteColor.getBlue() - 15)
        );
        Color borderColor = new Color(
            Math.max(0, noteColor.getRed() - 50),
            Math.max(0, noteColor.getGreen() - 50),
            Math.max(0, noteColor.getBlue() - 50)
        );
        
        JMenu menu = new JMenu(text);
        menu.setFont(MENU_FONT);
        menu.setForeground(Color.WHITE);
        menu.setBackground(menuBg);
        menu.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));
        menu.setPreferredSize(new Dimension(140, ITEM_HEIGHT));
        menu.setOpaque(true);
        
        menu.getPopupMenu().setBackground(menuBg);
        menu.getPopupMenu().setBorder(BorderFactory.createLineBorder(borderColor, 1));
        
        menu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                menu.setBackground(hoverBg);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                menu.setBackground(menuBg);
            }
        });
        
        return menu;
    }
    
    private JMenuItem createSizeItem(int size) {
        Color menuBg = new Color(
            Math.max(0, noteColor.getRed() - 30),
            Math.max(0, noteColor.getGreen() - 30),
            Math.max(0, noteColor.getBlue() - 30)
        );
        Color hoverBg = new Color(
            Math.max(0, noteColor.getRed() - 15),
            Math.max(0, noteColor.getGreen() - 15),
            Math.max(0, noteColor.getBlue() - 15)
        );
        
        JMenuItem item = new JMenuItem(size + " pt");
        item.setFont(MENU_FONT);
        item.setForeground(Color.WHITE);
        item.setBackground(menuBg);
        item.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));
        item.setPreferredSize(new Dimension(70, ITEM_HEIGHT));
        
        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                item.setBackground(hoverBg);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                item.setBackground(menuBg);
            }
        });
        
        item.addActionListener(e -> {
            setFontSize(size);
            setVisible(false);
        });
        
        return item;
    }
    
    private void addCompactSeparator() {
        Color borderColor = new Color(
            Math.max(0, noteColor.getRed() - 50),
            Math.max(0, noteColor.getGreen() - 50),
            Math.max(0, noteColor.getBlue() - 50)
        );
        JSeparator sep = new JSeparator();
        sep.setForeground(borderColor);
        sep.setBackground(borderColor);
        sep.setPreferredSize(new Dimension(0, 1));
        add(sep);
    }
    
    // Actions
    private void cut() {
        textPane.cut();
    }
    
    private void copy() {
        textPane.copy();
    }
    
    private void paste() {
        textPane.paste();
    }
    
    private void undo() {
        // Simple undo placeholder
    }
    
    private void toggleBold() {
        StyledDocument doc = textPane.getStyledDocument();
        int start = textPane.getSelectionStart();
        int end = textPane.getSelectionEnd();
        if (start == end) return;
        
        Element element = doc.getCharacterElement(start);
        AttributeSet as = element.getAttributes();
        boolean isBold = StyleConstants.isBold(as);
        
        MutableAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setBold(attrs, !isBold);
        doc.setCharacterAttributes(start, end - start, attrs, false);
    }
    
    private void toggleItalic() {
        StyledDocument doc = textPane.getStyledDocument();
        int start = textPane.getSelectionStart();
        int end = textPane.getSelectionEnd();
        if (start == end) return;
        
        Element element = doc.getCharacterElement(start);
        AttributeSet as = element.getAttributes();
        boolean isItalic = StyleConstants.isItalic(as);
        
        MutableAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setItalic(attrs, !isItalic);
        doc.setCharacterAttributes(start, end - start, attrs, false);
    }
    
    private void toggleUnderline() {
        StyledDocument doc = textPane.getStyledDocument();
        int start = textPane.getSelectionStart();
        int end = textPane.getSelectionEnd();
        if (start == end) return;
        
        Element element = doc.getCharacterElement(start);
        AttributeSet as = element.getAttributes();
        boolean isUnderline = StyleConstants.isUnderline(as);
        
        MutableAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setUnderline(attrs, !isUnderline);
        doc.setCharacterAttributes(start, end - start, attrs, false);
    }
    
    private void toggleStrikethrough() {
        StyledDocument doc = textPane.getStyledDocument();
        int start = textPane.getSelectionStart();
        int end = textPane.getSelectionEnd();
        if (start == end) return;
        
        Element element = doc.getCharacterElement(start);
        AttributeSet as = element.getAttributes();
        boolean isStrike = StyleConstants.isStrikeThrough(as);
        
        MutableAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setStrikeThrough(attrs, !isStrike);
        doc.setCharacterAttributes(start, end - start, attrs, false);
    }
    
    private void setFontSize(int size) {
        StyledDocument doc = textPane.getStyledDocument();
        int start = textPane.getSelectionStart();
        int end = textPane.getSelectionEnd();
        if (start == end) return;
        
        MutableAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setFontSize(attrs, size);
        doc.setCharacterAttributes(start, end - start, attrs, false);
    }
    
    private void addBullet() {
        try {
            int pos = textPane.getCaretPosition();
            textPane.getDocument().insertString(pos, "• ", null);
        } catch (BadLocationException e) {}
    }
    
    private void addNumbered() {
        try {
            int pos = textPane.getCaretPosition();
            textPane.getDocument().insertString(pos, "1. ", null);
        } catch (BadLocationException e) {}
    }
    
    private void selectAll() {
        textPane.selectAll();
    }
}
