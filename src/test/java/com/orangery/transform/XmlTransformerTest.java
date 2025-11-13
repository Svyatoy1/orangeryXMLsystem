package com.orangery.transform;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

public class XmlTransformerTest {

    @TempDir
    File tempDir;

    @Test
    void testXsltTransformation() throws IOException {
        XmlTransformer transformer = new XmlTransformer();

        File output = new File(tempDir, "output.html");

        transformer.transform("flowers.xml", "transform.xml", output.getAbsolutePath());

        assertTrue(output.exists(), "Output HTML file must exist");

        String content = Files.readString(output.toPath());
        assertFalse(content.isBlank(), "Transformed HTML must not be empty");

        assertTrue(
                content.contains("<html>") || content.contains("<table>") || content.contains("<body>"),
                "Output must contain HTML content"
        );
    }
}