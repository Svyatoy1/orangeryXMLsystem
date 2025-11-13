package com.orangery.parser;

import com.orangery.model.*;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParserFactory;
import java.util.ArrayList;
import java.util.List;

public class SaxParser {

    public List<Flower> parse(String xmlPath) {
        List<Flower> flowers = new ArrayList<>();

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            javax.xml.parsers.SAXParser parser = factory.newSAXParser();

            FlowerHandler handler = new FlowerHandler(flowers);
            parser.parse(xmlPath, handler);

        } catch (Exception e) {
            throw new RuntimeException("SAX parsing failed", e);
        }

        return flowers;
    }

    // ======================= HANDLER =========================

    private static class FlowerHandler extends DefaultHandler {

        private final List<Flower> flowers;
        private Flower currentFlower;
        private VisualParameters vp;
        private GrowingTips gt;
        private StringBuilder text = new StringBuilder();

        public FlowerHandler(List<Flower> flowers) {
            this.flowers = flowers;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {

            text.setLength(0);

            switch (qName) {
                case "flower":
                    currentFlower = new Flower();
                    currentFlower.setId(attributes.getValue("id"));
                    break;

                case "visualParameters":
                    vp = new VisualParameters();
                    break;

                case "growingTips":
                    gt = new GrowingTips();
                    break;
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            text.append(ch, start, length);
        }

        @Override
        public void endElement(String uri, String localName, String qName) {

            switch (qName) {

                case "flower":
                    currentFlower.setVisualParameters(vp);
                    currentFlower.setGrowingTips(gt);
                    flowers.add(currentFlower);
                    break;

                case "name":
                    currentFlower.setName(text.toString().trim());
                    break;

                case "soil":
                    currentFlower.setSoil(Soil.valueOf(text.toString().trim().toUpperCase()));
                    break;

                case "origin":
                    currentFlower.setOrigin(text.toString().trim());
                    break;

                // Visual parameters
                case "stemColor":
                    vp.setStemColor(text.toString().trim());
                    break;
                case "leafColor":
                    vp.setLeafColor(text.toString().trim());
                    break;
                case "averageSize":
                    vp.setAverageSize(Integer.parseInt(text.toString().trim()));
                    break;

                // Growing tips
                case "temperature":
                    gt.setTemperature(Integer.parseInt(text.toString().trim()));
                    break;
                case "light":
                    gt.setLight(Boolean.parseBoolean(text.toString().trim()));
                    break;
                case "watering":
                    gt.setWatering(Integer.parseInt(text.toString().trim()));
                    break;

                case "multiplying":
                    currentFlower.setMultiplying(
                            Multiplying.valueOf(text.toString().trim().toUpperCase())
                    );
                    break;
            }
        }
    }
}