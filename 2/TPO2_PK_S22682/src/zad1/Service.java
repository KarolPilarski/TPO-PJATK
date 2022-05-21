/**
 *
 *  @author Pilarski Karol S22682
 *
 */

package zad1;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Locale;

import org.xml.sax.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import org.w3c.dom.*;



public class Service {

    String countryName;
    public static SortedMap<Currency, Locale> countries;
    Currency countryCurrency;
    String NBPrate;
    String NBPConverter;

    public Service(String countryName) {
        this.countryName = countryName;

        //this currency
        countryCurrency=null;
        NBPConverter="1.0";
        Locale langEnglish  = new Locale.Builder().setLanguage("en"/*English*/).build();
        for (Locale locale: Locale.getAvailableLocales()) {
            if (locale.getDisplayCountry(langEnglish).equals(countryName)) {
                countryCurrency = Currency.getInstance(locale);
            }
        }

        //load NBP
        if(countryCurrency.getCurrencyCode().equals("PLN")){
            NBPrate="1.0";
            NBPConverter="1.0";
        }else {
            // xml1 rate
            String nbpAPI = null;
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            try {
                DocumentBuilder builder = factory.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
            try {
                nbpAPI = new Scanner(new URL("https://www.nbp.pl/kursy/xml/a056z220322.xml").openStream(), String.valueOf(StandardCharsets.UTF_8)).useDelimiter("\\A").next();
            } catch (IOException e) {
                e.printStackTrace();
            }

            XPath xpath = XPathFactory.newInstance().newXPath();
            InputSource inputSource = new InputSource(new StringReader(nbpAPI));
            DocumentBuilder builder = null;
            try {
                builder = factory.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
            Document doc = null;
            Object result = null;
            XPathExpression expr = null;
            try {
                doc = builder.parse(new URL("https://www.nbp.pl/kursy/xml/a056z220322.xml").openStream());
                expr = xpath.compile("//pozycja[kod_waluty='"+countryCurrency+"']/kurs_sredni/text()");
                result = null;
                result = expr.evaluate(doc, XPathConstants.NODESET);
            } catch (XPathExpressionException | IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
            NodeList nodes = (NodeList) result;
            for (int i = 0; i < nodes.getLength(); i++) {
                NBPrate = nodes.item(i).getNodeValue();
            }


            //xml1 converter
            factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            try {
                builder = factory.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
            try {
                doc = builder.parse(new URL("https://www.nbp.pl/kursy/xml/b012z220323.xml").openStream());
                expr = xpath.compile("//pozycja[kod_waluty='"+countryCurrency+"']/przelicznik/text()");
                result = expr.evaluate(doc, XPathConstants.NODESET);
            } catch (XPathExpressionException | IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
            nodes = (NodeList) result;
            for (int i = 0; i < nodes.getLength(); i++) {
                NBPConverter = nodes.item(i).getNodeValue();
            }


            //xml2 rate

            factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            try {
                builder = factory.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
            try {
                doc = builder.parse(new URL("https://www.nbp.pl/kursy/xml/b012z220323.xml").openStream());
                expr = xpath.compile("//pozycja[kod_waluty='"+countryCurrency+"']/kurs_sredni/text()");
                result = expr.evaluate(doc, XPathConstants.NODESET);
            } catch (XPathExpressionException | IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
            nodes = (NodeList) result;
            for (int i = 0; i < nodes.getLength(); i++) {
                NBPrate = nodes.item(i).getNodeValue();
            }

            //xml2 converter
            factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            try {
                builder = factory.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
            try {
                doc = builder.parse(new URL("https://www.nbp.pl/kursy/xml/b012z220323.xml").openStream());
                expr = xpath.compile("//pozycja[kod_waluty='"+countryCurrency+"']/przelicznik/text()");
                result = expr.evaluate(doc, XPathConstants.NODESET);
            } catch (XPathExpressionException | IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
            nodes = (NodeList) result;
            for (int i = 0; i < nodes.getLength(); i++) {
                NBPConverter = nodes.item(i).getNodeValue();
            }
        }
    }

    public String getWeather(String city) {
        String coordinatesAPI=null;
        try {
            coordinatesAPI = new Scanner(new URL("https://api.openweathermap.org/geo/1.0/direct?q="+city+"&limit=1&appid=e42e4ff55be0b9988d09cbae95ac3aad").openStream(), String.valueOf(StandardCharsets.UTF_8)).useDelimiter("\\A").next();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject coordinatesObj=null;
        try {
            JSONParser jparserer= new JSONParser();
            coordinatesObj= (JSONObject) ((JSONArray)jparserer.parse(coordinatesAPI)).get(0);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String weatherAPI=null;
        try {
            weatherAPI = new Scanner(new URL("https://api.openweathermap.org/data/2.5/weather?lat="+(coordinatesObj.get("lat"))+"&lon="+(coordinatesObj.get("lon"))+"&appid=e42e4ff55be0b9988d09cbae95ac3aad").openStream(), "UTF-8").useDelimiter("\\A").next();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return weatherAPI;
    }

    public Double getRateFor(String currency) {
        String currenciesAPI=null;
        try {
            currenciesAPI = new Scanner(new URL("https://api.exchangerate.host/convert?from="+this.countryCurrency.getCurrencyCode()+"&to="+currency).openStream(), String.valueOf(StandardCharsets.UTF_8)).useDelimiter("\\A").next();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject currenciesObj=null;
        try {
            currenciesObj= (JSONObject) new JSONParser().parse(currenciesAPI);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return (Double) currenciesObj.get("result");
    }

    public Double getNBPRate() {
return Double.parseDouble((NBPrate.replace(',','.')))/Double.parseDouble(NBPConverter);    }
}
