package main.java.view;

import main.java.controller.Fetcher;
import main.java.model.CurrencyDTO;
import main.java.model.CurrencyError;

import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named("currencyManager")
@ConversationScoped
public class CurrencyManager implements Serializable{
    @EJB
    private Fetcher fetcher = new Fetcher();
    private String searchedCurrency;
    private CurrencyDTO currentRate;
    private Exception failure;
    @Inject
    private Conversation conversation;

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

    public void getRate() {
        try {
            startConversation();
            failure = null;
            currentRate = fetcher.checkRate(searchedCurrency);
        } catch (CurrencyError currencyError) {
            handleException(currencyError);
        }
    }

    public void setSearchedCurrency(String searchedCurrency) {
        this.searchedCurrency = searchedCurrency;
    }

    public String getSearchedCurrency() {
        return searchedCurrency;
    }

    public CurrencyDTO getCurrentRate() {
        return currentRate;
    }

    public boolean success() {
        return failure == null;
    }
}
