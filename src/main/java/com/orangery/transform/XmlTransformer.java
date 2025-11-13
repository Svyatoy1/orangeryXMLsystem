package com.orangery.transform;

import com.orangery.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XmlTransformer {

    private static final Logger logger = LoggerFactory.getLogger(XmlTransformer.class);

    public List<Flower> transform(String xmlInput, String xslFile, String xmlOutput) {
        List<Flower> flowers = new ArrayList<>();

        try {
            logger.info("Starting XSLT transformation: {} -> {}", xmlInput, xmlOutput);

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xslFile));
            transformer.transform(new StreamSource(xmlInput), new StreamResult(xmlOutput));

            logger.info("Transformation completed successfully");

            // After transforming, parse the output XML into Flower objects
            flowers = parseTransformedXML(xmlOutput);

        } catch (Exception e) {
            logger.error("Transformation error: {}", e.getMessage());
        }

        return flowers;
    }


    private List<Flower> parseTransformedXML(String xmlPath) {
        List<Flower> list = new ArrayList<>();

        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            var doc = builder.parse(new File(xmlPath));
            var root = doc.getDocumentElement();

            var nodes = root.getElementsByTagName("flower");

            for (int i = 0; i < nodes.getLength(); i++) {
                var node = nodes.item(i);

                Flower flower = new Flower();

                // Simple string fields
                flower.setName(node.getAttributes().getNamedItem("name").getNodeValue());
                flower.setOrigin(node.getAttributes().getNamedItem("origin").getNodeValue());

                // Soil (enum)
                flower.setSoil(Soil.valueOf(
                        node.getAttributes().getNamedItem("soil").getNodeValue().toUpperCase()
                ));

                // Multiplying (enum)
                flower.setMultiplying(Multiplying.valueOf(
                        node.getAttributes().getNamedItem("multiplying").getNodeValue().toUpperCase()
                ));

                // Visual Parameters
                VisualParameters vp = new VisualParameters();
                vp.setStemColor(node.getAttributes().getNamedItem("stemColor").getNodeValue());
                vp.setLeafColor(node.getAttributes().getNamedItem("leafColor").getNodeValue());
                vp.setAverageSize(Double.parseDouble(
                        node.getAttributes().getNamedItem("averageSize").getNodeValue()
                ));
                flower.setVisualParameters(vp);

                // Growing Tips
                GrowingTips tips = new GrowingTips();
                tips.setTemperature(Integer.parseInt(
                        node.getAttributes().getNamedItem("temperature").getNodeValue()
                ));
                tips.setLight(Boolean.parseBoolean(
                        node.getAttributes().getNamedItem("light").getNodeValue()
                ));
                tips.setWatering(Integer.parseInt(
                        node.getAttributes().getNamedItem("watering").getNodeValue()
                ));
                flower.setGrowingTips(tips);

                list.add(flower);
                logger.debug("Parsed transformed flower: {}", flower.getName());
            }

            logger.info("Parsed {} flowers from transformed XML", list.size());

        } catch (Exception e) {
            logger.error("Parsing error after XSL transformation: {}", e.getMessage());
        }

        return list;
    }
}