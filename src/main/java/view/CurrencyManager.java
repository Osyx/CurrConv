package view;

import controller.Fetcher;
import model.CurrencyDTO;
import model.CurrencyError;

import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("currencyManager")
@ConversationScoped
public class CurrencyManager implements Serializable{
    @EJB
    private Fetcher fetcher = new Fetcher();
    private float amount = 0;
    private float desiredValue;
    private String toCurrency;
    private String fromCurrency;
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

    public void fetchRate() {
        try {
            startConversation();
            failure = null;
            List<CurrencyDTO> list = fetcher.checkNewRate(fromCurrency, toCurrency);
            desiredValue = amount / list.get(0).getRate() * list.get(1).getRate();
        } catch (CurrencyError currencyError) {
            handleException(currencyError);
        }
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public float getAmount() {
        return amount;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public float getDesiredValue() {
        return desiredValue;
    }

    public void setDesiredValue(float desiredValue) {
        this.desiredValue = desiredValue;
    }

    public boolean success() {
        return failure == null;
    }
}
