package com.evotek.cache.service.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import com.evotek.cache.model.Department;
import com.evotek.cache.model.dto.DepartmentDTO;
import com.evotek.cache.repository.StaffRepository;

@Mapper(componentModel = "spring")
public interface DepartmentMapper extends EntityMapper<DepartmentDTO, Department> {
	@Override
	Department toEntity(DepartmentDTO dto);

	@Named(value = "extra")
	DepartmentDTO toDto(Department entity, @Context StaffRepository staffRepository);

	@AfterMapping
	default void afterMapping(@MappingTarget DepartmentDTO dto, Department entity) {
	    dto.setCode(String.valueOf(entity.getId()) + "-" + entity.getName());
	}
	
	@AfterMapping
    default void afterMapping(@MappingTarget DepartmentDTO dto, Department entity,
                    @Context StaffRepository staffRepository) {
        dto.setStaffIds(staffRepository.getStaffIds());
    }
}
