package org.wink.engine.converter;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author Inejka
 * @since 0.0.1
 */
public class GwfToScsConverter {
    private static final String PATH_TO_CONVERTER = "kb_scripts/gwf_to_scs.py";

    public static String convertToScs(String gwfText) throws IOException, InterruptedException {

        Process process;
        process = Runtime.getRuntime().exec(new String[]{"python3", PATH_TO_CONVERTER, gwfText});
        int exit_id = 0;
        exit_id = process.waitFor();
        InputStream stdout = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stdout, StandardCharsets.UTF_8));
        String line;
        StringBuilder toReturn = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            toReturn.append(line).append("\n");
        }
        stdout.close();
        reader.close();
        if (exit_id == 0) {
            return toReturn.toString();
        } else
            throw new RuntimeException(toReturn.toString());
    }
}