package com.asiczen.iot.processor.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.asiczen.iot.processor.model.TransformedMessage;
import com.asiczen.iot.processor.model.VehicleInfo;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VehicleInfoMapper {

	VehicleInfoMapper Mapper = Mappers.getMapper(VehicleInfoMapper.class);

	@Mapping(source = "vehicleType", target = "vehicleType")
	@Mapping(source = "vehicleNumber", target = "vehicleNumber")
	@Mapping(source = "driverName", target = "driverName")
	@Mapping(source = "driverNumber", target = "driverContact")
	@Mapping(source = "orgRefName", target = "orgRefName")
	void updateVehcileInfoToTransformedMessage(VehicleInfo vehicle,@MappingTarget  TransformedMessage transformedMessage);
}
