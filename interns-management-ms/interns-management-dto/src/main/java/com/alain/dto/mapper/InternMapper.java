package com.alain.dto.mapper;
import com.alain.dto.Intern;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InternMapper {
    InternMapper MAPPER = Mappers.getMapper( InternMapper.class );

    @Mapping(source = "idInter", target = "idIntern")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    Intern fromJsonToXmlIntern(com.alain.dto.json.Intern intern);

    @Mapping(source = "idIntern", target = "idInter")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    com.alain.dto.json.Intern fromXmlToJsonIntern(Intern intern);
}
