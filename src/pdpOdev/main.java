package pdpOdev;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Lutfen GitHub reposu linkini girin: ");
            String repoLink = scanner.nextLine();

            GitCloner.cloneRepo(repoLink);

            String repoName = repoLink.substring(repoLink.lastIndexOf('/') + 1);
            File repoDir = new File(repoName);

            FileExplorer.fileResearch(repoDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
