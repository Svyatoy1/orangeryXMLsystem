package com.orangery.app;

import com.orangery.model.Flower;
import com.orangery.parser.DomParser;
import com.orangery.parser.SaxParser;
import com.orangery.parser.StaxParser;
import com.orangery.transform.XmlTransformer;
import com.orangery.util.FlowerComparatorByName;
import com.orangery.util.FlowerComparatorByOrigin;
import com.orangery.util.FlowerComparatorBySize;
import com.orangery.validator.XmlValidator;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String XML_PATH = "src/main/resources/flowers.xml";
    private static final String XSD_PATH = "src/main/resources/flowers.xsd";
    private static final String XSL_PATH = "src/main/resources/transform.xml";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        List<Flower> flowers = null;

        while (true) {
            System.out.println("\n==== ORANGERY XML SYSTEM ====");
            System.out.println("1 — Parse XML (choose parser)");
            System.out.println("2 — Sort flowers");
            System.out.println("3 — Validate XML");
            System.out.println("4 — Transform XML (XSLT)");
            System.out.println("5 — Show loaded flowers");
            System.out.println("0 — Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1: {
                    System.out.println("Choose parser:");
                    System.out.println("1 — DOM");
                    System.out.println("2 — SAX");
                    System.out.println("3 — StAX");

                    int p = scanner.nextInt();
                    scanner.nextLine();

                    switch (p) {
                        case 1:
                            flowers = new DomParser().parse(XML_PATH);
                            System.out.println("Parsed using DOM.");
                            break;
                        case 2:
                            flowers = new SaxParser().parse(XML_PATH);
                            System.out.println("Parsed using SAX.");
                            break;
                        case 3:
                            flowers = new StaxParser().parse(XML_PATH);
                            System.out.println("Parsed using StAX.");
                            break;
                        default:
                            System.out.println("Unknown parser.");
                    }
                    break;
                }

                case 2: {
                    if (flowers == null) {
                        System.out.println("No flowers loaded. Parse XML first.");
                        break;
                    }
                    System.out.println("Sort by:");
                    System.out.println("1 — Name");
                    System.out.println("2 — Origin");
                    System.out.println("3 — Size");

                    int s = scanner.nextInt();
                    scanner.nextLine();

                    switch (s) {
                        case 1:
                            flowers.sort(new FlowerComparatorByName());
                            System.out.println("Sorted by name.");
                            break;
                        case 2:
                            flowers.sort(new FlowerComparatorByOrigin());
                            System.out.println("Sorted by origin.");
                            break;
                        case 3:
                            flowers.sort(new FlowerComparatorBySize());
                            System.out.println("Sorted by size.");
                            break;
                        default:
                            System.out.println("Unknown sorting option.");
                    }
                    break;
                }

                case 3: {
                    XmlValidator validator = new XmlValidator();
                    boolean isValid = validator.validate(XML_PATH, XSD_PATH);

                    if (isValid)
                        System.out.println("XML is VALID!");
                    else
                        System.out.println("XML is NOT valid!");

                    break;
                }

                case 4: {
                    System.out.print("Enter output file name (e.g. result.xml): ");
                    String out = scanner.nextLine();

                    XmlTransformer transformer = new XmlTransformer();
                    transformer.transform(XML_PATH, XSL_PATH, out);

                    System.out.println("Transformation completed → " + out);
                    break;
                }

                case 5: {
                    if (flowers == null) {
                        System.out.println("No flowers loaded.");
                        break;
                    }
                    flowers.forEach(System.out::println);
                    break;
                }

                case 0:
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Unknown option.");
            }
        }
    }
}