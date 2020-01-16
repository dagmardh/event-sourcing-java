package domain.customer;

import java.util.Objects;

public class EMailAddress {

    private String eMailAddress;

    public EMailAddress(String eMailAddress) {
        this.eMailAddress = normalize(eMailAddress);
        validate();
    }

    private String normalize(String eMailAddress) {
        return eMailAddress.trim();
    }

    private void validate() {
        String regex = "^(.+)@(.+)$";
        if (!eMailAddress.matches(regex)) {
            throw new IllegalArgumentException("invalid eMail address");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EMailAddress that = (EMailAddress) o;
        return Objects.equals(eMailAddress, that.eMailAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eMailAddress);
    }
}
