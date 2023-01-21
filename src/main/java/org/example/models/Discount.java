package org.example.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class Discount implements Serializable, Container {
    @Serial
    private static final long serialVersionUID = 17;
    User user;
    int percent;
    int amountSpent;

    @Override
    public boolean isEmpty() {
        return user.isEmpty() && percent == 0;
    }
}
