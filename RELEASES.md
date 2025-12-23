# Releases - NoteBot Modern UI Edition

All releases of the NoteBot Modern UI Edition are documented here.

## v2.6 - December 23, 2025

**Latest Release - Windows 11 Fluent Design Edition**

### ?? Downloads

- **[notebot-modern-v2.6-setup.exe](https://github.com/intergalactic-pilot/Scitky-Notes/releases/download/v2.6/notebot-modern-v2.6-setup.exe)** (~5 MB)
  - Windows installer with auto-startup support
  - Recommended for end-users
  - Requires Java 8 or higher

### ? What's New in v2.6

#### ?? UI Refresh
- Slimmer header bar to maximize note space
- Custom drawn + / X icons (smaller, crisp, DPI-aware)
- Format menu rethemed per note color and fully in English

#### ?? Windows 11 Fluent Design UI (carried forward)
- Rounded corners (8px radius) throughout the interface
- Modern color palette with 9 vibrant schemes including dark charcoal
- Smooth hover effects and anti-aliased rendering

#### ??? Enhanced Usability
- **Custom Resize Handler** - 40px edge detection (vs. 5px original)
- Easy-to-grab window corners and edges
- Visual 8-direction cursor feedback during resizing
- Improved drag-and-drop responsiveness
- Better touch support with larger interactive areas

#### ?? Technical Enhancements
- DPI-aware scaling for all UI elements
- Full compatibility with Java 8-25+
- Optimized rendering with proper graphics hints
- Better memory management
- Support for high-DPI displays (2K, 4K monitors)

#### ?? Build System
- Launch4j integration for EXE generation
- Inno Setup installer with auto-startup registry configuration
- Proper JRE detection for system Java installation
- Complete installer uninstall support

#### ?? Documentation
- Updated README, FORK_INFO, and About dialog to show v2.6
- Release notes refreshed for the UI changes

### ?? Bug Fixes

- Fixed ComponentResizer reliability issues
- Improved font resource loading in JAR files
- Fixed manifest configuration for proper JAR execution
- Better error handling for missing Java installations

### ?? Statistics

- **23 files modified**
- **2074 lines of code added**
- **402 lines of code removed**
- **1 new utility class** (ModernUI.java - 458 lines)
- **3 new documentation files** (CHANGELOG.md, FORK_INFO.md, BUILD_INSTRUCTIONS.md)

### ?? System Requirements

- **OS:** Windows 10 / Windows 11
- **Java:** JRE 8.0 or higher (OpenJDK or Oracle JDK)
- **RAM:** 256 MB minimum
- **Storage:** ~10 MB (installer + installation)

### ?? Migration from Original

This version is **not backward compatible** in terms of UI, but note data and settings are preserved.

1. Uninstall original NoteBot if present
2. Install v2.6 using the installer
3. Your existing notes will be migrated automatically

### ?? Credits

- **Original NoteBot:** Federico Dossena (2016-2019)
- **Modern UI Edition:** Community fork contributors (2025)
- **Design Inspiration:** Windows 11 Fluent Design System
- **License:** GNU General Public License v3.0

### ?? Links

- **GitHub Repository:** https://github.com/intergalactic-pilot/Scitky-Notes
- **Original Project:** http://notebot.fdossena.com/
- **Report Issues:** https://github.com/intergalactic-pilot/Scitky-Notes/issues

---

## Version History

| Version | Date | Type | Link |
|---------|------|------|------|
| v2.6 | Dec 23, 2025 | Full Release | [Download](https://github.com/intergalactic-pilot/Scitky-Notes/releases/download/v2.6/notebot-modern-v2.6-setup.exe) |
| v2.0 | Dec 23, 2025 | Full Release | [Download](https://github.com/intergalactic-pilot/Scitky-Notes/releases/download/v2.0/notebot-modern-v2-setup.exe) |
| v1.6.3 | Original | - | [Original Project](http://notebot.fdossena.com/) |

---

**NoteBot Modern UI Edition - Making sticky notes beautiful again! ??**
