package hr.kaba.olb.codec.message;

import hr.kaba.olb.codec.constants.InitiatorType;
import hr.kaba.olb.codec.constants.ProductIndicator;

public class Base24Header {
    private final ProductIndicator productIndicator;
    private final String releaseNumber;
    private final String status;
    private final InitiatorType originatorCode;
    private final InitiatorType responderCode;

    public Base24Header(ProductIndicator productIndicator, String releaseNumber, String status, InitiatorType originatorCode, InitiatorType responderCode) {
        this.productIndicator = productIndicator;
        this.releaseNumber = releaseNumber;
        this.status = status;
        this.originatorCode = originatorCode;
        this.responderCode = responderCode;
    }

    public static boolean isValidHeader(String from) {
        return ((from != null) && (from.length() == 9));
    }

    public static Base24Header parse(String from) throws IllegalArgumentException {

        if (!isValidHeader(from)) {
            throw new IllegalArgumentException(String.format("Invalid base 24 header: %s", from));
        }

        Builder builder = new Base24Header.Builder();
        builder.setMessage(from)
                .setProductIndicator(from.substring(0, 2))
                .setReleaseNumber(from.substring(2, 4))
                .setStatus(from.substring(4, 7))
                .setOriginatorCode(from.substring(7, 8))
                .setResponderCode(from.substring(8, 9));

        return builder.build();
    }


    public ProductIndicator getProductIndicator() {
        return productIndicator;
    }

    public String getReleaseNumber() {
        return releaseNumber;
    }

    public String getStatus() {
        return status;
    }

    public InitiatorType getOriginatorCode() {
        return originatorCode;
    }

    public InitiatorType getResponderCode() {
        return responderCode;
    }


    public boolean isATM() {
        return getProductIndicator() == ProductIndicator.ATM;
    }

    public boolean isPOS() {
        return getProductIndicator() == ProductIndicator.POS;
    }

    public static Base24Header copyWithResponderCode(Base24Header header, InitiatorType responderCode) {
        return new Base24Header(header.getProductIndicator(), header.getReleaseNumber(), header.getStatus(), header.getOriginatorCode(), responderCode);
    }

    public static class Builder {
        private String message;

        private ProductIndicator productIndicator;
        private String releaseNumber;
        private String status;
        private InitiatorType originatorCode;
        private InitiatorType responderCode;

        public Builder() {
        }

        public Builder setProductIndicator(String productIndicator) {
            this.productIndicator = ProductIndicator.find(productIndicator);
            return this;
        }

        public Builder setReleaseNumber(String releaseNumber) {
            this.releaseNumber = releaseNumber;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder setOriginatorCode(String originatorCode) {
            this.originatorCode = InitiatorType.find(originatorCode);
            return this;
        }

        public Builder setResponderCode(String responderCode) {
            this.responderCode = InitiatorType.find(responderCode);
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Base24Header build() {
            return new Base24Header(productIndicator, releaseNumber, status, originatorCode, responderCode);
        }

    }


    @Override
    public String toString() {
        return String.format("%s%s%s%s%s", getProductIndicator().getCode(), getReleaseNumber(), getStatus(), getOriginatorCode().getCode(), getResponderCode().getCode());
    }
}
