package com.asiczen.iot.processor.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleInfo implements Serializable {

	private static final long serialVersionUID = -2670607325919116429L;

	String imei;
	String vehicleNumber;
	String vehicleType;
	String driverName;
	String driverNumber;
	String orgRefName;

}