package hr.kaba.olb.responders.ora.service;

public class HisoAnswer {
    private final String responseCode;
    private final Integer ledgerBalance;
    private final Integer availableBalance;

    public HisoAnswer(String responseCode, Integer ledgerBalance, Integer availableBalance) {
        this.responseCode = responseCode;
        this.ledgerBalance = ledgerBalance;
        this.availableBalance = availableBalance;
    }

    public String responseCode() {
        return responseCode;
    }

    public Integer ledgerBalance() {
        return ledgerBalance;
    }

    public Integer availableBalance() {
        return availableBalance;
    }
}
