package com.simbirsoft.kondratyev.ruslan.parser.mappers;

import com.simbirsoft.kondratyev.ruslan.parser.dto.WordDTO;
import com.simbirsoft.kondratyev.ruslan.parser.models.Word;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WordMapper {
    WordDTO WordToWordDto(Word word);
    Word WordDtoToWord(WordDTO wordDTO);
}