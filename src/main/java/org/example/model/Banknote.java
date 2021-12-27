package org.example.model;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Banknote {
    String  currencyCode;
    int nominal;
    int qty;
    List<InfoData> no;


    @Override
    public String toString() {
        return currencyCode +"," +
                ", " + nominal +
                ", " + qty +
                ", no=" + no;
    }
}
