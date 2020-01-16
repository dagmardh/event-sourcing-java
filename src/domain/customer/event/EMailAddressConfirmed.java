package domain.customer.event;

import domain.Event;
import domain.customer.ConfirmationHash;
import domain.customer.CustomerId;

public class EMailAddressConfirmed implements Event {

    private CustomerId customerId;
    private ConfirmationHash confirmationHash;

    public EMailAddressConfirmed(CustomerId customerId, ConfirmationHash confirmationHash) {
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
