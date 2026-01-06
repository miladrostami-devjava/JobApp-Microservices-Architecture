package com.jobapp.companyms.mapper;

import com.jobapp.companyms.bean.Company;
import com.jobapp.companyms.entity.CompanyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",
nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CompanyMapper {

    Company toBean(CompanyEntity companyEntity);
    @Mapping(target = "ratingSum",ignore = true)
    CompanyEntity toEntity(Company company);

    List<Company> toBeanList(List<CompanyEntity> companyEntities);

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "ratingSum",ignore = true)
    void updateEntityFromBean(Company company, @MappingTarget CompanyEntity companyEntity);

}
