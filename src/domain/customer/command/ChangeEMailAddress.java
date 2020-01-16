package domain.customer.command;

import domain.customer.ConfirmationHash;
import domain.customer.CustomerId;
import domain.customer.EMailAddress;

public class ChangeEMailAddress {

    private CustomerId customerId;
    private EMailAddress eMailAddress;
    private ConfirmationHash confirmationHash;

    public ChangeEMailAddress(CustomerId customerId, EMailAddress eMailAddress) {
        this.customerId = customerId;
        this.eMailAddress = eMailAddress;
        this.confirmationHash = ConfirmationHash.generate();
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
