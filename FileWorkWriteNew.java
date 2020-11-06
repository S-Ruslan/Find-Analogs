import java.io.*;
import java.util.Map;

public class FileWorkWriteNew {
    private String fileName = "result.txt";

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    //стандартная запись в фаил обновленного списка аналогов
    public FileWorkWriteNew(Map<String, String[]> analogs) throws IOException {
        fileName = System.getProperty("user.dir") + System.getProperty("file.separator") + fileName;
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(fileName),"CP1251");
        BufferedWriter writer = new BufferedWriter(osw);
        for (String key : analogs.keySet()) {
            String format = analogs.get(key)[0] + "\t" + analogs.get(key)[1];
            writer.write(key +"\t" + format + "\n");
        }
        writer.close();
        osw.close();
    }
}
