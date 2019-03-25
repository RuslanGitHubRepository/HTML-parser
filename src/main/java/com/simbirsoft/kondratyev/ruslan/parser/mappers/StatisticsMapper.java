package com.simbirsoft.kondratyev.ruslan.parser.mappers;

import com.simbirsoft.kondratyev.ruslan.parser.dto.StatisticsDTO;
import com.simbirsoft.kondratyev.ruslan.parser.models.Statistics;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {WordMapper.class, PageMapper.class})
public interface StatisticsMapper {
    StatisticsDTO StaticticsToStaticticsDto(Statistics statictics);
    Statistics StaticticsDtoToStatictics(StatisticsDTO staticticsDTO);
}