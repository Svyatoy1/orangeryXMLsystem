package com.orangery.parser;

import com.orangery.model.*;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class StaxParser {

    public List<Flower> parse(String fileName) {
        List<Flower> flowers = new ArrayList<>();

        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader reader =
                    factory.createXMLStreamReader(new FileInputStream(fileName));

            Flower flower = null;
            VisualParameters visual = null;
            GrowingTips tips = null;
            String text = null;

            while (reader.hasNext()) {
                switch (reader.next()) {

                    case XMLStreamConstants.START_ELEMENT:
                        switch (reader.getLocalName()) {
                            case "flower":
                                flower = new Flower();
                                flower.setId(reader.getAttributeValue(null, "id"));
                                break;
                            case "visualParameters":
                                visual = new VisualParameters();
                                break;
                            case "growingTips":
                                tips = new GrowingTips();
                                break;
                        }
                        break;

                    case XMLStreamConstants.CHARACTERS:
                        text = reader.getText().trim();
                        break;

                    case XMLStreamConstants.END_ELEMENT:
                        String name = reader.getLocalName();

                        if (text != null && !text.isEmpty()) {
                            switch (name) {
                                case "name":
                                    flower.setName(text);
                                    break;
                                case "soil":
                                    flower.setSoil(Soil.valueOf(text));
                                    break;
                                case "origin":
                                    flower.setOrigin(text);
                                    break;

                                case "stemColor":
                                    visual.setStemColor(text);
                                    break;
                                case "leafColor":
                                    visual.setLeafColor(text);
                                    break;
                                case "averageSize":
                                    visual.setAverageSize(Integer.parseInt(text));
                                    break;

                                case "temperature":
                                    tips.setTemperature(Integer.parseInt(text));
                                    break;
                                case "light":
                                    tips.setLight(Boolean.parseBoolean(text));
                                    break;
                                case "watering":
                                    tips.setWatering(Integer.parseInt(text));
                                    break;

                                case "multiplying":
                                    flower.setMultiplying(Multiplying.valueOf(text));
                                    break;
                            }
                        }

                        switch (name) {
                            case "visualParameters":
                                flower.setVisualParameters(visual);
                                break;
                            case "growingTips":
                                flower.setGrowingTips(tips);
                                break;
                            case "flower":
                                flowers.add(flower);
                                break;
                        }

                        text = null;
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return flowers;
    }
}