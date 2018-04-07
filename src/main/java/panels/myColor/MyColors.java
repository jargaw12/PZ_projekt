package panels.myColor;

import configuration.Logger;
import configuration.MyConfiguration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import panels.Frame;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

public class MyColors {

    private ArrayList<MyColor> myColors;
    MyConfiguration configuration;

    public MyColors() {
        configuration=new MyConfiguration();
        myColors =new ArrayList<>();
        zaladujKolory();
    }

    private void zaladujKolory() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            Logger.getInstance().logger.log(Level.WARNING,"Błąd z tworzeniem nowego dokumentu XML",e);
        }
        Document document = null;
        try {
            document = builder.parse(new File(configuration.getValue("dir.colorsXML")));
        } catch (SAXException | IOException e) {
            Logger.getInstance().logger.log(Level.WARNING,"Błąd z parsowaniem pliku XML",e);
            }
        document.getDocumentElement().normalize();
        Element root = document.getDocumentElement();
        NodeList children = root.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child instanceof Element) {
                MyColor myColor = new MyColor();
                myColor.setName(child.getAttributes().getNamedItem("name").getTextContent());
                NodeList cNodes = child.getChildNodes();
                for (int j = 0; j < cNodes.getLength(); j++) {
                    Node cNode = cNodes.item(j);
                    if (cNode instanceof Element) {
                        Element element = (Element) cNode;
                        int r = Integer.parseInt(element.getElementsByTagName("r").item(0).getTextContent());
                        int g = Integer.parseInt(element.getElementsByTagName("g").item(0).getTextContent());
                        int b = Integer.parseInt(element.getElementsByTagName("b").item(0).getTextContent());
                        if (element.getTagName().equals("bright")) {
                            myColor.setBright(new Color(r, g, b));
                        }
                        if (element.getTagName().equals("dark")) {
                            myColor.setDark(new Color(r, g, b));
                        }
                    }
                }
                myColors.add(myColor);
            }
        }
    }

    public MyColor getKolor(String nazwa){
        for (MyColor myColor : myColors) {
            if (myColor.getName().equals(nazwa)) return myColor;
        }
        return null;
    }

    public ArrayList<MyColor> getMyColors() {
        return myColors;
    }
}
