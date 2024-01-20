import annotation.CSVEntity;
import annotation.CSVField;
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
