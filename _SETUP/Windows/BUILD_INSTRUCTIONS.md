# NoteBot Modern UI Edition - Installer Oluşturma Rehberi

Bu rehber, Windows installer (.exe) oluşturmak için gereken adımları açıklar.

## 📋 Gerekli Araçlar

### 1. Launch4j (JAR → EXE dönüştürücü)
**İndir:** https://sourceforge.net/projects/launch4j/files/launch4j-3/

- Son sürümü indirin (örn: `launch4j-3.50-win32.zip`)
- Zip'i `C:\Program Files\Launch4j` klasörüne açın
- Installer gerekmez, portable bir araçtır

### 2. Inno Setup (Installer oluşturucu)
**İndir:** https://jrsoftware.org/isdl.php

- "Inno Setup 6.x.x" installer'ı indirin
- Normal kurulum yapın (varsayılan ayarlar: `C:\Program Files (x86)\Inno Setup 6`)
- Kurulum bitince `ISCC.exe` kullanılabilir olacak

## 🚀 Installer Oluşturma Adımları

### Adım 1: Proje Hazırlığı

Güncel JAR zaten hazır:
```
d:\everything\computer staff\NoteBot-Source\StickyNotes\dist\StickyNotes.jar
```

setupFiles klasörüne kopyalandı ✓

### Adım 2: Launch4j ile EXE Oluşturma

#### A. GUI İle (Kolay Yol)

1. Launch4j'yi başlatın: `C:\Program Files\Launch4j\launch4j.exe`
2. **File → Open** tıklayın
3. Bu dosyayı seçin: `d:\everything\computer staff\NoteBot-Source\_SETUP\Windows\launch4j\sticky_launcher.xml`
4. **Build wrapper** butonuna tıklayın (⚙️ ikonu)
5. EXE oluşturulacak: `setupFiles\StickyNotes.exe`

#### B. Komut Satırı İle (Hızlı Yol)

PowerShell'de çalıştırın:

```powershell
cd "d:\everything\computer staff\NoteBot-Source\_SETUP\Windows"

# Launch4j ile EXE oluştur
& "C:\Program Files\Launch4j\launch4jc.exe" "launch4j\sticky_launcher.xml"
```

**Sonuç:** `setupFiles\StickyNotes.exe` oluşturulacak

### Adım 3: Inno Setup ile Installer Oluşturma

#### A. GUI İle (Kolay Yol)

1. Inno Setup Compiler'ı açın
2. **File → Open** tıklayın
3. Bu dosyayı seçin: `d:\everything\computer staff\NoteBot-Source\_SETUP\Windows\setup.iss`
4. **Build → Compile** tıklayın (veya F9)
5. Installer oluşturulacak: `notebot-modern-setup.exe`

#### B. Komut Satırı İle (Hızlı Yol)

PowerShell'de çalıştırın:

```powershell
cd "d:\everything\computer staff\NoteBot-Source\_SETUP\Windows"

# Inno Setup ile installer oluştur
& "C:\Program Files (x86)\Inno Setup 6\ISCC.exe" "setup.iss"
```

**Sonuç:** `notebot-modern-setup.exe` oluşturulacak (aynı klasörde)

## 🎯 Hızlı Komut (Her İkisi Birden)

Araçlar kuruluyken tek komutla:

```powershell
cd "d:\everything\computer staff\NoteBot-Source\_SETUP\Windows"

# 1. JAR → EXE (Launch4j)
& "C:\Program Files\Launch4j\launch4jc.exe" "launch4j\sticky_launcher.xml"

# 2. EXE → Installer (Inno Setup)
& "C:\Program Files (x86)\Inno Setup 6\ISCC.exe" "setup.iss"

Write-Host "`n✓ Installer hazır: notebot-modern-setup.exe" -ForegroundColor Green
```

## 📦 Çıktı Dosyaları

### setupFiles\ klasörü:
- `StickyNotes.jar` - Modern UI Edition JAR
- `StickyNotes.exe` - Launch4j ile oluşturulmuş EXE

### Installer:
- `notebot-modern-setup.exe` - Windows installer (~2-3 MB)

## ⚙️ Installer Özellikleri

✅ **Otomatik başlatma:** Windows açılışında otomatik başlar  
✅ **Program Files:** `C:\Program Files\NoteBot Modern UI\` klasörüne kurulur  
✅ **Start Menu:** Başlat menüsüne kısayol ekler  
✅ **Uninstaller:** Kaldırma programı otomatik eklenir  
✅ **Registry:** Başlangıç kaydı otomatik yapılır

## 🔧 Özelleştirme

### Versiyon Değiştirme

**launch4j\sticky_launcher.xml:**
```xml
<fileVersion>2.6.0.0</fileVersion>
<txtFileVersion>2.6 Modern UI Edition</txtFileVersion>
```

**setup.iss:**
```ini
AppVersion="2.6"
```

### İkon Değiştirme

`icon.ico` dosyasını değiştirin (32x32 ve 64x64 boyutları içermeli)

## 🐛 Sorun Giderme

### "Java not found" hatası
- Launch4j XML'de JRE ayarları kontrol edin
- Kullanıcıların Java kurulu olması gerekir VEYA
- JRE'yi installer'a gömebilirsiniz (bundle)

### EXE çalışmıyor
- JAR'ın doğru konumda olduğundan emin olun
- XML'deki `<jar>` yolunu kontrol edin

### Installer hatası
- `setupFiles` klasörünün var olduğundan emin olun
- `StickyNotes.exe` dosyasının oluşturulmuş olduğunu kontrol edin

## 📚 Referanslar

- Launch4j: http://launch4j.sourceforge.net/docs.html
- Inno Setup: https://jrsoftware.org/ishelp/

---

**Not:** Modern UI Edition olarak güncellenmiş tüm dosyalar hazır durumda!
