import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.util.*;
import java.io.*;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.ContentHandler;

public class SAX extends DefaultHandler {
    private Hashtable tags;


    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws  SAXException
    {
        String key = localName;
        Object value = tags.get(key);
        if(value == null)
        {
            tags.put(key, new Integer(1));
        }
        else
        {
            int count = ((Integer)value).intValue(); count++;tags.put(key, new Integer(count));
        }
    }
    public void startDocument() throws  SAXException
    {
        tags = new Hashtable();
    }

    public void endDocument() throws  SAXException
    {
        Enumeration e = tags.keys();
        while (e.hasMoreElements())
        {
            String tag = (String)e.nextElement();
            int count = ((Integer)tags.get(tag)).intValue();
            System.out.println("Local Name \"" + tag + "\" occurs " + count + " times");
        }
    }



}
