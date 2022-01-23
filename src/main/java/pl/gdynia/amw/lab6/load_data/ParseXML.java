package pl.gdynia.amw.lab6.load_data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import pl.gdynia.amw.lab6.database.CurrencyRate;

@Service
public class ParseXML {
    private final Logger logger = LoggerFactory.getLogger(ParseXML.class);

    @Value("${app.URL.ECB}")
    private String URL;

    private String subject;
    private String sender;

    //create an empty list for currencies
    private List<CurrencyRate> currencyRates = new ArrayList<>();

    //create an empty list for all dates
    private List<String> dates = new ArrayList<>();

    public void parseCurrencyByDate(String date) {

        currencyRates.clear();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(URL);

            // normalize XML response
            doc.getDocumentElement().normalize();

            //read currency list
            NodeList nodeList = doc.getFirstChild().getChildNodes();

            //loop all available currency nodes
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;

                    switch (element.getTagName()) {
                        case "gesmes:subject":
                            this.subject = element.getTextContent();
                            break;
                        case "gesmes:Sender":
                            this.sender = element.getTextContent();
                            break;
                        case "Cube":
                            NodeList exchangesList = element.getChildNodes();   //list of elements with attribute time

                            for (int j = 0; j < exchangesList.getLength(); j++) {
                                Node exchangeNode = exchangesList.item(j);

                                if (exchangeNode.getAttributes().getNamedItem("time").getTextContent().equals(date)) {
                                    //System.out.println("znalazlem " + date + " " + i + " " + j);

                                    NodeList currencyRatesNodeList = exchangeNode.getChildNodes();
                                    for (int k = 0; k < currencyRatesNodeList.getLength(); k++) {

                                        String currency = currencyRatesNodeList.item(k).getAttributes().getNamedItem("currency").getTextContent();
                                        String rate = currencyRatesNodeList.item(k).getAttributes().getNamedItem("rate").getTextContent();

                                        CurrencyRate currencyRate = new CurrencyRate(Double.parseDouble(rate),currency, LocalDate.parse(date));
                                        currencyRates.add(currencyRate);
                                        //System.out.printf("1 EUR = %,.2f %s%n", Double.parseDouble(rate), currency);
                                    }
                                    break;
                                }
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }
    public void parseDates(){
        dates.clear();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(URL);

            // normalize XML response
            doc.getDocumentElement().normalize();

            //read currency list
            NodeList nodeList = doc.getFirstChild().getChildNodes();

            //System.out.println("currencies: " + currencyRatesNodeList.getLength());
            //loop all available currency nodes
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;

                    switch (element.getTagName()) {
                        case "gesmes:subject":
                            this.subject = element.getTextContent();
                            break;
                        case "gesmes:Sender":
                            this.sender = element.getTextContent();
                            break;
                        case "Cube":
                            NodeList exchangesList = element.getChildNodes();   //list of elements with attribute time

                            for (int j = 0; j < exchangesList.getLength(); j++) {
                                Node exchangeNode = exchangesList.item(j);
                                dates.add(exchangeNode.getAttributes().getNamedItem("time").getTextContent());
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }
    public List<CurrencyRate> getCurrencyRates() {
        return currencyRates;
    }
    public List<String> getDates() {
        return dates;
    }
}