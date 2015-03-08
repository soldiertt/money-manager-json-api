package be.soldier.money.common;

/**
 * Created by soldiertt on 15-02-15.
 */
public class LabelCriteria {

    private Integer year;
    private Boolean fixed;
    private Boolean income;
    private LabelType labelType;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Boolean getFixed() {
        return fixed;
    }

    public void setFixed(Boolean fixed) {
        this.fixed = fixed;
    }

    public Boolean getIncome() {
        return income;
    }

    public void setIncome(Boolean income) {
        this.income = income;
    }

    public LabelType getLabelType() {
        return labelType;
    }

    public void setLabelType(LabelType labelType) {
        this.labelType = labelType;
    }
}
