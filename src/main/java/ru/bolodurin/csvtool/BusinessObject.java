package ru.bolodurin.csvtool;

import ru.bolodurin.csvtool.annotation.CSVEntity;
import ru.bolodurin.csvtool.annotation.CSVField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@CSVEntity
@Getter
@Setter
@AllArgsConstructor
public class BusinessObject {
        @CSVField
        private long id;
        @CSVField
        private String name;
        @CSVField
        private String description;
        @CSVField
        private Integer serialNumber;
        private int nonBusinessField;

}
