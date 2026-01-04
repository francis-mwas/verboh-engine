package com.coop.demo.coopVerboh.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AudioUtils {

    // Convert MultipartFile to byte[]
    public static byte[] toByteArray(MultipartFile file) throws IOException {
        try (InputStream is = file.getInputStream();
             ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
            int nRead;
            byte[] data = new byte[16384];
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            return buffer.toByteArray();
        }
    }

    // TODO: Add audio format conversion helpers if needed
}
