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

public class Calculator {
    //define path to file
    private final String FILENAME = "kursy.xml";
    private ArrayList<Currency> CurrencyList = new ArrayList<>();

    public Calculator() {
        loadCurrencyRateFile();
    }

    public String countExchangeValue(String exchangeCurrency, double amount){
        int checkCurrency=findIndexOfCurrency(exchangeCurrency);
        if(checkCurrency==-1){
            return "Incorrect currency";
        }else {
            String result;
            Double countedAmount = amount * CurrencyList.get(checkCurrency).getRate();
            result = countedAmount + " " + CurrencyList.get(checkCurrency).getCurrencyname();
            return result;
        }
    }

    public int findIndexOfCurrency(String findCurrency){
        for (int i=0;i<CurrencyList.size();i++){
            if(findCurrency.equalsIgnoreCase(CurrencyList.get(i).getCurrencyname())){
                return i;
            }
        }
        return -1;
    }

    private void loadCurrencyRateFile(){
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

            //get all currencies and rates from file, save it in arraylist
            for(int i=0;i<salaryNodeList.getLength();i++){
                String currency = salaryNodeList.item(i).getAttributes().getNamedItem("currency").getTextContent();
                String rate = salaryNodeList.item(i).getAttributes().getNamedItem("rate").getTextContent();
                Currency newCurrency = new Currency(currency,Double.parseDouble(rate) );
                CurrencyList.add(newCurrency);
                //System.out.printf("1 EUR = %,.2f %s%n", Double.parseDouble(rate), currency);
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
    public void writeAllCurrencies(){
        for (int i=0;i<CurrencyList.size();i++){
            if(i!=0){
                System.out.print(", ");
            }
            System.out.print(CurrencyList.get(i).getCurrencyname());
        }
    }
}
