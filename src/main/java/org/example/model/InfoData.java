package org.example.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InfoData {
    String sn;
    int atm;
    int fit;
    int unfit;
    String error;

    @Override
    public String toString() {
        return "{" +
                "sn='" + sn + '\'' +
                ", atm=" + atm +
                ", fit=" + fit +
                ", unfit=" + unfit +
                ", error='" + error + '\'' +
                '}';
    }
}
