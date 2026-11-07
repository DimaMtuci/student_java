package edu.course.lab05;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class SampleFileLoader {

    public LoadResult load(Path path) {
        if (path == null) {
            throw new IllegalArgumentException("Путь к файлу не может быть null");
        }

        List<DataSample> samples = new ArrayList<>();
        List<ValidationError> errors = new ArrayList<>();

        
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String header = reader.readLine();
            int lineNumber = 1;

            if (header == null) {
                throw new DataLoadingException("Файл пуст или отсутствует заголовок", null);
            }

            
            if (!header.trim().equals("id;label;source;featureCount;status")) {
                throw new DataLoadingException("Неверный формат заголовка файла", null);
            }

            String line;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                
                
                if (line.isBlank()) {
                    continue;
                }

                String[] tokens = line.split(";", -1); 

                
                if (tokens.length != 5) {
                    errors.add(new ValidationError(lineNumber, "columns", "Ожидалось 5 столбцов, получено: " + tokens.length));
                    continue;
                }

                String id = tokens[0].trim();
                String label = tokens[1].trim();
                String source = tokens[2].trim();
                String featureCountStr = tokens[3].trim();
                String statusStr = tokens[4].trim();

               
                if (id.isEmpty() || label.isEmpty() || source.isEmpty() || featureCountStr.isEmpty() || statusStr.isEmpty()) {
                    errors.add(new ValidationError(lineNumber, "fields", "Строка содержит пустые поля"));
                    continue;
                }

            
                int featureCount;
                try {
                    featureCount = Integer.parseInt(featureCountStr);
                    if (featureCount <= 0) {
                        errors.add(new ValidationError(lineNumber, "featureCount", "Количество признаков должно быть больше нуля"));
                        continue;
                    }
                } catch (NumberFormatException e) {
                    errors.add(new ValidationError(lineNumber, "featureCount", "Неверный формат числа: " + featureCountStr));
                    continue;
                }

                SampleStatus status;
                try {
                    status = SampleStatus.valueOf(statusStr);
                } catch (IllegalArgumentException e) {
                    errors.add(new ValidationError(lineNumber, "status", "Неизвестный статус: " + statusStr));
                    continue;
                }

                samples.add(new DataSample(id, label, source, featureCount, status));
            }

        } catch (IOException e) {
            throw new DataLoadingException("Ошибка чтения источника данных: " + path, e);
        }

        return new LoadResult(samples, errors);
    }
}
