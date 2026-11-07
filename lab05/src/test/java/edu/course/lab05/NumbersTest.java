
package edu.course.lab05;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class NumbersTest {

    @TempDir
    Path tempDir;

    @Test
    void testReadAndParseNumbers() throws IOException {
        Path file = tempDir.resolve("numbers.txt");
        Files.write(file, List.of("10", "bad", "20"), StandardCharsets.UTF_8);
        List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
        assertEquals(3, lines.size());
        assertEquals("10", lines.get(0));
        assertEquals("bad", lines.get(1));


        List<Integer> parsedNumbers = new ArrayList<>();
        int errorsCount = 0;

        for (String line : lines) {
            try {
                parsedNumbers.add(Integer.parseInt(line));
            } catch (NumberFormatException e) {
                errorsCount++;
            }
        }

        assertEquals(2, parsedNumbers.size());
        assertEquals(10, parsedNumbers.get(0));
        assertEquals(20, parsedNumbers.get(1));
        assertEquals(1, errorsCount);
    }

    @Test
    void testBadStringThrowsNumberFormatException() throws IOException {
        Path file = tempDir.resolve("single_bad.txt");
        Files.write(file, List.of("bad"), StandardCharsets.UTF_8);

        List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
        
        assertThrows(NumberFormatException.class, () -> {
            Integer.parseInt(lines.get(0));
        });
    }
}
