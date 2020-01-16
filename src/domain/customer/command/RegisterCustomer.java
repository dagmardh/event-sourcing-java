package domain.customer.command;

import domain.customer.ConfirmationHash;
import domain.customer.CustomerId;
import domain.customer.EMailAddress;

public class RegisterCustomer {

    private CustomerId customerId;
    private ConfirmationHash confirmationHash;
    private EMailAddress eMailAddress;

    public RegisterCustomer(String eMailAddress) {
        this.customerId = CustomerId.generate();
        this.eMailAddress = new EMailAddress(eMailAddress);
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
