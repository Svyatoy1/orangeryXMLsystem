package com.orangery.parser;

import com.orangery.model.*;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DomParser {

    public List<Flower> parse(String filePath) {
        List<Flower> flowers = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.setIgnoringElementContentWhitespace(true);

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(filePath));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("Flower");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element flowerElement = (Element) nodeList.item(i);
                flowers.add(buildFlower(flowerElement));
            }

        } catch (Exception e) {
            throw new RuntimeException("DOM parsing failed", e);
        }

        return flowers;
    }

    private Flower buildFlower(Element el) {
        Flower flower = new Flower();

        flower.setId(el.getAttribute("id"));
        flower.setName(getText(el, "Name"));
        flower.setOrigin(getText(el, "Origin"));
        flower.setSoil(Soil.valueOf(getText(el, "Soil")));

        // Visual parameters
        Element visual = (Element) el.getElementsByTagName("VisualParameters").item(0);
        VisualParameters vp = new VisualParameters(
                getText(visual, "StemColor"),
                getText(visual, "LeafColor"),
                Integer.parseInt(getText(visual, "AverageSize"))
        );
        flower.setVisualParameters(vp);

        // Growing tips
        Element tips = (Element) el.getElementsByTagName("GrowingTips").item(0);
        GrowingTips gt = new GrowingTips(
                Integer.parseInt(getText(tips, "Temperature")),
                getText(tips, "Light"),
                Integer.parseInt(getText(tips, "Watering"))
        );
        flower.setGrowingTips(gt);

        flower.setMultiplying(Multiplying.valueOf(getText(el, "Multiplying")));

        return flower;
    }

    private String getText(Element parent, String tag) {
        return parent.getElementsByTagName(tag).item(0).getTextContent();
    }
}