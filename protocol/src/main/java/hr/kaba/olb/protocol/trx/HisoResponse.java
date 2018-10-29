package hr.kaba.olb.protocol.trx;

import hr.kaba.olb.codec.constants.ResponseCode;

import java.util.Optional;

public class HisoResponse {

    private final ResponseCode responseCode;
    private final Optional<Integer> ledgerBalance;
    private final Optional<Integer> availableBalance;
    private final Optional<String> approvedCode;

    public HisoResponse(ResponseCode responseCode, Integer ledgerBalance, Integer availableBalance, String approvedCode) {
        this.responseCode = responseCode;
        this.ledgerBalance = Optional.ofNullable(ledgerBalance);
        this.availableBalance = Optional.ofNullable(availableBalance);
        this.approvedCode = Optional.ofNullable(approvedCode);
    }

    ResponseCode getResponseCode() {
        return responseCode;
    }

    Optional<Integer> getLedgerBalance() {
        return ledgerBalance;
    }

    Optional<Integer> getAvailableBalance() {
        return availableBalance;
    }

    public Optional<String> getApprovedCode() {
        return approvedCode;
    }

    @Override
    public String toString() {
        return String.format("Trx response => code: [%s] - balance [%d - %d]", getResponseCode().getCode(), getLedgerBalance().orElse(0), getAvailableBalance().orElse(0));
    }

    public static final HisoResponse NO_RESPONSE = null;
}
