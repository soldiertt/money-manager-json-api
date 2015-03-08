package be.soldier.money.model;

import be.soldier.money.common.LabelType;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by soldiertt on 28-01-15.
 */
public class MoneyLabel {

    private Integer id;

    private String name;

    private Integer year;

    private boolean fixed;

    private boolean income;

    private LabelType type;

    private BigDecimal defaultAmount;

    private Account defaultRecipient;

    private List<Occurence> occurences;

    private int occSize;

    public MoneyLabel(Integer id, String name, Integer year, boolean fixed, boolean income, LabelType type, BigDecimal defaultAmount) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.fixed = fixed;
        this.income = income;
        this.defaultAmount = defaultAmount;
        this.type = type;

        if (LabelType.MONTHLY.equals(type)) {
            this.occSize = 12;
        } else if (LabelType.QUARTER.equals(type)) {
            this.occSize = 4;
        } else if (LabelType.YEARLY.equals(type)) {
            this.occSize = 1;
        } else {
            throw new InvalidParameterException("Bad LabelType for Label");
        }
        this.occurences = new ArrayList<Occurence>(occSize);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getYear() {
        return year;
    }

    public boolean isFixed() {
        return fixed;
    }

    public boolean isIncome() {
        return income;
    }

    public LabelType getType() {
        return type;
    }

    public BigDecimal getDefaultAmount() {
        return defaultAmount;
    }

    public Account getDefaultRecipient() {
        return defaultRecipient;
    }

    public void setDefaultRecipient(Account defaultRecipient) {
        this.defaultRecipient = defaultRecipient;
    }

    public Occurence getOccurence(int index) {
        Assert.isTrue(index > 0 && index <= occSize);
        return this.occurences.get(index - 1);
    }

    public int getOccSize() {
        return occSize;
    }
}
