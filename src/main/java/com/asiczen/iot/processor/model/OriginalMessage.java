package com.asiczen.iot.processor.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper=true, includeFieldNames=true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OriginalMessage {

	@JsonProperty("IMEI")
	private String imei;

	@JsonProperty("GPS")
	private int gps;

	@JsonProperty("Lat")
	private double lat;

	@JsonProperty("Lng")
	private double lng;

	@JsonProperty("IsKeyOn")
	private boolean isKeyOn;

	@JsonProperty("Heading")
	private int heading;

	@JsonProperty("Timestamp")
	private Date timestamp;

	@JsonProperty("Unplugged")
	private int unplugged;

	@JsonProperty("Fuel")
	private int fuel;

	@JsonProperty("Speed")
	private int speed;

}