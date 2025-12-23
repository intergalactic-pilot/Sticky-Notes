# NoteBot - Modern UI Edition
**Enhanced fork of the original NoteBot with Windows 11 Fluent Design UI**

![Version](https://img.shields.io/badge/version-2.6-blue.svg)
![License](https://img.shields.io/badge/license-GPL--3.0-green.svg)
![Platform](https://img.shields.io/badge/platform-Windows%2010%2F11-blue.svg)

NoteBot is a clone of Microsoft Sticky Notes, without useless functionalities.

## ⚡ This Fork's Enhancements (v2.6)

This is an **enhanced community fork** with the following improvements over the original:

### 🎨 Windows 11 Modern UI
* **Fluent Design System** - Rounded corners (8px radius) throughout the interface
* **Modern color schemes** - 8 vibrant colors + new dark charcoal theme
* **Hover effects** - Smooth button interactions with accent colors
* **Improved resize experience** - Custom resize handler with 40px edge detection
* **Better visual feedback** - Modern cursors and visual indicators
* **Anti-aliased rendering** - Smooth graphics with proper rendering hints

### 🖱️ Enhanced Usability
* **Easier window resizing** - Larger grab zones for comfortable edge/corner dragging
* **Modern button design** - Close button with red hover, add button with accent blue
* **Improved color picker** - Rounded swatches with modern selection indicators
* **Custom gradient picker** - Enhanced saturation for more vibrant custom colors
* **Touch-friendly** - Larger interactive areas for better touch support

### 🔧 Technical Improvements
* **DPI-aware scaling** - Consistent sizing across different display scales
* **Custom resize implementation** - More reliable than third-party components
* **Modern Java compatibility** - Tested with OpenJDK 25
* **Optimized rendering** - Better performance with modern graphics APIs

---

## 📥 Download & Installation

### Modern UI Edition v2.6 (Latest)

**Windows Installer (Recommended):**
- [notebot-modern-v2.6-setup.exe](https://github.com/intergalactic-pilot/Scitky-Notes/releases/download/v2.6/notebot-modern-v2.6-setup.exe) (~5 MB)
  - Includes Windows 11 Fluent Design UI
  - Auto-startup support
  - Java 8+ compatible
  - One-click installation

### Original NoteBot Downloads
[Installer for Windows](http://downloads.fdossena.com/geth.php?r=stickynotes-win)

[Deb Package for Ubuntu, Debian, ...](http://downloads.fdossena.com/geth.php?r=stickynotes-deb)

[Binaries for other platforms](http://downloads.fdossena.com/geth.php?r=stickynotes-bin) (Requires Java)

## Website
[Original NoteBot](http://notebot.fdossena.com/) | [This Fork on GitHub](https://github.com/intergalactic-pilot/Scitky-Notes)

## Features
* Stick notes on your desktop
* Can move, resize and zoom
* Custom colors
* Auto startup
* Support for high DPI screens and touch devices
* **[NEW]** Windows 11 Fluent Design UI with rounded corners
* **[NEW]** 9 modern color schemes including dark theme
* **[NEW]** Enhanced resize experience with visual feedback
* **[NEW]** Modern hover effects and button animations
* **[NEW]** Improved color picker with gradient enhancement

## Lack of features
Sticky Notes became trash with the August 2016 update of Windows 10, which turned it into a "modern" app, and added useless and suspicious features like synchronization and telemetry. Therefore...

* Not a "modern" app: you can obliterate all Metro apps from your PC
* No taskbar icon: notes actually stick to the desktop
* No synchronization
* No telemetry
* Not even an auto updater
* No botnet: it's open source, you can trust this application
* And most importantly: no ugly Segoe Script font

## Compatibility
* Windows XP or newer
* Any platform supported by Java SE 7 or newer
 
## Usage
Import the projects into Netbeans.

_SETUP contains all the files used to build the Windows installer and the .deb package.
To build the Windows installer, you'll need [Inno Setup](http://www.jrsoftware.org/isinfo.php) and [launch4j](http://launch4j.sourceforge.net/).

## Screenshots
![Screenshot](http://fdossena.com/stickynotes/screen1_16.png)

> **Note:** Screenshots show the original version. This fork features a modernized Windows 11-style interface with rounded corners, improved colors, and enhanced usability.

## Differences from Original

This fork maintains the lightweight philosophy of the original NoteBot while adding modern UI polish:

* ✅ **Preserves:** Lightweight design, no telemetry, no synchronization, open source
* ✨ **Adds:** Windows 11 Fluent Design, better resize experience, modern color schemes
* 🎨 **Updates:** All UI components modernized with rounded corners and hover effects
* 🔧 **Improves:** Custom resize handler replacing third-party component

### Original Project
The original NoteBot was created by Federico Dossena.  
Visit the [original project](http://notebot.fdossena.com/) for more information.

## License
Copyright (C) 2016-2019 Federico Dossena (Original NoteBot)  
Copyright (C) 2025 Modern UI Edition Contributors (UI Enhancements)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
