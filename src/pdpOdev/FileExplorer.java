package pdpOdev;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileExplorer {

	public static void fileResearch(File directory) {
        explore(directory);
    }

    private static void explore(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    explore(file);
                } else if (file.getName().endsWith(".java") && containsClassKeyword(file)) {
                    FileAnalyzer.analyzeFile(file);
                }
            }
        }
    }

    public static boolean containsClassKeyword(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("class")) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
