package com.orangery.parser;

import com.orangery.model.*;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DomParser {

    public List<Flower> parse(String fileName) {
        List<Flower> flowers = new ArrayList<>();

        try {
            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(new File(fileName));

            NodeList nodes = doc.getElementsByTagName("flower");

            for (int i = 0; i < nodes.getLength(); i++) {
                Element el = (Element) nodes.item(i);

                Flower flower = new Flower();
                flower.setId(el.getAttribute("id"));

                flower.setName(getText(el, "name"));
                flower.setSoil(Soil.valueOf(getText(el, "soil")));
                flower.setOrigin(getText(el, "origin"));

                // visual parameters
                Element visualEl = (Element) el.getElementsByTagName("visualParameters").item(0);
                VisualParameters visual = new VisualParameters();
                visual.setStemColor(getText(visualEl, "stemColor"));
                visual.setLeafColor(getText(visualEl, "leafColor"));
                visual.setAverageSize(Integer.parseInt(getText(visualEl, "averageSize")));
                flower.setVisualParameters(visual);

                // growing tips
                Element tipsEl = (Element) el.getElementsByTagName("growingTips").item(0);
                GrowingTips tips = new GrowingTips();
                tips.setTemperature(Integer.parseInt(getText(tipsEl, "temperature")));
                tips.setLight(Boolean.parseBoolean(getText(tipsEl, "light")));
                tips.setWatering(Integer.parseInt(getText(tipsEl, "watering")));
                flower.setGrowingTips(tips);

                // multiplying
                flower.setMultiplying(Multiplying.valueOf(getText(el, "multiplying")));

                flowers.add(flower);
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