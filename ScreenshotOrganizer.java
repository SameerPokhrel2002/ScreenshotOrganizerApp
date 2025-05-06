import java.io.IOException; //to handle file operation erros
import java.nio.file.*; //to watch paths, files, and directory
import static java.nio.file.StandardWatchEventKinds.*; //to detect new files
import java.util.concurrent.atomic.AtomicInteger; //to help track numbers safely 
public class ScreenshotOrganizer {

    private static final String SCREENSHOT_EXT = ".png"; 

    public static void main(String[] args) throws IOException, InterruptedException {
        
        Path sourceDir = Paths.get(System.getProperty("user.home"), "Pictures","Screenshots");

        // Set destination directory on Desktop
        Path destDir = Paths.get(System.getProperty("user.home"), "Desktop", "latestScreenshots");

        WatchService watchService = FileSystems.getDefault().newWatchService();
        sourceDir.register(watchService, ENTRY_CREATE);

        System.out.println("Watching directory: " + sourceDir);

        while (true) {
            WatchKey key = watchService.take();

            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();

                if (kind == OVERFLOW) continue;

                WatchEvent<Path> ev = (WatchEvent<Path>) event;
                Path fileName = ev.context();
                
                if (fileName.toString().endsWith(SCREENSHOT_EXT)) {
                    Path fullPath = sourceDir.resolve(fileName);
                    moveAndRename(fullPath, destDir);
                }
            }

            boolean valid = key.reset();
            
            if (!valid) {
                break;
            }
        }
    }

    private static void moveAndRename(Path source, Path destDir) {
        try {
            if (!Files.exists(destDir)) {
                Files.createDirectories(destDir);
                System.out.println("Created folder: " + destDir);
            }

            int count = getNextScreenshotNumber(destDir);
            String newFileName = "screenshot" + count + ".png";
            Path target = destDir.resolve(newFileName);

            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Moved and renamed: " + source.getFileName() + " → " + newFileName);

        } catch (IOException e) {
            System.err.println("Error moving file: " + e.getMessage());
        }
    }

    private static int getNextScreenshotNumber(Path dir) throws IOException {
        AtomicInteger max = new AtomicInteger(0);
        Files.list(dir)
            .filter(p -> p.getFileName().toString().startsWith("screenshot") && p.getFileName().toString().endsWith(".png"))
            .forEach(p -> {
                String name = p.getFileName().toString().replace("screenshot", "").replace(".png", "");
                try {
                    int num = Integer.parseInt(name.trim());
                    if (num > max.get()) {
                        max.set(num);
                    }
                } catch (NumberFormatException ignored) {}
            });
        return max.get() + 1;
    }
}
