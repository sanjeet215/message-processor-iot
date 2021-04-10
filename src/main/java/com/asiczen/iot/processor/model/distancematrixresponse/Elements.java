package com.asiczen.iot.processor.model.distancematrixresponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Elements {

    private Distance distance;
    private Duration duration;
    private String status;
}
