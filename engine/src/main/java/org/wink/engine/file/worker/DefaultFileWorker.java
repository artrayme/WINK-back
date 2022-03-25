package org.wink.engine.file.worker;

import org.wink.engine.Config;

import java.io.*;
import java.util.*;

/**
 * @author Inejka
 * @since 0.0.1
 */
public class DefaultFileWorker implements FileWorker {
    private String pathToWinkConf;
    private static final Set<String> EXTENSIONS = new HashSet<>(Arrays.asList("scs", "gwf"));
    private String pathToKB;

    public DefaultFileWorker() throws IOException {
        pathToKB = Config.getProperty("kb.path");
    }

    @Override
    public void write(String fileName, ByteArrayOutputStream outputStream) throws IOException {
        FileOutputStream fos = new FileOutputStream(pathToKB + "/" + fileName);
        outputStream.writeTo(fos);
        fos.close();
    }

    @Override
    public ByteArrayOutputStream read(String fileName) throws IOException {
        FileInputStream fis = new FileInputStream(pathToKB + "/" + fileName);
        ByteArrayOutputStream toReturn = new ByteArrayOutputStream();
        toReturn.write(fis.readAllBytes());
        fis.close();
        return toReturn;
    }

    @Override
    public List<String> getFilesList() {
        File root = new File(pathToKB);
        return recursiveDirTravel(root);
    }

    private List<String> recursiveDirTravel(File dir) {
        LinkedList<String> toReturn = new LinkedList<>();
        for (File i : dir.listFiles()) {
            if (i.isDirectory()) {
                toReturn.addAll(recursiveDirTravel(i));
            } else {
                if (checkFileExtension(i.getName()))
                    toReturn.add(i.getAbsolutePath().substring(pathToKB.length() + 1 +
                            i.getAbsolutePath().lastIndexOf(pathToKB)));
            }
        }
        return toReturn;
    }

    private boolean checkFileExtension(String fileName) {
        return EXTENSIONS.contains(fileName.substring(1 + fileName.lastIndexOf('.')));
    }


}
