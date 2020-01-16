package domain.customer.event;

import domain.Event;
import domain.customer.CustomerId;

public class EMailAddressConfirmationFailed implements Event {

    private CustomerId customerId;

    public EMailAddressConfirmationFailed(CustomerId customerId) {
        this.customerId = customerId;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

}
