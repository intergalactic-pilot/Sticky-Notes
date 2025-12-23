# Changelog - NoteBot Modern UI Edition

All notable changes to this fork are documented in this file.

## [Modern UI Edition v1.0] - 2025-12-23

### ✨ Added
- **Windows 11 Fluent Design System** throughout the application
- **Rounded corners** (8px radius) on all windows and UI elements
- **New dark charcoal color scheme** with Windows accent blue accents
- **Custom resize handler** with 40px edge detection for easier window resizing
- **Modern button designs** with hover effects:
  - Close button with red (#E81123) hover effect
  - Add button with accent blue (#0078D4) hover effect
- **ModernUI.java** utility class with reusable components:
  - RoundedBorder, RoundedPanel, ModernButton
  - CloseButton, AddButton components
  - ModernScrollBarUI with thin 8px width
- **Enhanced ColorSelector** with rounded swatches and modern selection indicators
- **Improved CustomColorSelector** with enhanced saturation (0.7f) for vibrant colors
- **Anti-aliased rendering** with proper RenderingHints throughout
- **Visual cursor feedback** for all 8 resize directions (N/S/E/W/NE/NW/SE/SW)

### 🔧 Changed
- **UI constants updated:**
  - CORNER_RADIUS: 8px (modern rounded corners)
  - HEADER_HEIGHT: 40px (more spacious header)
  - DEFAULT_NOTE_SIZE: 280×280px (better default proportions)
  - Resize border: 40px (easier to grab)
- **Color schemes modernized:**
  - All 8 original colors refreshed with modern palette
  - Selection color changed to Windows accent blue (#0078D4)
  - Better contrast and vibrancy
- **Component rendering:**
  - All panels use RoundRectangle2D for rounded corners
  - Consistent padding and margins (8px base)
  - Modern thin borders (1px)

### 🚀 Improved
- **Resize functionality:**
  - Replaced unreliable ComponentResizer with custom implementation
  - Larger grab zones (40px border) for comfortable edge/corner dragging
  - Better cursor feedback during resize operations
  - Minimum size constraints properly enforced
- **DPI scaling:**
  - All dimensions multiplied by Main.SCALE factor
  - Consistent sizing across different display scales
  - Better support for high-DPI displays
- **Performance:**
  - Optimized rendering with proper graphics hints
  - Reduced unnecessary repaints
  - Better memory management

### 🐛 Fixed
- ComponentResizer initialization order issues
- Font resource loading in JAR files
- Manifest configuration for proper JAR execution
- Build process reliability

### 🔄 Replaced
- ComponentResizer third-party component → Custom resize handler
- Square buttons → Modern rounded hover buttons
- Basic color swatches → Rounded modern color picker
- Standard cursor handling → 8-direction cursor feedback

---

## Original NoteBot

This fork is based on NoteBot by Federico Dossena (2016-2019).

Original features preserved:
- ✅ Lightweight desktop sticky notes
- ✅ No telemetry or synchronization
- ✅ No taskbar icon (notes stick to desktop)
- ✅ Open source under GPL-3.0
- ✅ Cross-platform Java support
- ✅ High DPI and touch device support

For the original project, visit: http://notebot.fdossena.com/
