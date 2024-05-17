package pdpOdev;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GitCloner {

    public static void cloneRepo(String repoLink) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("git", "clone", repoLink);
        processBuilder.directory(new File("."));
        Process process = processBuilder.start();
        process.waitFor();
    }
}
