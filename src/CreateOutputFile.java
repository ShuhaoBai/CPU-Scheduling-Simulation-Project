import java.io.*;

/**
 * Store output records in new created result file
 *
 * @author Shuhao Bai on 10/31/19
 */
public class CreateOutputFile {
    public static void createOutputFile(String result){
        Writer writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("outputReport.txt"), "utf-8"));
            writer.write(result);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
