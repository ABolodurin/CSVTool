import writer.CSVWriter;

import java.util.List;

class Main {
    public static void main(String[] args) {
        CSVWriter<BusinessObject> writer = new CSVWriter<>();
        List<BusinessObject> list = List.of(
                new BusinessObject(1L, "name1", "desc1", 11, 134),
                new BusinessObject(2L, "name2", null, 22, 234),
                new BusinessObject(3L, "name3", "desc3", 33, 334),
                new BusinessObject(4L, "name4", "desc4", null, 434));
        writer.writeToFile(list, "D:\\temp\\file.csv");
    }

}
