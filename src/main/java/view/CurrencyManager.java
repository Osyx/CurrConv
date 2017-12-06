package view;

import controller.Fetcher;
import model.CurrencyDTO;
import model.CurrencyError;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.Serializable;

@Named("currencyManager")
@ConversationScoped
public class CurrencyManager implements Serializable{
    @EJB
    private Fetcher fetcher = new Fetcher();
    private String searchedCurrency;
    private CurrencyDTO currentRate;
    private int test = 10;
    private Exception failure;
    @Inject
    private Conversation conversation;

    public void updateDB() {
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document document = docBuilder.parse(new File("rate.xml"));

            NodeList nodeList = document.getElementsByTagName("Currency");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    String isocode = node.getAttributes().getNamedItem("isocode").getNodeValue();
                    float rate = Float.parseFloat(node.getAttributes().getNamedItem("rate").getNodeValue());
                    fetcher.insertIntoDB(isocode, rate);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
        }
    }

    private void stopConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
    }

    private void handleException(Exception e) {
        stopConversation();
        e.printStackTrace(System.err);
        failure = e;
    }

    public void fetchRate() {
        System.out.println("Pelle svanslös");
        try {
            startConversation();
            failure = null;
            currentRate = fetcher.checkRate(searchedCurrency);
        } catch (CurrencyError currencyError) {
            handleException(currencyError);
        }
    }

    public void setSearchedCurrency(String searchedCurrency) {
        System.out.println("Hella hop");
        this.searchedCurrency = searchedCurrency;
    }

    public String getSearchedCurrency() {
        System.out.println("Måsen");
        return searchedCurrency;
    }

    public CurrencyDTO getCurrentRate() {
        System.out.println("Området");
        return currentRate;
    }

    public boolean success() {
        System.out.println("Målle");
        return failure == null;
    }

    public int getTest() {
        return test;
    }
}
