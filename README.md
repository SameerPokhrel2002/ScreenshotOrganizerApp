# 📸 Screenshot Organizer

A lightweight Java utility that monitors your system for new screenshots, renames them sequentially (`screenshot1.png`, `screenshot2.png`, etc.), and stores them in a dedicated `latestScreenshot` folder on your Desktop.

## 🚀 Features

- ✅ Auto-detects new screenshots
- ✅ Automatically renames them in sequential order
- ✅ Saves all screenshots to a clean folder on the Desktop
- ✅ Resets counter if the folder is deleted
- ✅ Lightweight and efficient

## 🛠️ Technologies Used

- Java (Standard Edition)
- File & Directory Monitoring (Java NIO)
- Gson (for future extensions if needed)
- Works on Linux (tested on Fedora)

## 📂 Project Structure
screenshot-organizer/
├── src/
│ └── com/yourpackage/ScreenshotOrganizer.java
├── .gitignore
├── README.md


## ⚙️ How It Works

1. The app watches your default screenshot directory (e.g., `~/Pictures` or `~/Desktop`)
2. When a new screenshot is detected, it's moved to `~/Desktop/latestScreenshot/`
3. It is renamed to `screenshot1.png`, `screenshot2.png`, etc.
4. If the folder is deleted, the counter resets

## 🧪 How to Run

1. **Clone the repository:**

```bash
git clone https://github.com/your-username/screenshot-organizer.git
cd screenshot-organizer

##Compile and run
javac -d bin src/com/yourpackage/ScreenshotOrganizer.java
java -cp bin com.yourpackage.ScreenshotOrganizer


## To-Do / Future Plans
 GUI version using JavaFX

 Tray icon support

 Configurable source folder

 
