package com.orangery.parser;

import com.orangery.model.*;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SaxParser {

    private static class Handler extends DefaultHandler {
        private List<Flower> flowers = new ArrayList<>();
        private Flower flower;
        private VisualParameters vp;
        private GrowingTips tips;
        private StringBuilder text = new StringBuilder();

        public List<Flower> getFlowers() {
            return flowers;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            text.setLength(0);

            if (qName.equals("flower")) {
                flower = new Flower();
                vp = new VisualParameters();
                tips = new GrowingTips();
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            text.append(ch, start, length);
        }

        @Override
        public void endElement(String uri, String localName, String qName) {
            String value = text.toString().trim();

            switch (qName) {
                case "name" -> flower.setName(value);
                case "origin" -> flower.setOrigin(value);

                case "soil" ->
                        flower.setSoil(Soil.valueOf(value.toUpperCase()));

                case "multiplying" ->
                        flower.setMultiplying(Multiplying.valueOf(value.toUpperCase()));

                case "stemColor" -> vp.setStemColor(value);
                case "leafColor" -> vp.setLeafColor(value);
                case "averageSize" -> vp.setAverageSize(Integer.parseInt(value));

                case "temperature" -> tips.setTemperature(Integer.parseInt(value));
                case "light" -> tips.setLight(Boolean.parseBoolean(value));
                case "watering" -> tips.setWatering(Integer.parseInt(value));

                case "visualParameters" -> flower.setVisualParameters(vp);
                case "growingTips" -> flower.setGrowingTips(tips);

                case "flower" -> flowers.add(flower);
            }
        }
    }

    public List<Flower> parse(String filePath) {
        try {
            Handler handler = new Handler();
            SAXParserFactory.newInstance()
                    .newSAXParser()
                    .parse(new File(filePath), handler);
            return handler.getFlowers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}