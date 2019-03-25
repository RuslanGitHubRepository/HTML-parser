package com.simbirsoft.kondratyev.ruslan.parser.service.impl;

import com.simbirsoft.kondratyev.ruslan.parser.dto.StatisticsDTO;
import com.simbirsoft.kondratyev.ruslan.parser.mappers.PageMapper;
import com.simbirsoft.kondratyev.ruslan.parser.mappers.StatisticsMapper;
import com.simbirsoft.kondratyev.ruslan.parser.mappers.WordMapper;
import com.simbirsoft.kondratyev.ruslan.parser.models.Page;
import com.simbirsoft.kondratyev.ruslan.parser.models.Statistics;
import com.simbirsoft.kondratyev.ruslan.parser.models.Word;
import com.simbirsoft.kondratyev.ruslan.parser.repository.StatisticRepository;
import com.simbirsoft.kondratyev.ruslan.parser.service.IStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StatisticServiceImpl implements IStatisticService {
    @Autowired
    StatisticRepository statisticRepository;
    @Autowired
    private StatisticsMapper statisticsMapper;
    @Override
    public void uploadStatistic(List<Statistics> listStatistics)
    {
        statisticRepository.saveAll(listStatistics);
    }
    @Override
    @Transactional(readOnly = true)
    public List<StatisticsDTO> downloadStatistics(String urlPage) {
        List<Statistics> statistacs = statisticRepository.findByPage_Url(urlPage);
        List<StatisticsDTO> result = new ArrayList();
        statistacs.forEach(statistic-> {
            result.add(statisticsMapper.StaticticsToStaticticsDto(statistic));
        });
        return result;
    }
    @Override
    public List<Statistics> checkStatistic(Page page, Word word) {
        List<Statistics> statisticsList = statisticRepository.findByPage_UrlAndWord_Word(page.getUrl(), word.getWord());
        return statisticsList;
    }


}
