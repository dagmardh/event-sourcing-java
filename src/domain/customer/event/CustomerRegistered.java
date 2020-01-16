package domain.customer.event;

import domain.Event;
import domain.customer.ConfirmationHash;
import domain.customer.CustomerId;
import domain.customer.EMailAddress;

public class CustomerRegistered implements Event {

    private CustomerId customerId;
    private EMailAddress eMailAddress;
    private ConfirmationHash confirmationHash;

    public CustomerRegistered(CustomerId customerId, EMailAddress eMailAddress, ConfirmationHash confirmationHash) {
        this.customerId = customerId;
        this.eMailAddress = eMailAddress;
        this.confirmationHash = confirmationHash;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public EMailAddress getEMailAddress() {
        return eMailAddress;
    }

    public ConfirmationHash getConfirmationHash() {
        return confirmationHash;
    }
}
