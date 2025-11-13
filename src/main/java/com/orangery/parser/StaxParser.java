package com.orangery.parser;

import com.orangery.model.*;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class StaxParser {

    public List<Flower> parse(String xmlPath) {

        List<Flower> flowers = new ArrayList<>();

        try {
            XMLStreamReader reader =
                    XMLInputFactory.newInstance().createXMLStreamReader(new FileInputStream(xmlPath));

            Flower flower = null;
            VisualParameters vp = null;
            GrowingTips gt = null;
            String tagName = null;

            while (reader.hasNext()) {
                int type = reader.next();

                switch (type) {

                    case XMLStreamConstants.START_ELEMENT:
                        tagName = reader.getLocalName();

                        switch (tagName) {
                            case "flower":
                                flower = new Flower();
                                flower.setId(reader.getAttributeValue(null, "id"));
                                break;
                            case "visualParameters":
                                vp = new VisualParameters();
                                break;
                            case "growingTips":
                                gt = new GrowingTips();
                                break;
                        }
                        break;

                    case XMLStreamConstants.CHARACTERS:
                        if (reader.isWhiteSpace() || tagName == null) break;

                        String text = reader.getText().trim();
                        if (text.isEmpty()) break;

                        switch (tagName) {

                            case "name":
                                flower.setName(text);
                                break;
                            case "soil":
                                flower.setSoil(Soil.valueOf(text.toUpperCase()));
                                break;
                            case "origin":
                                flower.setOrigin(text);
                                break;

                            case "stemColor":
                                vp.setStemColor(text);
                                break;
                            case "leafColor":
                                vp.setLeafColor(text);
                                break;
                            case "averageSize":
                                vp.setAverageSize(Integer.parseInt(text));
                                break;

                            case "temperature":
                                gt.setTemperature(Integer.parseInt(text));
                                break;
                            case "light":
                                gt.setLight(Boolean.parseBoolean(text));
                                break;
                            case "watering":
                                gt.setWatering(Integer.parseInt(text));
                                break;

                            case "multiplying":
                                flower.setMultiplying(Multiplying.valueOf(text.toUpperCase()));
                                break;
                        }
                        break;

                    case XMLStreamConstants.END_ELEMENT:
                        switch (reader.getLocalName()) {
                            case "visualParameters":
                                flower.setVisualParameters(vp);
                                break;
                            case "growingTips":
                                flower.setGrowingTips(gt);
                                break;
                            case "flower":
                                flowers.add(flower);
                                break;
                        }
                        tagName = null;
                        break;
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("StAX parsing error", e);
        }

        return flowers;
    }
}