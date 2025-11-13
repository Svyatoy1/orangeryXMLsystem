package com.orangery.parser;

import com.orangery.model.*;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DomParser {

    public List<Flower> parse(String filePath) {
        List<Flower> flowers = new ArrayList<>();

        try {
            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(new File(filePath));

            doc.getDocumentElement().normalize();
            NodeList nodes = doc.getElementsByTagName("flower");

            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                Flower flower = new Flower();

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element el = (Element) node;

                    flower.setName(getText(el, "name"));
                    flower.setOrigin(getText(el, "origin"));
                    flower.setSoil(Soil.valueOf(getText(el, "soil").toUpperCase()));
                    flower.setMultiplying(Multiplying.valueOf(getText(el, "multiplying").toUpperCase()));

                    // Visual
                    VisualParameters vp = new VisualParameters();
                    Element visual = (Element) el.getElementsByTagName("visualParameters").item(0);

                    vp.setStemColor(getText(visual, "stemColor"));
                    vp.setLeafColor(getText(visual, "leafColor"));
                    vp.setAverageSize(Integer.parseInt(getText(visual, "averageSize")));
                    flower.setVisualParameters(vp);

                    // Growing tips
                    GrowingTips tips = new GrowingTips();
                    Element grow = (Element) el.getElementsByTagName("growingTips").item(0);

                    tips.setTemperature(Integer.parseInt(getText(grow, "temperature")));
                    tips.setLight(Boolean.parseBoolean(getText(grow, "light")));
                    tips.setWatering(Integer.parseInt(getText(grow, "watering")));
                    flower.setGrowingTips(tips);

                    flowers.add(flower);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return flowers;
    }

    private String getText(Element parent, String tag) {
        return parent.getElementsByTagName(tag).item(0).getTextContent().trim();
    }
}