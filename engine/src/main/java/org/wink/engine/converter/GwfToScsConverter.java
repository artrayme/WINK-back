package org.wink.engine.converter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class GwfToScsConverter {
    public static void main(String[] args) {
        convertToScs("");
    }

    public static String convertToScs(String gwfText) {
        try {
            Process process;
            process = Runtime.getRuntime().exec(new String[]{"python3", "test.py", "arg1", "arg2"});
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
