package com.orangery.parser;

import com.orangery.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DomParser {

    private static final Logger logger = LoggerFactory.getLogger(DomParser.class);

    public List<Flower> parse(String filePath) {
        logger.info("DOM parsing started: {}", filePath);

        List<Flower> flowers = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(filePath));

            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("flower");
            logger.info("Found {} <flower> nodes", list.getLength());

            for (int i = 0; i < list.getLength(); i++) {
                Element element = (Element) list.item(i);

                Flower flower = new Flower();
                flower.setId(element.getAttribute("id"));
                flower.setName(getText(element, "name"));
                flower.setSoil(getText(element, "soil"));
                flower.setOrigin(getText(element, "origin"));

                // visual parameters
                Element visual = (Element) element.getElementsByTagName("visualParameters").item(0);
                VisualParameters vp = new VisualParameters();
                vp.setStemColor(getText(visual, "stemColor"));
                vp.setLeafColor(getText(visual, "leafColor"));
                vp.setAverageSize(Integer.parseInt(getText(visual, "averageSize")));
                flower.setVisualParameters(vp);

                // growing tips
                Element tipsEl = (Element) element.getElementsByTagName("growingTips").item(0);
                GrowingTips tips = new GrowingTips();
                tips.setTemperature(Integer.parseInt(getText(tipsEl, "temperature")));
                tips.setLight(Boolean.parseBoolean(getText(tipsEl, "light")));
                tips.setWatering(Integer.parseInt(getText(tipsEl, "watering")));
                flower.setGrowingTips(tips);

                flower.setMultiplying(getText(element, "multiplying"));

                flowers.add(flower);
            }

            logger.info("DOM parsing finished successfully.");
        } catch (Exception e) {
            logger.error("DOM parsing error: {}", e.getMessage());
        }

        return flowers;
    }

    private String getText(Element parent, String tag) {
        return parent.getElementsByTagName(tag).item(0).getTextContent();
    }
}