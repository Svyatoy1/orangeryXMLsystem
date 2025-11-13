package com.orangery.parser;

import com.orangery.model.*;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DomParser {

    public List<Flower> parse(String fileName) {
        List<Flower> flowers = new ArrayList<>();

        try {
            InputStream is = Objects.requireNonNull(
                    getClass().getClassLoader().getResourceAsStream(fileName),
                    "File not found in resources: " + fileName
            );

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(is);

            NodeList nodeList = doc.getElementsByTagName("flower");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element e = (Element) nodeList.item(i);

                Flower f = new Flower();
                f.setId(e.getAttribute("id"));
                f.setName(getText(e, "name"));
                f.setOrigin(getText(e, "origin"));
                f.setSoil(Soil.fromString(getText(e, "soil")));
                f.setMultiplying(Multiplying.fromString(getText(e, "multiplying")));

                VisualParameters vp = new VisualParameters();
                Element vpElem = (Element) e.getElementsByTagName("visualParameters").item(0);
                vp.setStemColor(getText(vpElem, "stemColor"));
                vp.setLeafColor(getText(vpElem, "leafColor"));
                vp.setAverageSize(Integer.parseInt(getText(vpElem, "averageSize")));
                f.setVisualParameters(vp);

                GrowingTips tips = new GrowingTips();
                Element tipsElem = (Element) e.getElementsByTagName("growingTips").item(0);
                tips.setTemperature(Integer.parseInt(getText(tipsElem, "temperature")));
                tips.setLight(Boolean.parseBoolean(getText(tipsElem, "light")));
                tips.setWatering(Integer.parseInt(getText(tipsElem, "watering")));
                f.setGrowingTips(tips);

                flowers.add(f);
            }

            System.out.println("Parsed using DOM.");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return flowers;
    }

    private String getText(Element parent, String tag) {
        return parent.getElementsByTagName(tag).item(0).getTextContent();
    }
}