package com.orangery.parser;

import com.orangery.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SaxParser {

    private static final Logger logger = LoggerFactory.getLogger(SaxParser.class);

    public List<Flower> parse(String filePath) {
        logger.info("SAX parsing started: {}", filePath);

        List<Flower> flowers = new ArrayList<>();

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {

                Flower flower;
                VisualParameters vp;
                GrowingTips tips;
                StringBuilder data = new StringBuilder();

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) {
                    data.setLength(0);
                    logger.debug("Start element: {}", qName);

                    switch (qName) {
                        case "flower":
                            flower = new Flower();
                            flower.setId(attributes.getValue("id"));
                            break;
                        case "visualParameters":
                            vp = new VisualParameters();
                            break;
                        case "growingTips":
                            tips = new GrowingTips();
                            break;
                    }
                }

                @Override
                public void characters(char[] ch, int start, int length) {
                    data.append(ch, start, length);
                }

                @Override
                public void endElement(String uri, String localName, String qName) {
                    logger.debug("End element: {}", qName);

                    switch (qName) {
                        case "name" -> flower.setName(data.toString());
                        case "soil" -> flower.setSoil(data.toString());
                        case "origin" -> flower.setOrigin(data.toString());

                        case "stemColor" -> vp.setStemColor(data.toString());
                        case "leafColor" -> vp.setLeafColor(data.toString());
                        case "averageSize" -> vp.setAverageSize(Integer.parseInt(data.toString()));

                        case "temperature" -> tips.setTemperature(Integer.parseInt(data.toString()));
                        case "light" -> tips.setLight(Boolean.parseBoolean(data.toString()));
                        case "watering" -> tips.setWatering(Integer.parseInt(data.toString()));

                        case "multiplying" -> flower.setMultiplying(data.toString());

                        case "visualParameters" -> flower.setVisualParameters(vp);
                        case "growingTips" -> flower.setGrowingTips(tips);

                        case "flower" -> flowers.add(flower);
                    }
                }
            };

            parser.parse(new File(filePath), handler);

            logger.info("SAX parsing finished successfully.");
        } catch (Exception e) {
            logger.error("SAX parsing error: {}", e.getMessage());
        }

        return flowers;
    }
}