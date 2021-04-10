package com.asiczen.iot.processor.model.distancematrixresponse;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rows {
    private List<Elements> elements;

}
