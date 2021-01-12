package com.asiczen.iot.processor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AlertMessage {

	private String orgRefName;
	private String vehicleNumber;
	private String driverName;
	private AlertType alertType;
}
