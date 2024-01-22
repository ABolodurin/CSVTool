package ru.bolodurin.csvtool.writer;

import ru.bolodurin.csvtool.exception.CSVWriterException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class CSVWriter<T> {
    private final AnnotationHandler handler = AnnotationHandler.getInstance();

    public void writeToFile(List<T> entities, String pathToFile) {
        Class<?> entityClass = checkEntities(entities);
        File file = createFile(pathToFile);

        Field[] fields = handler.getTargetFields(entityClass);

        try (FileWriter writer = new FileWriter(file)) {
            for (T entity : entities) {
                writer.write(getResultingRowForEntity(entity, fields));
            }
        } catch (IOException e) {
            throw new CSVWriterException("Can not write file: " + e.getMessage());
        }
    }

    private File createFile(String path) {
        File file = new File(path);

        try {
            if (!file.createNewFile()) throw new CSVWriterException("Can not create file: " + path);
        } catch (IOException e) {
            throw new CSVWriterException("Can not create file: " + path);
        }

        return file;
    }

    private String getResultingRowForEntity(T t, Field[] fields) {
        StringBuilder builder = new StringBuilder();

        try {
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                builder.append(fields[i].get(t)).append(";");
            }

            builder.setCharAt(builder.length() - 1, '\n');
        } catch (IllegalAccessException e) {
            throw new CSVWriterException(e.getMessage());
        }

        return builder.toString();
    }

    private Class<?> checkEntities(List<?> entities) {
        if (entities.isEmpty()) throw new CSVWriterException("Can not write to file an empty list: " + entities);
        Class<?> c = entities.get(0).getClass();

        for (int i = 1; i < entities.size(); i++) {
            if (entities.get(i).getClass() != c)
                throw new CSVWriterException("Not same instance for entities: " + entities);
        }

        return c;
    }

}
