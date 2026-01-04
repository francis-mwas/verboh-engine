package com.coop.demo.coopVerboh.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class STTResponse {

    private String text;
    private String language;
    private double confidence;
}
