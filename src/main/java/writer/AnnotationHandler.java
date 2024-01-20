package writer;

import annotation.CSVField;
import exception.CSVWriterException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

final class AnnotationHandler {
    private static AnnotationHandler instance;

    private AnnotationHandler() {
    }

    static AnnotationHandler getInstance(){
        if (instance == null) return new AnnotationHandler();
        return instance;
    }

    Field[] getTargetFields(Class<?> entityClass) {
        Field[] allFields = entityClass.getDeclaredFields();
        List<Field> resultList = new ArrayList<>();

        for (Field field : allFields) {
            if (field.isAnnotationPresent(CSVField.class)) resultList.add(field);
        }

        if (resultList.isEmpty()) throw new CSVWriterException("No annotated fields found for class: " + entityClass);

        return resultList.toArray(new Field[0]);
    }

}
