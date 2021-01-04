package tools;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileTools {

    private FileTools() {
        //vazio
    }

    public static String getStringByFilePath(String path) throws IOException {
        Logger logger = Logger.getLogger("Leitor de arquivos");
        String msg = "lendo path: " + path;
        logger.log(Level.INFO, msg);

        File file = new File(path);

        msg = "nome do arquivo: " + file.getName();
        logger.log(Level.INFO, msg);

        return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
    }
}
