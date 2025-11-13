package com.orangery.transform;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class XmlTransformerTest {

    private static final String INPUT_XML = "src/main/resources/flowers.xml";
    private static final String XSL_FILE = "src/main/resources/transform.xml";
    private static final String OUTPUT_XML = "src/test/resources/transformed.xml";

    @Test
    void testXmlTransformation() {
        XmlTransformer transformer = new XmlTransformer();

        // delete previous file if exists
        File out = new File(OUTPUT_XML);
        if (out.exists()) {
            out.delete();
        }

        boolean result = transformer.transform(INPUT_XML, XSL_FILE, OUTPUT_XML);

        assertTrue(result, "Transformer should return TRUE on success");

        assertTrue(out.exists(), "Output XML file must be created");

        assertTrue(out.length() > 10, "Output XML must not be empty");

        String content = readFileToString(out);

        assertTrue(content.contains("<A>") || content.contains("<a>"),
                "Transformed XML must contain tag <A>");

        assertTrue(content.contains("<B>") || content.contains("<b>"),
                "Transformed XML must contain tag <B>");

        assertTrue(content.contains("<C>") || content.contains("<c>"),
                "Transformed XML must contain tag <C>");
    }

    private String readFileToString(File f) {
        try {
            return new String(java.nio.file.Files.readAllBytes(f.toPath()));
        } catch (Exception e) {
            fail("Cannot read output file: " + e.getMessage());
            return null;
        }
    }
}