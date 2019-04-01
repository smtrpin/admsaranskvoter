package com.simbirsoft.voter.voter.api.anticaptcha.helper;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StringHelper {
    public static String toCamelCase(String s) {
        String[] parts = s.split("_");
        String camelCaseString = "";

        for (String part : parts) {
            camelCaseString += part.substring(0, 1).toUpperCase() + part.substring(1).toLowerCase();
        }

        return camelCaseString.substring(0, 1).toLowerCase() + camelCaseString.substring(1);
    }

    public static String imageFileToBase64String(String path) {
        try {
            return DatatypeConverter.printBase64Binary(Files.readAllBytes(Paths.get(path)));
        } catch (Exception e) {
            return null;
        }
    }

    public static String imageFileToBase64String(InputStream inputStream) {
        try {
            byte[] bytes = IOUtils.toByteArray(inputStream);
            return Base64.encodeBase64String(bytes);
        } catch (IOException e) {
            return null;
        }
    }
}
