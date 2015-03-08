package be.soldier.money.model;

/**
 * Created by soldiertt on 29-01-15.
 */
public class Account {

    private Integer id;

    private String name;

    private String accountNumber;

    private Boolean own;

    public Account(Integer id, String name, String accountNumber,Boolean own) {
        this.id = id;
        this.name = name;
        this.accountNumber = accountNumber;
        this.own = own;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Boolean isOwn() {
        return own;
    }
}
