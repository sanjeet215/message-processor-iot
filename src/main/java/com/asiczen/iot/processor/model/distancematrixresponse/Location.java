package com.asiczen.iot.processor.model.distancematrixresponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    @NotNull
    private double lng;

    @NotNull
    private double lat;
}
