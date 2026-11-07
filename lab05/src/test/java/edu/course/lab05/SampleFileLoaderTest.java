package edu.course.lab05;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class SampleFileLoaderTest {

    @TempDir
    Path tempDir;

    private Path createTestFile(String filename, List<String> lines) throws IOException {
        Path file = tempDir.resolve(filename);
        Files.write(file, lines, StandardCharsets.UTF_8);
        return file;
    }

 
    @Test
    void testCorrectFileLoading() throws IOException {
        Path file = createTestFile("correct.csv", List.of(
            "id;label;source;featureCount;status",
            "S-101;cat;camera-1;128;READY",
            "S-102;dog;camera-2;256;PROCESSING"
        ));

        SampleFileLoader loader = new SampleFileLoader();
        LoadResult result = loader.load(file);

        assertTrue(result.getErrors().isEmpty());
        assertEquals(2, result.getSamples().size());
        assertEquals("S-101", result.getSamples().get(0).getId());
        assertEquals(SampleStatus.PROCESSING, result.getSamples().get(1).getStatus());
    }

    @Test
    void testUnicodeTextLoading() throws IOException {
        Path file = createTestFile("unicode.csv", List.of(
            "id;label;source;featureCount;status",
            "S-103;собака;камера-альфа;64;READY"
        ));

        SampleFileLoader loader = new SampleFileLoader();
        LoadResult result = loader.load(file);

        assertTrue(result.getErrors().isEmpty());
        assertEquals("собака", result.getSamples().get(0).getLabel());
        assertEquals("камера-альфа", result.getSamples().get(0).getSource());
    }

    @Test
    void testEmptyFieldValidation() throws IOException {
        Path file = createTestFile("empty_field.csv", List.of(
            "id;label;source;featureCount;status",
            "S-104;;camera-1;128;READY" // Пропущено поле label
        ));

        SampleFileLoader loader = new SampleFileLoader();
        LoadResult result = loader.load(file);

        assertEquals(0, result.getSamples().size());
        assertEquals(1, result.getErrors().size());
        assertEquals("fields", result.getErrors().get(0).getField());
        assertEquals(2, result.getErrors().get(0).getLineNumber());
    }

  
    @Test
    void testInvalidNumberValidation() throws IOException {
        Path file = createTestFile("bad_number.csv", List.of(
            "id;label;source;featureCount;status",
            "S-105;cat;camera-1;not_a_number;READY",
            "S-106;dog;camera-2;-5;READY" // Отрицательное число
        ));

        SampleFileLoader loader = new SampleFileLoader();
        LoadResult result = loader.load(file);

        assertEquals(0, result.getSamples().size());
        assertEquals(2, result.getErrors().size());
        assertEquals("featureCount", result.getErrors().get(0).getField());
        assertEquals("featureCount", result.getErrors().get(1).getField());
    }

    @Test
    void testUnknownStatusValidation() throws IOException {
        Path file = createTestFile("bad_status.csv", List.of(
            "id;label;source;featureCount;status",
            "S-107;cat;camera-1;128;UNKNOWN_STATE"
        ));

        SampleFileLoader loader = new SampleFileLoader();
        LoadResult result = loader.load(file);

        assertEquals(0, result.getSamples().size());
        assertEquals(1, result.getErrors().size());
        assertEquals("status", result.getErrors().get(0).getField());
    }

    @Test
    void testInvalidColumnCountValidation() throws IOException {
        Path file = createTestFile("bad_columns.csv", List.of(
            "id;label;source;featureCount;status",
            "S-108;cat;camera-1;128", // Пропущен столбец status
            "S-109;dog;camera-2;128;READY;extra_field" // Лишний столбец
        ));

        SampleFileLoader loader = new SampleFileLoader();
        LoadResult result = loader.load(file);

        assertEquals(0, result.getSamples().size());
        assertEquals(2, result.getErrors().size());
        assertEquals("columns", result.getErrors().get(0).getField());
    }

    @Test
    void testMixedRowsLoading() throws IOException {
        Path file = createTestFile("mixed.csv", List.of(
            "id;label;source;featureCount;status",
            "S-110;cat;camera-1;100;READY",         // Гуд
            "S-111;dog;camera-2;bad_number;READY",  // Битая
            "S-112;bird;camera-3;50;FAILED"         // Гуд
        ));

        SampleFileLoader loader = new SampleFileLoader();
        LoadResult result = loader.load(file);

        assertEquals(2, result.getSamples().size());
        assertEquals(1, result.getErrors().size());
        assertEquals(3, result.getErrors().get(0).getLineNumber()); // Ошибка на 3 строке файла
    }

    @Test
    void testMissingFileThrowsException() {
        Path nonExistentPath = tempDir.resolve("does_not_exist.csv");
        SampleFileLoader loader = new SampleFileLoader();

        DataLoadingException exception = assertThrows(DataLoadingException.class, () -> {
            loader.load(nonExistentPath);
        });

        assertNotNull(exception.getCause());
        assertTrue(exception.getMessage().contains("Ошибка чтения источника данных"));
    }
}
