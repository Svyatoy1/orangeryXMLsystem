package com.orangery.parser;

import com.orangery.model.*;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.util.*;

public class SaxParser {

    public List<Flower> parse(String fileName) {

        List<Flower> flowers = new ArrayList<>();

        try {
            InputStream is = Objects.requireNonNull(
                    getClass().getClassLoader().getResourceAsStream(fileName),
                    "File not found in resources: " + fileName
            );

            SAXParserFactory factory = SAXParserFactory.newInstance();
            javax.xml.parsers.SAXParser parser = factory.newSAXParser();

            parser.parse(is, new DefaultHandler() {

                Flower flower = null;
                VisualParameters vp = null;
                GrowingTips tips = null;
                String currentTag = "";

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) {
                    currentTag = qName;

                    if (qName.equals("flower")) {
                        flower = new Flower();
                        flower.setId(attributes.getValue("id"));
                    }
                    if (qName.equals("visualParameters")) vp = new VisualParameters();
                    if (qName.equals("growingTips")) tips = new GrowingTips();
                }

                @Override
                public void characters(char[] ch, int start, int length) {
                    String text = new String(ch, start, length).trim();
                    if (text.isEmpty() || flower == null) return;

                    switch (currentTag) {
                        case "name" -> flower.setName(text);
                        case "origin" -> flower.setOrigin(text);
                        case "soil" -> flower.setSoil(Soil.fromString(text));
                        case "multiplying" -> flower.setMultiplying(Multiplying.fromString(text));

                        case "stemColor" -> vp.setStemColor(text);
                        case "leafColor" -> vp.setLeafColor(text);
                        case "averageSize" -> vp.setAverageSize(Integer.parseInt(text));

                        case "temperature" -> tips.setTemperature(Integer.parseInt(text));
                        case "light" -> tips.setLight(Boolean.parseBoolean(text));
                        case "watering" -> tips.setWatering(Integer.parseInt(text));
                    }
                }

                @Override
                public void endElement(String uri, String localName, String qName) {
                    if (qName.equals("visualParameters")) flower.setVisualParameters(vp);
                    if (qName.equals("growingTips")) flower.setGrowingTips(tips);
                    if (qName.equals("flower")) flowers.add(flower);
                }
            });

            System.out.println("Parsed using SAX.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return flowers;
    }
}