package org.wink.engine.converter;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author Diana
 * @since 0.0.1
 */
public class GwfToScsConverter {
    public static String convertToScs(String gwfText) throws IOException {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("temp_file.gwf"), StandardCharsets.UTF_8))) {
            writer.write(gwfText);
        }
        try {
            Process process;
            process = Runtime.getRuntime().exec(new String[]{"python3", "converter.py", "arg1", "arg2"});
            InputStream stdout = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stdout, StandardCharsets.UTF_8));
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    System.out.println("stdout: " + line);
                }
            } catch (IOException e) {
                System.out.println("Exception in reading output" + e.toString());
            }
        } catch (Exception e) {
            System.out.println("Exception Raised" + e.toString());
        }

        return "";
    }
}
