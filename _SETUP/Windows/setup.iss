[Setup]
AppId={{3E658137-CE80-49E3-8084-FD0B0158CA31}
AppName="Sticky Notes Modern UI Edition"
AppVersion="2.0"
AppPublisher="Modern UI Edition Contributors"
AppPublisherURL="https://github.com/intergalactic-pilot/Scitky-Notes"
AppSupportURL="https://github.com/intergalactic-pilot/Scitky-Notes"
AppUpdatesURL="https://github.com/intergalactic-pilot/Scitky-Notes"
DefaultDirName="{pf}\NoteBot Modern UI"
DefaultGroupName="NoteBot Modern UI"
DisableProgramGroupPage=yes
LicenseFile=gpl-3.0.txt
OutputDir=.
OutputBaseFilename=notebot-modern-v2-setup
Compression=lzma2/ultra64
LZMAAlgorithm=1
LZMAMatchFinder=BT
SolidCompression=yes
LZMANumBlockThreads=1
LZMANumFastBytes=273
LZMADictionarySize=1048576
LZMAUseSeparateProcess=yes
InternalCompressLevel=ultra64
SetupIconFile="icon.ico"
UninstallDisplayIcon="icon.ico"

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"
Name: "italian"; MessagesFile: "compiler:Languages\Italian.isl"

[Files]
Source: "setupFiles\*"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs sortfilesbyextension;

[Icons]
Name: "{group}\NoteBot Modern UI"; Filename: "{app}\StickyNotes.exe"

[Run]
Filename:"{app}\StickyNotes.exe"; Flags:runasoriginaluser nowait;

[Registry]
Root: HKLM; Subkey: "SOFTWARE\Microsoft\Windows\CurrentVersion\Run"; ValueName: "NoteBot Modern UI"; ValueType: string; ValueData: """{app}\StickyNotes.exe"" -autostartup"; Flags: uninsdeletevalue 
Root: HKLM32; Subkey: "SOFTWARE\Microsoft\Windows\CurrentVersion\Run"; ValueName: "NoteBot Modern UI"; ValueType: string; ValueData: """{app}\StickyNotes.exe"" -autostartup"; Flags: uninsdeletevalue 
