package domain.customer;

import domain.Event;
import domain.customer.command.ChangeEMailAddress;
import domain.customer.command.ConfirmEMailAddress;
import domain.customer.command.RegisterCustomer;
import domain.customer.event.CustomerRegistered;
import domain.customer.event.EMailAddressChanged;
import domain.customer.event.EMailAddressConfirmationFailed;
import domain.customer.event.EMailAddressConfirmed;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomerTest {

    @Test
    public void register_should_record_CustomerRegistered() {
        Customer customer = Customer.register(new RegisterCustomer("example@dot.com"));

        assertEquals(1, customer.getRecordedEvents().size());
        assertTrue(customer.getRecordedEvents().get(0) instanceof CustomerRegistered);
    }

    @Test
    public void register_should_pass_payload_to_event() {
        String eMailAddress = "example@dot.com";
        RegisterCustomer command = new RegisterCustomer(eMailAddress);

        Customer customer = Customer.register(command);

        CustomerRegistered event = (CustomerRegistered) customer.getRecordedEvents().get(customer.getRecordedEvents().size() - 1);
        assertEquals(command.getCustomerId(), event.getCustomerId());
        assertEquals(new EMailAddress(eMailAddress), event.getEMailAddress());
        assertEquals(command.getConfirmationHash(), event.getConfirmationHash());
    }

    @Test
    public void register_should_update_state() {
        RegisterCustomer command = new RegisterCustomer("example@dot.com");
        Customer customer = Customer.register(command);

        customer.confirmEMailAddress(new ConfirmEMailAddress(command.getCustomerId(), command.getConfirmationHash()));

        assertEquals(2, customer.getRecordedEvents().size());
        assertTrue(customer.getRecordedEvents().get(0) instanceof CustomerRegistered);
        assertTrue(customer.getRecordedEvents().get(1) instanceof EMailAddressConfirmed);
    }

    @Test
    public void confirmEMailAddress_should_record_EMailAddressConfirmed() {
        List<Event> eventStream = new ArrayList<>();
        CustomerId customerId = CustomerId.generate();
        EMailAddress eMailAddress = new EMailAddress("example@dot.com");
        ConfirmationHash confirmationHash = ConfirmationHash.generate();
        eventStream.add(new CustomerRegistered(customerId, eMailAddress, confirmationHash));
        Customer customer = Customer.reconstitute(eventStream);

        customer.confirmEMailAddress(new ConfirmEMailAddress(customerId, confirmationHash));

        assertEquals(1, customer.getRecordedEvents().size());
        assertTrue(customer.getRecordedEvents().get(0) instanceof EMailAddressConfirmed);
    }

    @Test
    public void confirmEMailAddress_should_pass_payload_to_event() {
        List<Event> eventStream = new ArrayList<>();
        CustomerId customerId = CustomerId.generate();
        EMailAddress eMailAddress = new EMailAddress("example@dot.com");
        ConfirmationHash confirmationHash = ConfirmationHash.generate();
        eventStream.add(new CustomerRegistered(customerId, eMailAddress, confirmationHash));
        Customer customer = Customer.reconstitute(eventStream);

        customer.confirmEMailAddress(new ConfirmEMailAddress(customerId, confirmationHash));

        EMailAddressConfirmed event = (EMailAddressConfirmed) customer.getRecordedEvents().get(customer.getRecordedEvents().size() - 1);
        assertEquals(customerId, event.getCustomerId());
        assertEquals(confirmationHash, event.getConfirmationHash());
    }

    @Test
    public void confirmEMailAddress_should_do_nothing_when_eMailAddress_is_already_confirmed() {
        List<Event> eventStream = new ArrayList<>();
        CustomerId customerId = CustomerId.generate();
        EMailAddress eMailAddress = new EMailAddress("example@dot.com");
        ConfirmationHash confirmationHash = ConfirmationHash.generate();
        eventStream.add(new CustomerRegistered(customerId, eMailAddress, confirmationHash));
        eventStream.add(new EMailAddressConfirmed(customerId, confirmationHash));
        Customer customer = Customer.reconstitute(eventStream);

        customer.confirmEMailAddress(new ConfirmEMailAddress(customerId, confirmationHash));

        assertEquals(0, customer.getRecordedEvents().size());
    }

    @Test
    public void confirmEMailAddress_should_record_EMailAddressConfirmationFailed_when_wrong_hash() {
        List<Event> eventStream = new ArrayList<>();
        CustomerId customerId = CustomerId.generate();
        EMailAddress eMailAddress = new EMailAddress("example@dot.com");
        ConfirmationHash confirmationHash = ConfirmationHash.generate();
        eventStream.add(new CustomerRegistered(customerId, eMailAddress, confirmationHash));
        Customer customer = Customer.reconstitute(eventStream);

        customer.confirmEMailAddress(new ConfirmEMailAddress(customerId, ConfirmationHash.generate()));

        assertEquals(1, customer.getRecordedEvents().size());
        Event event = customer.getRecordedEvents().get(0);
        assertTrue(event instanceof EMailAddressConfirmationFailed);
        assertEquals(customerId, ((EMailAddressConfirmationFailed)event).getCustomerId());
    }

    @Test
    public void changeEMailAddress_should_record_EMailAddressChanged() {
        List<Event> eventStream = new ArrayList<>();
        CustomerId customerId = CustomerId.generate();
        EMailAddress eMailAddress = new EMailAddress("example@dot.com");
        ConfirmationHash confirmationHash = ConfirmationHash.generate();
        eventStream.add(new CustomerRegistered(customerId, eMailAddress, confirmationHash));
        eventStream.add(new EMailAddressConfirmed(customerId, confirmationHash));
        Customer customer = Customer.reconstitute(eventStream);

        EMailAddress newEMailAddress = new EMailAddress("john.doe@dot.com");
        customer.changeEMailAddress(new ChangeEMailAddress(customerId, newEMailAddress));

        assertEquals(1, customer.getRecordedEvents().size());
        assertTrue(customer.getRecordedEvents().get(0) instanceof EMailAddressChanged);
    }

    @Test
    public void changeEMailAddress_should_pass_payload_to_event() {
        List<Event> eventStream = new ArrayList<>();
        CustomerId customerId = CustomerId.generate();
        EMailAddress eMailAddress = new EMailAddress("example@dot.com");
        ConfirmationHash confirmationHash = ConfirmationHash.generate();
        eventStream.add(new CustomerRegistered(customerId, eMailAddress, confirmationHash));
        eventStream.add(new EMailAddressConfirmed(customerId, confirmationHash));
        Customer customer = Customer.reconstitute(eventStream);

        EMailAddress newEMailAddress = new EMailAddress("john.doe@dot.com");
        customer.changeEMailAddress(new ChangeEMailAddress(customerId, newEMailAddress));

        EMailAddressChanged event = (EMailAddressChanged) customer.getRecordedEvents().get(0);
        assertEquals(customerId, event.getCustomerId());
        assertEquals(newEMailAddress, event.getEMailAddress());
    }

    @Test
    public void changeEMailAddress_should_do_nothing_when_eMailAddress_is_unchanged() {
        List<Event> eventStream = new ArrayList<>();
        CustomerId customerId = CustomerId.generate();
        EMailAddress eMailAddress = new EMailAddress("example@dot.com");
        ConfirmationHash confirmationHash = ConfirmationHash.generate();
        eventStream.add(new CustomerRegistered(customerId, eMailAddress, confirmationHash));
        eventStream.add(new EMailAddressConfirmed(customerId, confirmationHash));
        Customer customer = Customer.reconstitute(eventStream);

        customer.changeEMailAddress(new ChangeEMailAddress(customerId, eMailAddress));

        assertEquals(0, customer.getRecordedEvents().size());
    }

    @Test
    public void changeEMailAddress_should_update_state() {
        List<Event> eventStream = new ArrayList<>();
        CustomerId customerId = CustomerId.generate();
        EMailAddress eMailAddress = new EMailAddress("example@dot.com");
        ConfirmationHash confirmationHash = ConfirmationHash.generate();
        eventStream.add(new CustomerRegistered(customerId, eMailAddress, confirmationHash));
        eventStream.add(new EMailAddressConfirmed(customerId, confirmationHash));
        EMailAddress newEMailAddress = new EMailAddress("john.doe@dot.com");
        ConfirmationHash newConfirmationHash = ConfirmationHash.generate();
        eventStream.add(new EMailAddressChanged(customerId, newEMailAddress, newConfirmationHash));
        Customer customer = Customer.reconstitute(eventStream);

        customer.changeEMailAddress(new ChangeEMailAddress(customerId, newEMailAddress));

        assertEquals(0, customer.getRecordedEvents().size());

        customer.confirmEMailAddress(new ConfirmEMailAddress(customerId, newConfirmationHash));

        assertEquals(1, customer.getRecordedEvents().size());
        assertTrue(customer.getRecordedEvents().get(0) instanceof EMailAddressConfirmed);
    }
}
