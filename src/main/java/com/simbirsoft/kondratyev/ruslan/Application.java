package com.simbirsoft.kondratyev.ruslan;


import com.simbirsoft.kondratyev.ruslan.parser.configuration.SpringBeanContextConfiguration;
import com.simbirsoft.kondratyev.ruslan.parser.dto.StatisticsDTO;
import com.simbirsoft.kondratyev.ruslan.parser.models.Page;
import com.simbirsoft.kondratyev.ruslan.parser.models.Statistics;
import com.simbirsoft.kondratyev.ruslan.parser.models.Word;
import com.simbirsoft.kondratyev.ruslan.parser.repository.PageRepository;
import com.simbirsoft.kondratyev.ruslan.parser.service.IAnalyzerDocument;
import com.simbirsoft.kondratyev.ruslan.parser.service.IPageService;
import com.simbirsoft.kondratyev.ruslan.parser.service.IStatisticService;
import com.simbirsoft.kondratyev.ruslan.parser.service.IWordService;
import com.simbirsoft.kondratyev.ruslan.parser.service.impl.WordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class Application{


    public static void main(String... args) {

        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(SpringBeanContextConfiguration.class);
        IAnalyzerDocument analyzerDocument = annotationConfigApplicationContext.getBean(IAnalyzerDocument.class);
        analyzerDocument.setFileName(args);
        analyzerDocument.obtainHtmlFiles();
        Map<String, List<String>> readAndParseHtmlFiles = analyzerDocument.ReadAndParseHtmlFile();
        analyzerDocument.analyzeDataHtmlFile(readAndParseHtmlFiles);
        List<List<StatisticsDTO>> analyzeDataHtmlFiles = new ArrayList();
        for(int i=0; i<args.length;i++) {
            analyzeDataHtmlFiles.add(analyzerDocument.downloadStatisticFromBD(args[i]));
        }
        System.out.println("\n");
        analyzeDataHtmlFiles.forEach(list_statisticsDto -> {
            System.out.println(list_statisticsDto.get(0).getPage().getUrl());
            list_statisticsDto.forEach(statisticsDto->System.out.println(statisticsDto.getWord().getWord()+": "+statisticsDto.getCountWord()));
        });
    }
}