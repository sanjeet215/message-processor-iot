package com.asiczen.iot.processor.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.asiczen.iot.processor.model.OriginalMessage;
import com.asiczen.iot.processor.model.TransformedMessage;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OriginalMessageMapper {

	OriginalMessageMapper Mapper = Mappers.getMapper(OriginalMessageMapper.class);
	
	@Mapping(source = "imei" , target = "imei")
	@Mapping(source = "speed" , target = "speed")
	@Mapping(source = "fuel" , target = "fuel")
	@Mapping(source = "lng" , target = "lng")
	@Mapping(source = "lat" , target = "lat")
	@Mapping(source = "keyOn" , target = "keyOn")
	@Mapping(source = "heading" , target = "heading")
	@Mapping(source = "unplugged" , target = "unplugged")
	void updateTransformedMessagewithOrginalMsgData(OriginalMessage message,@MappingTarget TransformedMessage transformedMessage);
}
