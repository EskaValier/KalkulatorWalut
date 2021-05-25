package com.company;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    //define path to file
    private static final String FILENAME = "kursy.xml";

    public static void main(String[] args) {

        loadCurrencyRateFile();
    }
    private static void loadCurrencyRateFile(){
        // Instantiate the Factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(FILENAME));

            // get <staff>
            NodeList list = doc.getElementsByTagName("Cube");
            // get 2-nd item -> element cube with attribute time
            Node node = list.item(1);

            Element element = (Element) node;
            String time = element.getAttributes().getNamedItem("time").getTextContent();
            System.out.println("Date of update currency rate: " + time);
            NodeList salaryNodeList = element.getElementsByTagName("Cube");

            //get all currencies and rates in file
            for(int i=0;i<32;i++){
                String currency = salaryNodeList.item(i).getAttributes().getNamedItem("currency").getTextContent();
                String salary = salaryNodeList.item(i).getAttributes().getNamedItem("rate").getTextContent();
                System.out.printf("1 EUR = %,.2f %s%n", Float.parseFloat(salary), currency);
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}