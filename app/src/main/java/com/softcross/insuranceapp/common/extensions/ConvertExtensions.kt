package com.softcross.insuranceapp.common.extensions

import com.softcross.insuranceapp.data.dto.address.DistrictDto
import com.softcross.insuranceapp.data.dto.address.ProvincesDto
import com.softcross.insuranceapp.data.dto.customer.CustomerDto
import com.softcross.insuranceapp.data.dto.dask.DaskDto
import com.softcross.insuranceapp.data.dto.health.HealthDto
import com.softcross.insuranceapp.data.dto.policy.PolicyDto
import com.softcross.insuranceapp.data.dto.traffic_kasko.TrafficKaskoDto
import com.softcross.insuranceapp.domain.model.Customer
import com.softcross.insuranceapp.domain.model.Dask
import com.softcross.insuranceapp.domain.model.District
import com.softcross.insuranceapp.domain.model.Health
import com.softcross.insuranceapp.domain.model.Kasko
import com.softcross.insuranceapp.domain.model.Policy
import com.softcross.insuranceapp.domain.model.Province
import com.softcross.insuranceapp.domain.model.Traffic

fun ProvincesDto.toProvince() = Province(
    id = id,
    name = name,
    districts = districts.map { it.toDistrict() }
)

fun DistrictDto.toDistrict() = District(
    id = id,
    name = name
)

fun CustomerDto.toCustomer() = Customer(
    id = id,
    name = name,
    surname = surname,
    birthdate = birthdate,
    city = city,
    phone = phone,
    email = email,
    district = district
)

fun Customer.toCustomerDto() = CustomerDto(
    id = id,
    name = name,
    surname = surname,
    birthdate = birthdate ?: "",
    city = city,
    phone = phone,
    email = email,
    district = district
)

fun Policy.toPolicyDto() = PolicyDto(
    customerNo = customerNo,
    policyAgent = policyAgent,
    policyEndDate = policyEndDate ?: "",
    policyEnterDate = policyEnterDate,
    policyNo = policyNo,
    policyPrim = policyPrim,
    policyStartDate = policyStartDate ?: "",
    policyStatus = policyStatus,
    policyTypeCode = policyTypeCode
)

fun PolicyDto.toPolicy() = Policy(
    customerNo = customerNo,
    policyAgent = policyAgent,
    policyEndDate = policyEndDate,
    policyEnterDate = policyEnterDate,
    policyNo = policyNo,
    policyPrim = policyPrim,
    policyStartDate = policyStartDate,
    policyStatus = policyStatus,
    policyTypeCode = policyTypeCode
)

fun TrafficKaskoDto.toTraffic() = Traffic(
    policyNo = policyNo,
    plate = plateProvinceCode,
    plateCode = plateCode,
    make = carMark,
    model = carModel,
    year = modelYear,
    engineNo = carMotorNo,
    chassisNo = carChassisNo
)

fun Traffic.toTrafficDto() = TrafficKaskoDto(
    policyNo = policyNo,
    plateProvinceCode = plate,
    plateCode = plateCode,
    carMark = make,
    carModel = model,
    modelYear = year,
    carMotorNo = engineNo,
    carChassisNo = chassisNo
)

fun Kasko.toKaskoDto() = TrafficKaskoDto(
    policyNo = policyNo,
    plateProvinceCode = plate,
    plateCode = plateCode,
    carMark = make,
    carModel = model,
    modelYear = year,
    carMotorNo = engineNo,
    carChassisNo = chassisNo
)

fun TrafficKaskoDto.toKasko() = Kasko(
    policyNo = policyNo,
    plate = plateProvinceCode,
    plateCode = plateCode,
    make = carMark,
    model = carModel,
    year = modelYear,
    engineNo = carMotorNo,
    chassisNo = carChassisNo
)

fun HealthDto.toHealth() = Health(
    policyNo = policyNo,
    smoke = smoke,
    alcohol = alcohol,
    drugs = drugs,
    sport = sport,
    surgery = surgery,
    allergy = allergy
)

fun Health.toHealthDto() = HealthDto(
    policyNo = policyNo,
    smoke = smoke,
    alcohol = alcohol,
    drugs = drugs,
    sport = sport,
    surgery = surgery,
    allergy = allergy
)

fun Dask.toDaskDto() = DaskDto(
    policyNo = policyNo,
    uavt = uavt,
    apartmentMeter = apartmentMeter,
    apartmentFloor = apartmentFloor,
    apartmentAge = apartmentAge,
    apartmentType = apartmentType
)

fun DaskDto.toDask() = Dask(
    policyNo = policyNo,
    uavt = uavt,
    apartmentMeter = apartmentMeter,
    apartmentFloor = apartmentFloor,
    apartmentAge = apartmentAge,
    apartmentType = apartmentType
)