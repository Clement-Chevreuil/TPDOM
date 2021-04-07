import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.File;
import java.io.IOException;

public class FilmParsing {

    public static void main(String[] args) throws Exception {

        test();

    }

    public static void afficherDom() throws Exception
    {
        File f = new File(".idea/dvd.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(f);
        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(System.out);
        transformer.transform(source, result);
    }
    public static void acteurDVD() throws Exception
    {
        File f = new File(".idea/dvd.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(f);

        Element root = document.getDocumentElement();

        //root.getElementsByTagName("lastName").getLength()
        for (int i = 0; i < root.getElementsByTagName("actor").getLength(); i++)
        {
            System.out.println(root.getElementsByTagName("lastName").item(i).getFirstChild().getNodeValue() + " " + root.getElementsByTagName("firstName").item(i).getFirstChild().getNodeValue() );
        }
    }
    public static void rendreDVD() throws Exception
    {
        File f = new File(".idea/dvd.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(f);

        Element root = document.getDocumentElement();
        NodeList childrenDate = root.getElementsByTagName("rent");

        for (int i = 0; i < root.getElementsByTagName("rent").getLength(); i++)
        {
            System.out.println(childrenDate.item(i).getAttributes().getNamedItem("date").getNodeValue());
        }
    }

    public static void XPath() throws Exception
    {
        File f = new File(".idea/dvd.xml");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(f);

        XPathFactory factory1 = XPathFactory.newInstance();
        XPath xpath = factory1.newXPath();
        String xpathStringTitre = "//title";

        XPathExpression exp = xpath.compile (xpathStringTitre);
        NodeList result = (NodeList) exp.evaluate(document, XPathConstants.NODESET);

        for (int i = 0; i < result.getLength(); i++)
        {
            System.out.println(result.item(i).getFirstChild().getNodeValue());
        }

        System.out.println(" ");
        System.out.println("Film loue");
        XPathFactory factory2 = XPathFactory.newInstance();
        XPath xpath2 = factory1.newXPath();
        String xpathStringFilmLoue = "//DVD[rent]//title";


        XPathExpression exp2 = xpath.compile (xpathStringFilmLoue);
        NodeList result2 = (NodeList) exp2.evaluate(document, XPathConstants.NODESET);

        for (int i = 0; i < result2.getLength(); i++)
        {
            System.out.println(result2.item(i).getFirstChild().getNodeValue());
        }

        System.out.println(" ");
        System.out.println("Film nombre acteur");
        XPathFactory factory3 = XPathFactory.newInstance();
        XPath xpath3 = factory3.newXPath();
        String xpathStringFilmNbActeur = "//film";


        XPathExpression exp3 = xpath.compile (xpathStringFilmNbActeur);
        NodeList result3 = (NodeList) exp3.evaluate(document, XPathConstants.NODESET);

        for (int i = 0; i < result3.getLength(); i++)
        {
            String nbActeur = "count(.//actor)";
            exp3 = xpath.compile(nbActeur);
            double nb = (Double)exp3.evaluate(result3.item(i), XPathConstants.NUMBER);

            System.out.println(result3.item(i).getChildNodes().item(1).getFirstChild().getNodeValue() + " " + nb);
        }

    }
    public static void ModificationDOM() throws Exception
    {

        File f = new File(".idea/dvd.xml");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(f);
        Element root = document.getDocumentElement();

        XPathFactory xfactory = XPathFactory.newInstance();
        XPath xpath = xfactory.newXPath();
        String listFilmloue = "//DVD[rent]//title";


        XPathExpression exp = xpath.compile(listFilmloue);
        NodeList titlesl = (NodeList)exp.evaluate(document, XPathConstants.NODESET);
        NodeList person = root.getElementsByTagName("person");
        System.out.println("Liste des films loués");

        for(int i = 0; i < titlesl.getLength(); ++i)
        {
            Element renttitle = document.createElement("filmtitle");
            renttitle.appendChild(document.createTextNode(titlesl.item(i).getFirstChild().getNodeValue()));
            person.item(i).appendChild(renttitle);
        }


        TransformerFactory tfactory = TransformerFactory.newInstance();
        Transformer transform = tfactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(System.out);
        transform.transform(source, result);

    }
    public static void test() throws Exception
    {
        File f = new File(".idea/dvd.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(f);

        Element root = document.getDocumentElement();
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        SAXParser saxParser = spf.newSAXParser();

        XMLReader xmlReader = saxParser.getXMLReader();
        xmlReader.setContentHandler(new SAX());
        xmlReader.parse(new org.xml.sax.InputSource(".idea/dvd.xml"));
         System.out.println("roro");
    }


}
