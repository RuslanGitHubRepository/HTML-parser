package com.simbirsoft.kondratyev.ruslan.parser.dto;

import com.simbirsoft.kondratyev.ruslan.parser.models.Page;
import com.simbirsoft.kondratyev.ruslan.parser.models.Word;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StatisticsDTO {
    private WordDTO word;
    private PageDTO page;
    private Integer countWord;
}