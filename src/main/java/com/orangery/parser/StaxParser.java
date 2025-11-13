package com.orangery.parser;

import com.orangery.model.*;
import javax.xml.stream.*;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class StaxParser {

    public List<Flower> parse(String filePath) {
        List<Flower> flowers = new ArrayList<>();

        try {
            XMLStreamReader reader = XMLInputFactory.newInstance()
                    .createXMLStreamReader(new FileInputStream(filePath));

            Flower flower = null;
            VisualParameters vp = null;
            GrowingTips tips = null;

            String currentTag = "";
            String text = "";

            while (reader.hasNext()) {
                int type = reader.next();

                switch (type) {

                    case XMLStreamConstants.START_ELEMENT -> {
                        currentTag = reader.getLocalName();
                        text = "";

                        switch (currentTag) {
                            case "flower" -> {
                                flower = new Flower();
                                vp = new VisualParameters();
                                tips = new GrowingTips();
                            }
                        }
                    }

                    case XMLStreamConstants.CHARACTERS -> {
                        text += reader.getText().trim();
                    }

                    case XMLStreamConstants.END_ELEMENT -> {

                        switch (reader.getLocalName()) {

                            case "name" -> flower.setName(text);
                            case "origin" -> flower.setOrigin(text);

                            case "soil" ->
                                    flower.setSoil(Soil.valueOf(text.toUpperCase()));

                            case "multiplying" ->
                                    flower.setMultiplying(Multiplying.valueOf(text.toUpperCase()));

                            case "stemColor" -> vp.setStemColor(text);
                            case "leafColor" -> vp.setLeafColor(text);
                            case "averageSize" -> vp.setAverageSize(Integer.parseInt(text));

                            case "temperature" -> tips.setTemperature(Integer.parseInt(text));
                            case "light" -> tips.setLight(Boolean.parseBoolean(text));
                            case "watering" -> tips.setWatering(Integer.parseInt(text));

                            case "visualParameters" -> flower.setVisualParameters(vp);
                            case "growingTips" -> flower.setGrowingTips(tips);

                            case "flower" -> flowers.add(flower);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return flowers;
    }
}