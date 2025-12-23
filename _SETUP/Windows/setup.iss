[Setup]
AppId={{3E658137-CE80-49E3-8084-FD0B0158CA31}
AppName="Sticky Notes Modern UI Edition"
AppVersion="2.6"
AppPublisher="Modern UI Edition Contributors"
AppPublisherURL="https://github.com/intergalactic-pilot/Scitky-Notes"
AppSupportURL="https://github.com/intergalactic-pilot/Scitky-Notes"
AppUpdatesURL="https://github.com/intergalactic-pilot/Scitky-Notes"
DefaultDirName="{pf}\NoteBot Modern UI"
DefaultGroupName="NoteBot Modern UI"
DisableProgramGroupPage=yes
LicenseFile=gpl-3.0.txt
OutputDir=.
OutputBaseFilename=notebot-modern-v2.6-setup
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
Name: "turkish"; MessagesFile: "compiler:Languages\Turkish.isl"
Name: "german"; MessagesFile: "compiler:Languages\German.isl"

[Files]
Source: "setupFiles\*"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs sortfilesbyextension;

[Icons]
Name: "{group}\NoteBot Modern UI"; Filename: "{app}\StickyNotes.exe"

[Run]
Filename:"{app}\StickyNotes.exe"; Flags:runasoriginaluser nowait;

[Registry]
Root: HKLM; Subkey: "SOFTWARE\Microsoft\Windows\CurrentVersion\Run"; ValueName: "NoteBot Modern UI"; ValueType: string; ValueData: """{app}\StickyNotes.exe"" -autostartup"; Flags: uninsdeletevalue 
Root: HKLM32; Subkey: "SOFTWARE\Microsoft\Windows\CurrentVersion\Run"; ValueName: "NoteBot Modern UI"; ValueType: string; ValueData: """{app}\StickyNotes.exe"" -autostartup"; Flags: uninsdeletevalue 

[Code]
function LangToCode(const Lang: String): String;
begin
  if Lang = 'turkish' then Result := 'tr'
  else if Lang = 'italian' then Result := 'it'
  else if Lang = 'german' then Result := 'de'
  else Result := 'en';
end;

procedure WriteLanguageConfig;
var
  cfgPath: String;
  langCode: String;
begin
  cfgPath := ExpandConstant('{localappdata}\NoteBot\config.properties');
  langCode := LangToCode(ActiveLanguage());
  if not DirExists(ExtractFilePath(cfgPath)) then
    ForceDirectories(ExtractFilePath(cfgPath));
  SaveStringToFile(cfgPath, 'lang=' + langCode + #13#10, False);
end;

procedure CurStepChanged(CurStep: TSetupStep);
begin
  if CurStep = ssInstall then
  begin
    WriteLanguageConfig;
  end;
end;
