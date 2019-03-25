package com.simbirsoft.kondratyev.ruslan.parser.mappers;

import com.simbirsoft.kondratyev.ruslan.parser.dto.PageDTO;
import com.simbirsoft.kondratyev.ruslan.parser.models.Page;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.jaxb.SpringDataJaxb;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PageMapper {
   PageDTO PageToPageDto(Page page);
   Page PageDtoToPage(PageDTO pageDTO);
}