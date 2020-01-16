package domain.customer.command;

import domain.customer.ConfirmationHash;
import domain.customer.CustomerId;

public class ConfirmEMailAddress {

    private CustomerId customerId;
    private ConfirmationHash confirmationHash;

    public ConfirmEMailAddress(CustomerId customerId, ConfirmationHash confirmationHash) {
        this.customerId = customerId;
        this.confirmationHash = confirmationHash;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public ConfirmationHash getConfirmationHash() {
        return confirmationHash;
    }
}
