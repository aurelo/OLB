package hr.kaba.olb.protocol.trx;

import hr.kaba.olb.codec.constants.ResponseCode;

import java.util.Optional;

public class HisoResponse {

    private final ResponseCode responseCode;
    private final Optional<Integer> ledgerBalance;
    private final Optional<Integer> availableBalance;

    public HisoResponse(ResponseCode responseCode, Integer ledgerBalance, Integer availableBalance) {
        this.responseCode = responseCode;
        this.ledgerBalance = Optional.ofNullable(ledgerBalance);
        this.availableBalance = Optional.ofNullable(availableBalance);
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public Optional<Integer> getLedgerBalance() {
        return ledgerBalance;
    }

    public Optional<Integer> getAvailableBalance() {
        return availableBalance;
    }

    @Override
    public String toString() {
        return String.format("Trx response => code: [%s] - balance [%d - %d]", getResponseCode().getCode(), getLedgerBalance().orElse(0), getAvailableBalance().orElse(0));
    }
}
