package ru.bolodurin.csvtool.writer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.bolodurin.csvtool.BusinessObject;
import ru.bolodurin.csvtool.exception.CSVWriterException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CSVWriterTest {
    private CSVWriter<BusinessObject> writer;

    @BeforeEach
    void init() {
        writer = new CSVWriter<>();
    }

    @Test
    void writeToFile() throws FileNotFoundException {
        BusinessObject object = new BusinessObject(
                1L, "name", "description", 123, 22);
        List<BusinessObject> list = List.of(object, object);

        String existingPath = "D:\\temp\\file.csv";

        writer.writeToFile(list, existingPath);
        String expectedLine =
                object.getId() + ";" + object.getName() + ";" + object.getDescription() + ";" + object.getSerialNumber();

        try (BufferedReader reader = new BufferedReader(new FileReader(existingPath))) {
            while (reader.ready()) {
                String actualLine = reader.readLine();
                assertEquals(actualLine, expectedLine);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //file already exists
        Exception exception = assertThrows(
                CSVWriterException.class, () -> writer.writeToFile(list, existingPath));
        assertTrue(exception.getMessage().contains("Can not create file: "));

        File file = new File(existingPath);
        assertTrue(file.delete());

        String nonExistingPath = "";
        exception = assertThrows(
                CSVWriterException.class, () -> writer.writeToFile(list, nonExistingPath));
        assertTrue(exception.getMessage().contains("Can not create file: "));

        exception = assertThrows(
                CSVWriterException.class, () -> writer.writeToFile(new ArrayList<>(), existingPath));
        assertTrue(exception.getMessage().contains("Can not write to file an empty list: "));

        CSVWriter<Object> otherWriter = new CSVWriter<>();

        exception = assertThrows(
                CSVWriterException.class, () -> otherWriter.writeToFile(List.of(object, new Object()), existingPath));
        assertTrue(exception.getMessage().contains("Not same instance for entities: "));

        exception = assertThrows(
                CSVWriterException.class, () -> otherWriter.writeToFile(List.of("whatever", "whatever"), existingPath));
        assertTrue(exception.getMessage().contains("No annotated fields found for class: "));
    }

}
