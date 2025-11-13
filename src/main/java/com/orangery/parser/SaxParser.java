package com.orangery.parser;

import com.orangery.model.*;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SaxParser {

    public List<Flower> parse(String fileName) {
        List<Flower> flowers = new ArrayList<>();

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            javax.xml.parsers.SAXParser parser = factory.newSAXParser();

            parser.parse(new File(fileName), new DefaultHandler() {

                Flower flower;
                VisualParameters visual;
                GrowingTips tips;
                String text;

                @Override
                public void startElement(String uri, String local, String qName, Attributes attrs) {
                    switch (qName) {
                        case "flower":
                            flower = new Flower();
                            flower.setId(attrs.getValue("id"));
                            break;
                        case "visualParameters":
                            visual = new VisualParameters();
                            break;
                        case "growingTips":
                            tips = new GrowingTips();
                            break;
                    }
                }

                @Override
                public void characters(char[] ch, int start, int length) {
                    text = new String(ch, start, length).trim();
                }

                @Override
                public void endElement(String uri, String localName, String qName) {
                    if (text != null && !text.isEmpty()) {
                        switch (qName) {
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

                    switch (qName) {
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
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return flowers;
    }
}