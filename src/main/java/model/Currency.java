package model;

import javax.persistence.*;
import java.io.Serializable;

@NamedQueries({
        @NamedQuery(
                name = "selectCurrency",
                query = "SELECT currency FROM currencies currency WHERE currency.isocode LIKE :isoCode"
        )
})

@Entity(name = "currencies")
public class Currency implements CurrencyDTO, Serializable {
    @Id
    @Column(name = "isocode", nullable = false)
    private String isocode;

    @Column(name = "rate", nullable = false)
    private float rate;

    @Version
    @Column(name = "version")
    private int versionNum;

    public Currency() {}

    public String getIsocode() {
        return isocode;
    }

    public float getRate() {
        return rate;
    }

    public int getVersionNum() {
        return versionNum;
    }

    /**
     * @return A string representation of all fields in this object.
     */
    @Override
    public String toString() {
        return "Currency: " + isocode + "'s rate is currently at " + rate;
    }
}