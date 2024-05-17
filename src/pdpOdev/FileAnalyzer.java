package pdpOdev;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileAnalyzer {

    public static void analyzeFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            int javadocComment = 0;
            int totalComment = 0;
            int CodeLine = 0;
            int LOC = 0;
            int functionCount = 0;
            int emptyLine = 0;

            String line;
            boolean inCommentBlock = false;
            boolean inJavadocCommentBlock = false;

            Pattern commentPattern = Pattern.compile("//.*");
            Pattern functionPattern = Pattern.compile("(public|private|static)?\\s+\\w+\\s+\\w+\\([^)]*\\)");
            Pattern functionPattern2 = Pattern.compile("(public|private)?\\s+\\w+\\s+\\w+\\([^)]*\\)");
            Pattern functionPattern3 = Pattern.compile("\\w+\\s+\\w+\\s+\\w+\\([^)]*\\)");

            while ((line = reader.readLine()) != null) {
                LOC++;
                if (line.trim().isEmpty()) {
                    emptyLine++;
                }
                Matcher matcher = commentPattern.matcher(line);
                if (matcher.find()) {
                    totalComment++;
                }
                if (!(line.trim().startsWith("/*") || line.trim().startsWith("*") || line.trim().startsWith("/**") || line.trim().startsWith("*/") || line.trim().startsWith("//"))) {
                    CodeLine++;
                }

                if (line.trim().startsWith("/**")) {
                    inJavadocCommentBlock = true;
                    javadocComment--;
                }
                if (inJavadocCommentBlock && line.trim().startsWith("*")) {
                    javadocComment++;
                }
                if (line.trim().endsWith("*/")) {
                    inJavadocCommentBlock = false;
                }

                if (line.trim().startsWith("/*")) {
                    inCommentBlock = true;
                    totalComment--;
                }
                if (inCommentBlock) {
                    totalComment++;
                }
                if (line.trim().endsWith("*/")) {
                    inCommentBlock = false;
                    totalComment--;
                }
                Matcher matcher2 = functionPattern.matcher(line);
                Matcher matcher3 = functionPattern2.matcher(line);
                Matcher matcher4 = functionPattern3.matcher(line);
                if (matcher2.find() || matcher3.find() || matcher4.find()) {
                    functionCount++;
                }
            }
            double YG = (((javadocComment + (totalComment - javadocComment))) * 0.8) / (double) functionCount;
            double YH = ((CodeLine - emptyLine) / (double) functionCount) * 0.3;
            double CommentDeflection = ((100 * YG) / YH) - 100;
            DecimalFormat afterDot = new DecimalFormat("#.##");

            System.out.println("Dosya Adı: " + file.getName());
            System.out.println("Javadoc Yorum Sayisi: " + (javadocComment));
            System.out.println("Yorum Satiri Sayisi: " + (totalComment - javadocComment));
            System.out.println("Kod Satir sayisi: " + (CodeLine - emptyLine));
            System.out.println("LOC: " + LOC);
            System.out.println("Fonksiyon Sayısı: " + functionCount);
            System.out.println("Yorum Sapma Yuzdesi:%" + afterDot.format(CommentDeflection));
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
