package com.orangery.app;

import com.orangery.model.Flower;
import com.orangery.parser.DomParser;
import com.orangery.parser.SaxParser;
import com.orangery.parser.StaxParser;
import com.orangery.transform.XmlTransformer;
import com.orangery.validator.XmlValidator;
import com.orangery.util.*;
import java.io.StringWriter;

import java.nio.file.Path;
import java.nio.file.Files;
import java.awt.Desktop;

import java.util.*;

public class Main {

    private static List<Flower> flowers = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("==== ORANGERY XML SYSTEM ====");
            System.out.println("1 — Parse XML (choose parser)");
            System.out.println("2 — Sort flowers");
            System.out.println("3 — Validate XML");
            System.out.println("4 — Transform XML (XSLT)");
            System.out.println("5 — Show loaded flowers");
            System.out.println("0 — Exit");
            System.out.print("> ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1" -> parseMenu();
                case "2" -> sortMenu();
                case "3" -> validateXml();
                case "4" -> transformXml();
                case "5" -> showFlowers();
                case "0" -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Unknown option.");
            }
        }
    }

    private static void parseMenu() {
        System.out.println("Choose parser:");
        System.out.println("1 — DOM");
        System.out.println("2 — SAX");
        System.out.println("3 — StAX");
        System.out.print("> ");

        String c = scanner.nextLine();

        switch (c) {
            case "1" -> flowers = new DomParser().parse("flowers.xml");
            case "2" -> flowers = new SaxParser().parse("flowers.xml");
            case "3" -> flowers = new StaxParser().parse("flowers.xml");
            default -> {
                System.out.println("Unknown parser.");
                return;
            }
        }
        System.out.println("Loaded " + flowers.size() + " flowers.");
    }

    private static void sortMenu() {
        if (flowers.isEmpty()) {
            System.out.println("No flowers loaded.");
            return;
        }

        System.out.println("Sort by:");
        System.out.println("1 — Name");
        System.out.println("2 — Origin");
        System.out.println("3 — Size");

        String c = scanner.nextLine();

        switch (c) {
            case "1" -> flowers.sort(new FlowerComparatorByName());
            case "2" -> flowers.sort(new FlowerComparatorByOrigin());
            case "3" -> flowers.sort(new FlowerComparatorBySize());
            default -> {
                System.out.println("Unknown option.");
                return;
            }
        }

        System.out.println("Sorting complete.");
    }

    private static void validateXml() {
        XmlValidator validator = new XmlValidator();

        boolean valid = validator.validate("flowers.xml", "flowers.xsd");

        if (valid) System.out.println("XML is valid!");
        else System.out.println("XML is invalid!");
    }

    private static void transformXml() {
        if (flowers.isEmpty()) {
            System.out.println("No flowers loaded.");
            return;
        }

        System.out.println("Performing XSLT transformation...");

        XmlTransformer transformer = new XmlTransformer();

        // Створюємо XML зі списку квітів
        String xml = toXmlString(flowers);

        // Виконуємо трансформацію
        String html = transformer.transformStringXml(xml, "transform.xml");

        // Записуємо у resources/output/output.html
        try {
            Path out = Path.of("src/main/resources/output/output.html");
            Files.createDirectories(out.getParent());
            Files.writeString(out, html);

            System.out.println("HTML saved: " + out.toAbsolutePath());

            // Автоматично відкриваємо у браузері
            java.awt.Desktop.getDesktop().browse(out.toUri());

        } catch (Exception e) {
            System.out.println("Error while saving HTML: " + e.getMessage());
        }
    }

    private static void showFlowers() {
        if (flowers.isEmpty()) {
            System.out.println("No flowers loaded.");
            return;
        }

        System.out.println("=== Flowers ===");
        flowers.forEach(System.out::println);
    }

    public static String toXmlString(List<Flower> flowers) {
        StringWriter sw = new StringWriter();
        sw.write("<flowers>\n");

        for (Flower f : flowers) {
            sw.write("  <flower id=\"" + f.getId() + "\">\n");
            sw.write("    <name>" + f.getName() + "</name>\n");
            sw.write("    <soil>" + f.getSoil().getValue() + "</soil>\n");
            sw.write("    <origin>" + f.getOrigin() + "</origin>\n");

            sw.write("    <visualParameters>\n");
            sw.write("      <stemColor>" + f.getVisualParameters().getStemColor() + "</stemColor>\n");
            sw.write("      <leafColor>" + f.getVisualParameters().getLeafColor() + "</leafColor>\n");
            sw.write("      <averageSize>" + f.getVisualParameters().getAverageSize() + "</averageSize>\n");
            sw.write("    </visualParameters>\n");

            sw.write("    <growingTips>\n");
            sw.write("      <temperature>" + f.getGrowingTips().getTemperature() + "</temperature>\n");
            sw.write("      <light>" + f.getGrowingTips().isLight() + "</light>\n");
            sw.write("      <watering>" + f.getGrowingTips().getWatering() + "</watering>\n");
            sw.write("    </growingTips>\n");

            sw.write("    <multiplying>" + f.getMultiplying().getValue() + "</multiplying>\n");

            sw.write("  </flower>\n");
        }

        sw.write("</flowers>");
        return sw.toString();
    }
}