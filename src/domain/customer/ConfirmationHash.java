package domain.customer;

import java.util.Objects;
import java.util.UUID;

public class ConfirmationHash {

    private String value;

    private ConfirmationHash(String hash) {
        this.value = hash;
    }

    public static ConfirmationHash generate() {
        UUID uuid = UUID.randomUUID();
        return new ConfirmationHash(uuid.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfirmationHash that = (ConfirmationHash) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
