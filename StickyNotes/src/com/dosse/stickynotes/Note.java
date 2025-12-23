/*
 * Copyright (C) 2016-2019 Federico Dossena (Original NoteBot)
 * Copyright (C) 2025 Modern UI Edition Contributors (UI Enhancements)
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
 * 
 * ============================================================================
 * MODERN UI ENHANCEMENTS:
 * - Windows 11 Fluent Design with rounded corners (8px)
 * - Modern color schemes with accent colors
 * - Custom resize handler with 40px edge detection
 * - Hover effects and modern button designs
 * - Enhanced visual feedback and usability
 * ============================================================================
 */
package com.dosse.stickynotes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.rtf.RTFEditorKit;
import javax.swing.undo.UndoManager;

/**
 *
 * @author Federico
 */
public class Note extends JDialog {

    private static final ResourceBundle locBundle = ResourceBundle.getBundle("com/dosse/stickynotes/locale/locale");

    /**
     * returns localized string
     *
     * @param s key
     * @return localized String, or null the key doesn't exist
     */
    private static String getLocString(String s) {
        return locBundle.getString(s);
    }

    //<editor-fold defaultstate="collapsed" desc="Color schemes - Windows 11 Style">
    /**
     * SCHEME FORMAT: {external color, line border color, bar color, buttons
     * color, internal color, text color, selection background color, selected
     * text color}
     * 
     * Windows 11 Sticky Notes color palette - more vibrant and modern
     */
    private static final Color[] YELLOW_SCHEME = new Color[]{
        new Color(255, 247, 177),  // external - bright yellow
        new Color(229, 222, 159),  // border - slightly darker
        new Color(255, 247, 177),  // bar
        new Color(89, 84, 51),     // buttons - dark contrast
        new Color(255, 252, 204),  // internal - lighter yellow
        new Color(38, 38, 38),     // text - near black
        new Color(0, 120, 212),    // selection - Windows accent blue
        new Color(255, 255, 255)   // selected text
    };
    private static final Color[] ORANGE_SCHEME = new Color[]{
        new Color(255, 214, 156),  // Peach/Orange
        new Color(229, 192, 140),
        new Color(255, 214, 156),
        new Color(89, 67, 43),
        new Color(255, 228, 186),
        new Color(38, 38, 38),
        new Color(0, 120, 212),
        new Color(255, 255, 255)
    };
    private static final Color[] BLUE_SCHEME = new Color[]{
        new Color(167, 219, 255),  // Sky blue
        new Color(150, 196, 229),
        new Color(167, 219, 255),
        new Color(51, 76, 89),
        new Color(194, 234, 255),
        new Color(38, 38, 38),
        new Color(0, 90, 158),
        new Color(255, 255, 255)
    };
    private static final Color[] GREEN_SCHEME = new Color[]{
        new Color(175, 237, 173),  // Mint green
        new Color(157, 213, 155),
        new Color(175, 237, 173),
        new Color(54, 82, 53),
        new Color(200, 247, 197),
        new Color(38, 38, 38),
        new Color(0, 120, 212),
        new Color(255, 255, 255)
    };
    private static final Color[] PINK_SCHEME = new Color[]{
        new Color(255, 191, 224),  // Pink
        new Color(229, 171, 201),
        new Color(255, 191, 224),
        new Color(89, 59, 78),
        new Color(255, 214, 236),
        new Color(38, 38, 38),
        new Color(0, 120, 212),
        new Color(255, 255, 255)
    };
    private static final Color[] PURPLE_SCHEME = new Color[]{
        new Color(208, 191, 255),  // Lavender
        new Color(186, 171, 229),
        new Color(208, 191, 255),
        new Color(72, 59, 89),
        new Color(224, 214, 255),
        new Color(38, 38, 38),
        new Color(0, 120, 212),
        new Color(255, 255, 255)
    };
    private static final Color[] RED_SCHEME = new Color[]{
        new Color(255, 183, 176),  // Coral/Salmon
        new Color(229, 164, 158),
        new Color(255, 183, 176),
        new Color(89, 57, 54),
        new Color(255, 209, 204),
        new Color(38, 38, 38),
        new Color(0, 120, 212),
        new Color(255, 255, 255)
    };
    private static final Color[] WHITE_SCHEME = new Color[]{
        new Color(249, 249, 249),  // Light gray/white
        new Color(229, 229, 229),
        new Color(249, 249, 249),
        new Color(96, 96, 96),
        new Color(255, 255, 255),
        new Color(38, 38, 38),
        new Color(0, 120, 212),
        new Color(255, 255, 255)
    };
    // New: Charcoal/Dark theme for Windows 11 dark mode users
    private static final Color[] CHARCOAL_SCHEME = new Color[]{
        new Color(55, 55, 55),     // Dark gray
        new Color(70, 70, 70),
        new Color(55, 55, 55),
        new Color(180, 180, 180),
        new Color(45, 45, 45),
        new Color(240, 240, 240),  // Light text
        new Color(0, 120, 212),
        new Color(255, 255, 255)
    };

    private static final Color[] DEFAULT_SCHEME = YELLOW_SCHEME;
    //</editor-fold>

    /**
     * copy a string to the system clipboard
     *
     * @param s string
     */
    private static void setClipboard(String s) {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(s), null);
    }

    /**
     * read contents of system clipboard
     *
     * @return clipboard contents
     */
    private static String getClipboard() {
        try {
            Transferable contents = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
            if (contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                return (String) contents.getTransferData(DataFlavor.stringFlavor);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * load image from classpath (it will be the jar file)
     *
     * @param pathInClasspath path in classpath
     * @return the image, or an empty image if something went wrong
     */
    public static final Image loadImage(String pathInClasspath) {
        try {
            return ImageIO.read(Note.class.getResource(pathInClasspath));
        } catch (IOException ex) {
            BufferedImage i = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            i.setRGB(0, 0, 0);
            return i;
        }
    }

    //UI Constants - Windows 11 Modern Style
    private static final int DEFAULT_NOTE_WIDTH = 160;
    private static final int DEFAULT_NOTE_HEIGHT = 160;
    private static final int MIN_NOTE_WIDTH = 120;
    private static final int MIN_NOTE_HEIGHT = 120;
    private static final int BORDER_SIZE = 1;
    private static final int BUTTON_HEIGHT = 21;
    private static final int HEADER_HEIGHT = 22; // slightly taller than buttons
    private static final int MENU_ITEM_WIDTH = 140;
    private static final int MENU_ITEM_HEIGHT = 28;
    private static final int CORNER_RADIUS = 22;
    private static final float SCALE_FACTOR = 0.85f;
    private static final float TEXT_SCALE_STEP = 0.1f;

    //UI Elements
    private final JPanel wrapper1; //outer wrapper: it's the area that the user can use to resize the window
    private final JPanel wrapper2; //inner wrapper: it contains the buttons and the actual note; the empty space can be dragged to move the note
    private int mouseDragStartX, mouseDragStartY; //used for dragging
    private final JButton deleteNote, newNote; //buttons to delete and create notes
    private final JScrollPane jScrollPane1; //container for the text. provides the scrollbar
    private final JTextPane text; //the actual note - JTextPane for rich text formatting
    private final UndoManager undo = new UndoManager(); //undo/redo manager (provided by swing)
    private final FormatMenu formatMenu; //modern format menu shown when text is right-clicked
    private final JPopupMenu colorMenu; //menu shown when the top is right-clicked
    private Point preferredLocation = new Point(0, 0); //the preferred location is the last user-set location of the note. this is useful when the screen resolution is changed and the notes are all scrambled up
    private float textScale = 1; //text zoom
    private static final float MIN_TEXT_SCALE = 0.2f, MAX_TEXT_SCALE = 4f; //min max text zoom

    /**
     * Creates new form Note.
     *
     * A note is initialized empty, with a yellow background and at current
     * mouse coordinates.
     */
    public Note() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); //if alt+f4 is pressed, this will cause the windowClosing event to be fired
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //if alt+f4 is pressed, save the current state and terminate the app
                Main.flushSaves();
                System.exit(0);
            }
        });
        addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowLostFocus(WindowEvent e) {
                //if focus is lost, save the current state
                Main.requestSave();
            }

            @Override
            public void windowGainedFocus(WindowEvent e) {
                Main.bringToFront(Note.this);
                text.requestFocusInWindow();
            }
        });
        setTitle(getLocString("APPNAME")); //set window title
        setIconImage(loadImage("/com/dosse/stickynotes/icon.png")); //set window icon
        setUndecorated(true); //removes system window border

        //now we will create and initialize all the elements inside the note
        // Windows 11 style - rounded corners panel
        wrapper1 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), (int)(CORNER_RADIUS * Main.SCALE), (int)(CORNER_RADIUS * Main.SCALE));
                g2.dispose();
            }
        };
        wrapper1.setOpaque(false);
        
        wrapper2 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                // Only round top corners for header area
                g2.fillRoundRect(0, 0, getWidth(), (int)(HEADER_HEIGHT * Main.SCALE), (int)(CORNER_RADIUS * Main.SCALE), (int)(CORNER_RADIUS * Main.SCALE));
                g2.fillRect(0, (int)(CORNER_RADIUS * Main.SCALE), getWidth(), getHeight() - (int)(CORNER_RADIUS * Main.SCALE));
                g2.dispose();
            }
        };
        wrapper2.setOpaque(false);
        wrapper2.setCursor(new Cursor(Cursor.MOVE_CURSOR));
        
        // Modern buttons with hover effects
        newNote = new JButton() {
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
                    g2.setColor(new Color(0, 0, 0, 40));
                } else if (isHovered) {
                    g2.setColor(new Color(0, 0, 0, 20));
                }
                if (isHovered || isPressed) {
                    g2.fillRoundRect(2, 2, getWidth()-4, getHeight()-4, 6, 6);
                }
                int cx = getWidth() / 2;
                int cy = getHeight() / 2;
                int arm = (int) (Math.min(getWidth(), getHeight()) / 3f * 0.4f); // 60% smaller
                float stroke = Math.max(1.5f, 1.2f * Main.SCALE);
                g2.setStroke(new BasicStroke(stroke, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.setColor(getForeground());
                g2.drawLine(cx - arm, cy, cx + arm, cy);
                g2.drawLine(cx, cy - arm, cx, cy + arm);
                g2.dispose();
            }
        };
        
        deleteNote = new JButton() {
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
                Color iconColor = getForeground();
                if (isPressed) {
                    g2.setColor(new Color(232, 17, 35, 200));
                    iconColor = Color.WHITE;
                } else if (isHovered) {
                    g2.setColor(new Color(232, 17, 35, 160));
                    iconColor = Color.WHITE;
                }
                if (isHovered || isPressed) {
                    g2.fillRoundRect(2, 2, getWidth()-4, getHeight()-4, 6, 6);
                }
                int cx = getWidth() / 2;
                int cy = getHeight() / 2;
                int arm = (int) (Math.min(getWidth(), getHeight()) / 3f * 0.4f); // 60% smaller
                float stroke = Math.max(1.5f, 1.2f * Main.SCALE);
                g2.setStroke(new BasicStroke(stroke, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.setColor(iconColor);
                g2.drawLine(cx - arm, cy - arm, cx + arm, cy + arm);
                g2.drawLine(cx - arm, cy + arm, cx + arm, cy - arm);
                g2.dispose();
            }
        };
        
        jScrollPane1 = new JScrollPane();
        //create the text pane with rich text support
        text = new JTextPane() {
            @Override
            public boolean getScrollableTracksViewportWidth() {//configures the textpane to resize properly horizontaly (workaround for swing bug)
                return true;
            }
        };
        
        // Set modern text area margins
        text.setMargin(new java.awt.Insets((int)(8 * Main.SCALE), (int)(12 * Main.SCALE), (int)(8 * Main.SCALE), (int)(12 * Main.SCALE)));
        
        //allow undo/redo
        Document doc = text.getDocument();
        doc.addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                undo.addEdit(e.getEdit());
            }
        });
        
        // Initialize modern format menu
        formatMenu = new FormatMenu(text, getBackground());

        jScrollPane1.setBorder(null);
        jScrollPane1.setViewportView(text); //add text area to scrollpane
        jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        // Modern thin scrollbar
        jScrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension((int)(8 * Main.SCALE), 0));
        jScrollPane1.getVerticalScrollBar().setUnitIncrement((int)(16 * Main.SCALE));

        //events used for dragging the note
        wrapper2.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                mouseDragStartX = evt.getXOnScreen() - getX();
                mouseDragStartY = evt.getYOnScreen() - getY();
            }
        });
        wrapper2.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent evt) {
                setLocation(evt.getXOnScreen() - mouseDragStartX, evt.getYOnScreen() - mouseDragStartY);
            }
        });
        
        // Custom resize handler - much simpler and more reliable
        addMouseListener(new MouseAdapter() {
            private int direction = 0;
            
            @Override
            public void mousePressed(MouseEvent e) {
                direction = getResizeDirection(e.getX(), e.getY());
                if (direction != 0) {
                    startResizeX = e.getXOnScreen();
                    startResizeY = e.getYOnScreen();
                    startResizeWidth = getWidth();
                    startResizeHeight = getHeight();
                }
            }
        });
        
        addMouseMotionListener(new MouseMotionAdapter() {
            private int direction = 0;
            
            @Override
            public void mouseMoved(MouseEvent e) {
                lastMouseX = e.getX();
                lastMouseY = e.getY();
                direction = getResizeDirection(e.getX(), e.getY());
                updateCursor(direction);
            }
            
            @Override
            public void mouseDragged(MouseEvent e) {
                lastMouseX = e.getX();
                lastMouseY = e.getY();
                if (direction == 0) {
                    direction = getResizeDirection(e.getX(), e.getY());
                }
                if (direction != 0) {
                    resizeWindow(e.getXOnScreen(), e.getYOnScreen());
                }
            }
        });
        
        setPreferredSize(new Dimension((int) (DEFAULT_NOTE_WIDTH * Main.SCALE), (int) (DEFAULT_NOTE_HEIGHT * Main.SCALE)));
        setLocation(MouseInfo.getPointerInfo().getLocation()); //new note is placed at current mouse coordinates
        setResizable(false); //disallow resize by OS, such as maximize. Notes are still resizeable by the user (provided by custom handler)

        //new note button - Windows 11 style
        newNote.setFont(new FontUIResource(Main.BUTTON_FONT.deriveFont(Main.BUTTON_TEXT_SIZE * 1.4f)));
        newNote.setBorderPainted(false);
        newNote.setContentAreaFilled(false);
        newNote.setFocusPainted(false);
        newNote.setCursor(new Cursor(Cursor.HAND_CURSOR));
        newNote.setToolTipText(getLocString("APPNAME") + " - New Note");
        newNote.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Main.newNote();
            }
        });

        //delete note button - Windows 11 style with red hover
        deleteNote.setFont(new FontUIResource(Main.BUTTON_FONT.deriveFont(Main.BUTTON_TEXT_SIZE * 1.4f)));
        deleteNote.setBorderPainted(false);
        deleteNote.setContentAreaFilled(false);
        deleteNote.setFocusPainted(false);
        deleteNote.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteNote.setToolTipText("Close Note");
        deleteNote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                Main.delete(Note.this);
            }
        });

        //right click on the note - show modern format menu
        text.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.isPopupTrigger() || evt.getButton() == MouseEvent.BUTTON3) { //isPopupTrigger does not work on windows. workaround is to listen for BUTTON3 instead (right mouse button)
                    formatMenu.show(evt.getComponent(), evt.getX(), evt.getY()); //show the modern format menu at current mouse location
                }
            }
        });
        
        // Add keyboard shortcuts for formatting
        text.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() || e.isMetaDown()) {
                    StyledDocument doc = text.getStyledDocument();
                    int start = text.getSelectionStart();
                    int end = text.getSelectionEnd();
                    
                    if (e.getKeyCode() == KeyEvent.VK_B) {
                        // Toggle bold
                        MutableAttributeSet attr = new SimpleAttributeSet();
                        boolean isBold = StyleConstants.isBold(text.getCharacterAttributes());
                        StyleConstants.setBold(attr, !isBold);
                        if (start != end) {
                            doc.setCharacterAttributes(start, end - start, attr, false);
                        } else {
                            text.getInputAttributes().addAttributes(attr);
                        }
                        e.consume();
                    } else if (e.getKeyCode() == KeyEvent.VK_I) {
                        // Toggle italic
                        MutableAttributeSet attr = new SimpleAttributeSet();
                        boolean isItalic = StyleConstants.isItalic(text.getCharacterAttributes());
                        StyleConstants.setItalic(attr, !isItalic);
                        if (start != end) {
                            doc.setCharacterAttributes(start, end - start, attr, false);
                        } else {
                            text.getInputAttributes().addAttributes(attr);
                        }
                        e.consume();
                    } else if (e.getKeyCode() == KeyEvent.VK_U) {
                        // Toggle underline
                        MutableAttributeSet attr = new SimpleAttributeSet();
                        boolean isUnderline = StyleConstants.isUnderline(text.getCharacterAttributes());
                        StyleConstants.setUnderline(attr, !isUnderline);
                        if (start != end) {
                            doc.setCharacterAttributes(start, end - start, attr, false);
                        } else {
                            text.getInputAttributes().addAttributes(attr);
                        }
                        e.consume();
                    }
                }
            }
        });

        //listener for ctrl+wheel (for zooming in and out)
        text.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.isControlDown() || e.isMetaDown()) { //scroll wheel with ctrl pressed (isMetaDown is for macOS)
                    if (e.getWheelRotation() < 0) {
                        setTextScale(textScale + TEXT_SCALE_STEP);
                    } else if (e.getWheelRotation() > 0) {
                        setTextScale(textScale - TEXT_SCALE_STEP);
                    }
                } else { //scroll wheel without ctrl pressed simply scrolls
                    jScrollPane1.getMouseWheelListeners()[0].mouseWheelMoved(e);
                }
            }
        });

        //listener for ctrl+add, ctrl+minus, ctrl+NP0 (for zooming in and out)
        text.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() || e.isMetaDown()) {
                    if (e.getKeyCode() == KeyEvent.VK_ADD) {
                        setTextScale(textScale + TEXT_SCALE_STEP);
                    } else if (e.getKeyCode() == KeyEvent.VK_SUBTRACT) {
                        setTextScale(textScale - TEXT_SCALE_STEP);
                    } else if (e.getKeyCode() == KeyEvent.VK_NUMPAD0) {
                        setTextScale(1);
                    }
                }
            }
        });

        //listener for ctrl+N, ctrl+D
        text.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() || e.isMetaDown()) {
                    if (e.getKeyCode() == KeyEvent.VK_N) {
                        Main.newNote().setLocation((int) (preferredLocation.x + 40 * Main.SCALE), (int) (preferredLocation.y + 40 * Main.SCALE));
                    } else if (e.getKeyCode() == KeyEvent.VK_D) {
                        Main.delete(Note.this);
                    }
                }
            }
        });

        //listener for ctrl+A (select all text)
        text.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() || e.isMetaDown()) {
                    if (e.getKeyCode() == KeyEvent.VK_A) {
                        text.selectAll();
                    }
                }
            }
        });

        //listener for ctrl+Z, ctrl+Y (undo, redo)
        text.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() || e.isMetaDown()) {
                    if (e.getKeyCode() == KeyEvent.VK_Z) {
                        if (undo.canUndo()) {
                            undo.undo();
                        }
                    } else if (e.getKeyCode() == KeyEvent.VK_Y) {
                        if (undo.canRedo()) {
                            undo.redo();
                        }
                    }

                }
            }
        });

        //right click on top bar
        wrapper2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.isPopupTrigger() || evt.getButton() == MouseEvent.BUTTON3) {
                    colorMenu.show(evt.getComponent(), evt.getX(), evt.getY()); //show color selection menu
                }
            }
        });

        //initialize color selection menu (right click on top bar)
        colorMenu = new JPopupMenu();
        // Windows 11 style color palette - includes dark theme option
        colorMenu.add(new ColorSelector(new Color[][]{YELLOW_SCHEME, ORANGE_SCHEME, BLUE_SCHEME, GREEN_SCHEME, PINK_SCHEME, PURPLE_SCHEME, RED_SCHEME, WHITE_SCHEME, CHARCOAL_SCHEME}) {
            @Override
            public void onColorSchemeSelected(Color[] scheme) {
                setColorScheme(scheme);
                colorMenu.setVisible(false);
                Main.requestSave();
            }
        });
        //custom color selector
        colorMenu.add(new CustomColorSelector() {
            @Override
            public void onColorSelected(Color c) {
                //compute all colors from the selected color
                float[] hsb = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
                hsb[1] *= 0.69f; //reduce saturation
                if (hsb[2] < 0.55f) {
                    //if brightness<55%, use dark settings
                    Color internal = new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2])),
                            external = new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2] + 0.1f)),
                            bar = external,
                            buttons = new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2] + 0.3f)),
                            lineBorder = new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2] + 0.15f)),
                            text = new Color(255, 255, 255),
                            selectionBk = hsb[1] < 0.01f ? new Color(192, 192, 192) : new Color(Color.HSBtoRGB(hsb[0]-0.05f, hsb[1]+0.2f, 1f)), //if low saturation, use alternative selection color instead of computed one
                            selectedText = new Color(0, 0, 0);
                    Color[] customScheme = new Color[]{external, lineBorder, bar, buttons, internal, text, selectionBk, selectedText};
                    setColorScheme(customScheme);
                } else {
                    //otherwise, use bright settings
                    Color internal = new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2])),
                            external = new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2] - 0.04f)),
                            bar = external,
                            buttons = new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2] - 0.35f)),
                            lineBorder = new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2] - 0.1f)),
                            text = new Color(0, 0, 0),
                            selectionBk = hsb[1] < 0.01f ? new Color(72, 72, 72) : new Color(Color.HSBtoRGB(hsb[0], hsb[1]+0.2f, 0.4f)), //if low saturation, use alternative selection color instead of computed one
                            selectedText = new Color(255, 255, 255);
                    Color[] customScheme = new Color[]{external, lineBorder, bar, buttons, internal, text, selectionBk, selectedText};
                    setColorScheme(customScheme);
                }
                colorMenu.setVisible(false);
                Main.requestSave();
            }
        });
        colorMenu.add(new JPopupMenu.Separator());
        JMenuItem m = new JMenuItem(getLocString("ABOUT"));
        m.setPreferredSize(new Dimension((int) (MENU_ITEM_WIDTH * Main.SCALE), (int) (MENU_ITEM_HEIGHT * Main.SCALE)));
        m.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AboutDialog(null, true).setVisible(true);
            }
        });
        colorMenu.add(m);

        //add everything to the layout - Windows 11 Modern Style
        int buttonSize = (int)(BUTTON_HEIGHT * Main.SCALE);
        int padding = (int)(3 * Main.SCALE);
        int headerPadding = (int)(6 * Main.SCALE);
        
        GroupLayout wrapper2Layout = new GroupLayout(wrapper2);
        wrapper2.setLayout(wrapper2Layout);
        wrapper2Layout.setHorizontalGroup(
            wrapper2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(wrapper2Layout.createSequentialGroup()
                    .addGap(headerPadding)
                    .addComponent(newNote, GroupLayout.PREFERRED_SIZE, buttonSize, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 134, Short.MAX_VALUE)
                    .addComponent(deleteNote, GroupLayout.PREFERRED_SIZE, buttonSize, GroupLayout.PREFERRED_SIZE)
                    .addGap(headerPadding))
                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        wrapper2Layout.setVerticalGroup(
            wrapper2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING, wrapper2Layout.createSequentialGroup()
                    .addGap(padding)
                    .addGroup(wrapper2Layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(deleteNote, GroupLayout.PREFERRED_SIZE, buttonSize, GroupLayout.PREFERRED_SIZE)
                        .addComponent(newNote, GroupLayout.PREFERRED_SIZE, buttonSize, GroupLayout.PREFERRED_SIZE))
                    .addGap(padding)
                    .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
        );
        
        GroupLayout wrapper1Layout = new GroupLayout(wrapper1);
        wrapper1.setLayout(wrapper1Layout);
        wrapper1Layout.setHorizontalGroup(
            wrapper1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(wrapper2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        wrapper1Layout.setVerticalGroup(
            wrapper1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(wrapper2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(wrapper1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(wrapper1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        pack(); //finalize the layout

        setColorScheme(DEFAULT_SCHEME); //set default color scheme (yellow)
        
        // Apply rounded corners to window - Windows 11 style
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), CORNER_RADIUS * Main.SCALE, CORNER_RADIUS * Main.SCALE));
            }
        });

        //and we're done
    }

    /**
     * setLocation method is overridden to force the note to stay on the screen
     *
     * @param x new x location
     * @param y new y location
     */
    @Override
    public void setLocation(int x, int y) {
        setBounds(x, y, getWidth(), getHeight());
    }

    /**
     * setLocation method is overridden to force the note to stay on the screen
     *
     * @param p new location
     */
    @Override
    public void setLocation(Point p) {
        setLocation(p.x, p.y);
    }

    /**
     * setBounds method is overridden to force the note to stay on the screen
     *
     * @param x x
     * @param y y
     * @param width width
     * @param height height
     */
    @Override
    public void setBounds(int x, int y, int width, int height) {
        if (preferredLocation == null) { //for some odd fucking reason, this can happen on some versions of java
            preferredLocation = new Point(0, 0);
        }
        preferredLocation.x = x;
        preferredLocation.y = y;
        Dimension s = Main.getExtendedScreenResolution();
        if (x + 60 * Main.SCALE > s.width) {
            x = (int) (s.width - 60 * Main.SCALE);
        }
        if (y + 60 * Main.SCALE > s.height) {
            y = (int) (s.height - 60 * Main.SCALE);
        }
        super.setBounds(x, y, width, height);
    }

    /**
     * setBounds method is overridden to force the note to stay on the screen
     *
     * @param r new bounds
     */
    @Override
    public void setBounds(Rectangle r) {
        setBounds(r.x, r.y, r.width, r.height);
    }

    /**
     * get text currently inside the note
     *
     * @return text
     */
    public String getText() {
        return text.getText();
    }

    /**
     * set text currently inside the note
     *
     * @param s
     */
    public void setText(String s) {
        text.setText(s);
        undo.discardAllEdits();
    }

    /**
     * gets the last user-set location of the note
     *
     * @return location
     */
    public Point getPreferredLocation() {
        return preferredLocation;
    }

    /**
     * get current text scale
     *
     * @return text scale
     */
    public float getTextScale() {
        return textScale;
    }

    /**
     * set new text scale
     *
     * @param scale scale as float 0.2-4.0
     */
    public void setTextScale(float scale) {
        if (scale >= 0.99 && scale <= 1.01) {
            textScale = 1;
            text.setFont(Main.BASE_FONT);
        } else {
            textScale = scale < MIN_TEXT_SCALE ? MIN_TEXT_SCALE : scale > MAX_TEXT_SCALE ? MAX_TEXT_SCALE : scale;
            text.setFont(Main.BASE_FONT.deriveFont(Main.TEXT_SIZE * textScale));
        }
    }

    /**
     * get current color scheme
     *
     * @return current color scheme (see format at the beginning of this file)
     */
    public Color[] getColorScheme() {
        Color[] ret = new Color[8];
        ret[0] = wrapper1.getBackground();
        ret[1] = savedBorderColor != null ? savedBorderColor : wrapper1.getBackground();
        ret[2] = wrapper2.getBackground();
        ret[3] = newNote.getForeground();
        ret[4] = text.getBackground();
        ret[5] = text.getForeground();
        ret[6] = text.getSelectionColor();
        ret[7] = text.getSelectedTextColor();
        return ret;
    }
    
    private Color savedBorderColor = null;

    /**
     * set color scheme - Windows 11 Modern Style
     *
     * @param c color scheme (see format at the beginning of this file)
     */
    public void setColorScheme(Color[] c) {
        savedBorderColor = c[1];
        wrapper1.setBackground(c[0]);
        // Modern thin border - Windows 11 style
        wrapper1.setBorder(new LineBorder(c[1], 1));
        wrapper2.setBackground(c[2]);
        newNote.setForeground(c[3]);
        deleteNote.setForeground(c[3]);
        text.setBackground(c[4]);
        text.setForeground(c[5]);
        text.setCaretColor(c[5]);
        text.setSelectionColor(c[6]);
        text.setSelectedTextColor(c[7]);
        // Update scrollbar color based on theme
        jScrollPane1.getViewport().setBackground(c[4]);
        formatMenu.updateTheme(c[4]);
    }
    
    private int resizeBorder = (int)(40 * Main.SCALE);
    private int startResizeX, startResizeY, startResizeWidth, startResizeHeight;
    
    /**
     * Determine which direction to resize based on mouse position
     */
    private int getResizeDirection(int x, int y) {
        int direction = 0;
        // Check if mouse is in resize area (40px from edges)
        if (x < resizeBorder) direction |= 2; // WEST
        if (x > getWidth() - resizeBorder) direction |= 8; // EAST
        if (y < resizeBorder) direction |= 1; // NORTH
        if (y > getHeight() - resizeBorder) direction |= 4; // SOUTH
        return direction;
    }
    
    /**
     * Update cursor based on resize direction
     */
    private void updateCursor(int direction) {
        Cursor cursor;
        switch (direction) {
            case 1: cursor = new Cursor(Cursor.N_RESIZE_CURSOR); break;
            case 2: cursor = new Cursor(Cursor.W_RESIZE_CURSOR); break;
            case 3: cursor = new Cursor(Cursor.NW_RESIZE_CURSOR); break;
            case 4: cursor = new Cursor(Cursor.S_RESIZE_CURSOR); break;
            case 6: cursor = new Cursor(Cursor.SW_RESIZE_CURSOR); break;
            case 8: cursor = new Cursor(Cursor.E_RESIZE_CURSOR); break;
            case 9: cursor = new Cursor(Cursor.NE_RESIZE_CURSOR); break;
            case 12: cursor = new Cursor(Cursor.SE_RESIZE_CURSOR); break;
            default: cursor = Cursor.getDefaultCursor();
        }
        setCursor(cursor);
    }
    
    /**
     * Resize window based on mouse drag
     */
    private void resizeWindow(int mouseX, int mouseY) {
        int direction = getResizeDirection(lastMouseX, lastMouseY);
        if (direction == 0) return;
        
        int deltaX = mouseX - startResizeX;
        int deltaY = mouseY - startResizeY;
        
        int minWidth = (int)(MIN_NOTE_WIDTH * Main.SCALE);
        int minHeight = (int)(MIN_NOTE_HEIGHT * Main.SCALE);
        
        int newX = getX();
        int newY = getY();
        int newWidth = startResizeWidth;
        int newHeight = startResizeHeight;
        
        if ((direction & 2) != 0) { // WEST
            newX = getX() + deltaX;
            newWidth = startResizeWidth - deltaX;
        }
        if ((direction & 8) != 0) { // EAST
            newWidth = startResizeWidth + deltaX;
        }
        if ((direction & 1) != 0) { // NORTH
            newY = getY() + deltaY;
            newHeight = startResizeHeight - deltaY;
        }
        if ((direction & 4) != 0) { // SOUTH
            newHeight = startResizeHeight + deltaY;
        }
        
        // Apply minimum size constraints
        if (newWidth < minWidth) newWidth = minWidth;
        if (newHeight < minHeight) newHeight = minHeight;
        
        // Keep WEST resize from shrinking too much
        if ((direction & 2) != 0 && newWidth < minWidth) {
            newX = getX() + (startResizeWidth - minWidth);
            newWidth = minWidth;
        }
        
        // Keep NORTH resize from shrinking too much
        if ((direction & 1) != 0 && newHeight < minHeight) {
            newY = getY() + (startResizeHeight - minHeight);
            newHeight = minHeight;
        }
        
        setLocation(newX, newY);
        setSize(newWidth, newHeight);
    }
    
    private int lastMouseX, lastMouseY;
}
