package be.soldier.money.common;

/**
 * Created by soldiertt on 07-02-15.
 */
public enum TransactionImportType {
    FOR_ME("Virement en votre faveur"),
    BANCONTACT("Paiement Bancontact"),
    PAYMENT_BELG("Paiement Belg."),
    VIREMENT("Votre virement"),
    RETRAIT("Retrait Bancontact"),
    RETRAIT_BELG("Retrait Belgiq."),
    DOMICILIATION("Domiciliation SEPA"),
    BELFIUS_OUT("BELFIUS_OUT"),
    DECOMPTE_INTERETS("Votre décompte d'intérêts"),
    CARBURANT("Achat carburant Bancontact"),
    PAYMENT_PENSION("Versement EP");

    private String value;

    TransactionImportType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TransactionImportType fromValue(String type) {
        for (TransactionImportType txType : TransactionImportType.values()) {
            if (type.equals(txType.getValue())) {
                return txType;
            }
        }
        throw new IllegalArgumentException("Invalid value '" + type + "' for enum " + TransactionImportType.class.getName());
    }
}
