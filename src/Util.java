import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

class Util {
    public static ArrayList<String> getInputFileLines(String fileName) {
        ArrayList<String> lines = new ArrayList<String>();
        try {
            Files.lines(Paths.get("./input/" + fileName)).forEach(lines::add);
        }  catch (IOException e) {
            System.out.println("Error reading input file");
        }
        return lines;
    }
}
