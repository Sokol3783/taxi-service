package org.example.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class Discount implements Serializable {

    User user;
    int percent;
    int amountSpent;
}
