package domain.customer;

import java.util.Objects;
import java.util.UUID;

public class CustomerId {

    private String value;

    private CustomerId(String id) {
        this.value = id;
    }

    public static CustomerId generate() {
        UUID uuid = UUID.randomUUID();
        return new CustomerId(uuid.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerId that = (CustomerId) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
