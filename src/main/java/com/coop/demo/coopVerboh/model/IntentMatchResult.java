package com.coop.demo.coopVerboh.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IntentMatchResult {
    private String intentCode;
    private double confidence;

    public static IntentMatchResult unknown() {
        return new IntentMatchResult("UNKNOWN", 0.0);
    }
}
