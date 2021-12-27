package org.example.services.impl;

import com.opencsv.CSVWriter;
import org.example.model.Banknote;
import org.example.model.InfoData;
import org.example.services.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServiceImpl implements Service {

    private static final String FILENAME = "C:\\Users\\BalasafAlizade\\Desktop\\theEnd\\Xml\\T_K21F05-A04768_20211206144632274.xml";

    @Override
    public void startProcess() {
        createCsvFile(readDataFromXml());
    }

    @Override
    public List<Banknote> readDataFromXml() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(FILENAME));
            doc.getDocumentElement().normalize();
            System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
            System.out.println("------");

            // get <lines>
            NodeList list = doc.getElementsByTagName("line");

            List<Banknote> listOfBanknote = new ArrayList<>();

            for (int temp = 0; temp < list.getLength(); temp++) {

                Node node = list.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;
                    Banknote bank = Banknote.builder()
                            .currencyCode(element.getElementsByTagName("currencyCode").item(0).getTextContent())
                            .nominal(Integer.parseInt(element.getElementsByTagName("nominal").item(0).getTextContent()))
                            .qty(Integer.parseInt(element.getElementsByTagName("qty").item(0).getTextContent()))
                            .no(new ArrayList<InfoData>())
                            .build();

                    for (int i = 0; i < bank.getQty(); i++) {
                        NodeList qtyNodeList = element.getElementsByTagName("No"+(i+1));
                        for(int temp2 = 0; temp2 < qtyNodeList.getLength(); temp2++) {
                            Node qtyNode = qtyNodeList.item(temp2);
                            if (node.getNodeType() == Node.ELEMENT_NODE) {
                                Element qtyElement = (Element) qtyNode;
                                InfoData info = InfoData.builder()
                                        .sn(qtyElement.getElementsByTagName("SN").item(0).getTextContent())
                                        .atm(Integer.parseInt(qtyElement.getElementsByTagName("ATM").item(0).getTextContent()))
                                        .fit(Integer.parseInt(qtyElement.getElementsByTagName("FIT").item(0).getTextContent()))
                                        .unfit(Integer.parseInt(qtyElement.getElementsByTagName("Unfit").item(0).getTextContent()))
                                        .error(qtyElement.getElementsByTagName("ERROR").item(0).getTextContent())
                                        .build();
                                bank.getNo().add(info);
                            }
                        }
                    }
                    listOfBanknote.add(bank);
                }
            }
            return listOfBanknote;
        } catch (ParserConfigurationException | IOException | org.xml.sax.SAXException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void createCsvFile(List<Banknote> dataForCsv) {
        final List<String[]> list = new ArrayList<>();
        for (Banknote banknote : dataForCsv) {
            list.add(new String[]{"CurrencyCode", "Nominal", "Qty"});
            list.add(new String[]{banknote.getCurrencyCode(),banknote.getNominal()+"", banknote.getQty()+""});
            banknote.getNo().forEach(
                    info -> {
                        list.add(new String[]{"SerialNumber", "ATM", "FIT", "Unfit", "ERROR"});
                        list.add(new String[]{info.getSn()+"",info.getAtm()+"",info.getFit()+"", info.getUnfit()+"", info.getError()});
                    }
            );
        }


        try (CSVWriter writer = new CSVWriter(new FileWriter("test.csv"))) {
            writer.writeAll(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
