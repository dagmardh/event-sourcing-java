package domain.customer;

import domain.Event;
import domain.customer.command.ChangeEMailAddress;
import domain.customer.command.ConfirmEMailAddress;
import domain.customer.command.RegisterCustomer;
import domain.customer.event.CustomerRegistered;
import domain.customer.event.EMailAddressChanged;
import domain.customer.event.EMailAddressConfirmationFailed;
import domain.customer.event.EMailAddressConfirmed;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    private List<Event> recordedEvents;

    private ConfirmationHash confirmationHash;
    private boolean isEMailAddressConfirmed;
    private EMailAddress eMailAddress;
    
    private Customer() {
        recordedEvents = new ArrayList<>();
        isEMailAddressConfirmed = false;
    }
    
    public static Customer register(RegisterCustomer registerCustomer) {
        Customer customer = new Customer();
        customer.record(new CustomerRegistered(registerCustomer.getCustomerId(), registerCustomer.getEMailAddress(),
                registerCustomer.getConfirmationHash()));
        return customer;
    }

    public void confirmEMailAddress(ConfirmEMailAddress confirmEMailAddress) {
        if (isEMailAddressConfirmed) {
            return;
        }

        if (confirmEMailAddress.getConfirmationHash().equals(confirmationHash)) {
            Event event = new EMailAddressConfirmed(confirmEMailAddress.getCustomerId(), confirmEMailAddress.getConfirmationHash());
            record(event);
        } else {
            Event event = new EMailAddressConfirmationFailed(confirmEMailAddress.getCustomerId());
            record(event);
        }
    }

    private void record(Event event) {
        recordedEvents.add(event);
        apply(event);
    }

    public static Customer reconstitute(List<Event> eventStream) {
        Customer customer = new Customer();
        for (Event event : eventStream) {
            customer.apply(event);
        }
        return customer;
    }

    private void apply(Event event) {
        if (event instanceof CustomerRegistered) {
            confirmationHash = ((CustomerRegistered) event).getConfirmationHash();
            eMailAddress = ((CustomerRegistered) event).getEMailAddress();
        } else if (event instanceof EMailAddressConfirmed) {
            isEMailAddressConfirmed = true;
        } else if (event instanceof EMailAddressChanged) {
            eMailAddress = ((EMailAddressChanged) event).getEMailAddress();
            isEMailAddressConfirmed = false;
            confirmationHash = ((EMailAddressChanged) event).getConfirmationHash();
        }
    }

    public List<Event> getRecordedEvents() {
        return recordedEvents;
    }

    public void changeEMailAddress(ChangeEMailAddress command) {
        if (command.getEMailAddress().equals(eMailAddress)) {
            return;
        }

        record(new EMailAddressChanged(command.getCustomerId(), command.getEMailAddress(), command.getConfirmationHash()));
    }
}
