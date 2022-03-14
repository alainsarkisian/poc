package com.alain.mapper;
import com.alain.dto.Intern;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InternMapper {
    InternMapper MAPPER = Mappers.getMapper( InternMapper.class );

    @Mapping(source = "idIntern", target = "idIntern")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target= "lastName")
    com.alain.model.Intern fromXmlToEntityIntern(Intern intern);
}
