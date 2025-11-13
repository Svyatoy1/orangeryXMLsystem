package com.orangery.parser;

import com.orangery.model.*;

import javax.xml.stream.*;
import java.io.InputStream;
import java.util.*;

public class StaxParser {

    public List<Flower> parse(String fileName) {
        List<Flower> list = new ArrayList<>();

        try {
            InputStream is = Objects.requireNonNull(
                    getClass().getClassLoader().getResourceAsStream(fileName),
                    "File not found in resources: " + fileName
            );

            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader reader = factory.createXMLStreamReader(is);

            Flower flower = null;
            VisualParameters vp = null;
            GrowingTips tips = null;

            while (reader.hasNext()) {
                int type = reader.next();

                if (type == XMLStreamConstants.START_ELEMENT) {
                    String tag = reader.getLocalName();

                    switch (tag) {
                        case "flower" -> {
                            flower = new Flower();
                            flower.setId(reader.getAttributeValue(null, "id"));
                        }
                        case "visualParameters" -> vp = new VisualParameters();
                        case "growingTips" -> tips = new GrowingTips();
                        case "name" -> flower.setName(reader.getElementText());
                        case "origin" -> flower.setOrigin(reader.getElementText());
                        case "soil" -> flower.setSoil(Soil.fromString(reader.getElementText()));
                        case "multiplying" -> flower.setMultiplying(Multiplying.fromString(reader.getElementText()));

                        case "stemColor" -> vp.setStemColor(reader.getElementText());
                        case "leafColor" -> vp.setLeafColor(reader.getElementText());
                        case "averageSize" -> vp.setAverageSize(Integer.parseInt(reader.getElementText()));

                        case "temperature" -> tips.setTemperature(Integer.parseInt(reader.getElementText()));
                        case "light" -> tips.setLight(Boolean.parseBoolean(reader.getElementText()));
                        case "watering" -> tips.setWatering(Integer.parseInt(reader.getElementText()));
                    }

                } else if (type == XMLStreamConstants.END_ELEMENT) {
                    switch (reader.getLocalName()) {
                        case "visualParameters" -> flower.setVisualParameters(vp);
                        case "growingTips" -> flower.setGrowingTips(tips);
                        case "flower" -> list.add(flower);
                    }
                }
            }

            System.out.println("Parsed using StAX.");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}