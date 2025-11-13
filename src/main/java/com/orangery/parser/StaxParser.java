package com.orangery.parser;

import com.orangery.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.xml.stream.*;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class StaxParser {

    private static final Logger logger = LoggerFactory.getLogger(StaxParser.class);

    public List<Flower> parse(String filePath) {
        logger.info("StAX parsing started: {}", filePath);

        List<Flower> flowers = new ArrayList<>();

        try (FileInputStream input = new FileInputStream(filePath)) {

            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader reader = factory.createXMLStreamReader(input);

            Flower flower = null;
            VisualParameters vp = null;
            GrowingTips tips = null;
            String currentTag = null;

            while (reader.hasNext()) {
                int event = reader.next();

                switch (event) {

                    case XMLStreamConstants.START_ELEMENT:
                        currentTag = reader.getLocalName();
                        logger.debug("Start element: {}", currentTag);

                        if (currentTag.equals("flower")) {
                            flower = new Flower();
                            flower.setId(reader.getAttributeValue(null, "id"));
                        }
                        if (currentTag.equals("visualParameters")) {
                            vp = new VisualParameters();
                        }
                        if (currentTag.equals("growingTips")) {
                            tips = new GrowingTips();
                        }
                        break;

                    case XMLStreamConstants.CHARACTERS:
                        if (reader.isWhiteSpace() || currentTag == null) break;

                        String text = reader.getText().trim();
                        if (text.isEmpty()) break;

                        switch (currentTag) {
                            case "name" -> flower.setName(text);
                            case "soil" -> flower.setSoil(text);
                            case "origin" -> flower.setOrigin(text);

                            case "stemColor" -> vp.setStemColor(text);
                            case "leafColor" -> vp.setLeafColor(text);
                            case "averageSize" -> vp.setAverageSize(Integer.parseInt(text));

                            case "temperature" -> tips.setTemperature(Integer.parseInt(text));
                            case "light" -> tips.setLight(Boolean.parseBoolean(text));
                            case "watering" -> tips.setWatering(Integer.parseInt(text));

                            case "multiplying" -> flower.setMultiplying(text);
                        }
                        break;

                    case XMLStreamConstants.END_ELEMENT:
                        String tag = reader.getLocalName();
                        logger.debug("End element: {}", tag);

                        if (tag.equals("visualParameters"))
                            flower.setVisualParameters(vp);

                        if (tag.equals("growingTips"))
                            flower.setGrowingTips(tips);

                        if (tag.equals("flower"))
                            flowers.add(flower);

                        currentTag = null;
                        break;
                }
            }

            logger.info("StAX parsing finished successfully.");

        } catch (Exception e) {
            logger.error("StAX parsing error: {}", e.getMessage());
        }

        return flowers;
    }
}