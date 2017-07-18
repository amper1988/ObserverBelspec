package model;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileManager {
    public static File createPdfFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String pdfFileName = "PDF_" + timeStamp + "_";
        String property = "java.io.tmpdir";
        String tempDir = System.getProperty(property);
        File dir = new File(tempDir);
        File pdfFile = File.createTempFile(
                pdfFileName,  /* prefix */
                ".pdf",         /* suffix */
                dir     /* directory */
        );
        return pdfFile;
    }

    public static File createExeFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String exeFileName = "Exe_" + timeStamp + "_";
        String property = "java.io.tmpdir";
        String tempDir = System.getProperty(property);
        File dir = new File(tempDir);
        File exeFile = File.createTempFile(
                exeFileName,  /* prefix */
                ".exe",         /* suffix */
                dir     /* directory */
        );
        return exeFile;
    }
}
