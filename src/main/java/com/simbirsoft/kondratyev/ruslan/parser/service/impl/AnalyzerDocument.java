package com.simbirsoft.kondratyev.ruslan.parser.service.impl;

import com.simbirsoft.kondratyev.ruslan.parser.dto.StatisticsDTO;
import com.simbirsoft.kondratyev.ruslan.parser.models.Page;
import com.simbirsoft.kondratyev.ruslan.parser.models.Statistics;
import com.simbirsoft.kondratyev.ruslan.parser.models.Word;
import com.simbirsoft.kondratyev.ruslan.parser.repository.PageRepository;
import com.simbirsoft.kondratyev.ruslan.parser.service.*;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

@NoArgsConstructor

public class AnalyzerDocument implements IAnalyzerDocument {
    List<String> fileUrls;
    @Autowired
    private ICreatAndSaveHTMLFile manipulatorHTMLFiles;
    @Autowired
    private IParserAnalyzer parserAnalyzer;
    @Autowired
    private IPageService pageService;
    @Autowired
    private IWordService wordService;
    @Autowired
    private IStatisticService statisticService;

    private class WaitThread extends Thread {
        public void run(){
            try {
                while (!isInterrupted()) {
                    System.out.print(". ");
                    Thread.sleep(500);
                }
            }catch (InterruptedException exc){}
        }
    }
    public void obtainHtmlFiles(){
        manipulatorHTMLFiles.creatLocalHtmlFiles(fileUrls);
        manipulatorHTMLFiles.saveAndFillingCreatedFiles();
    }
    public Map<String, List<String>> ReadAndParseHtmlFile(){
        Map<String, File> fileList = manipulatorHTMLFiles.getHtmlFiles();
        Map<String, List<String>> parserResult = parserAnalyzer.parser(fileList);
        return parserResult;
    }
    public Map<String,Map<String, Integer>> analyzeDataHtmlFile(Map<String, List<String>> parserResult){
        Map<String, Map<String, Integer>> resultParser = parserAnalyzer.creatStatistics(parserResult);
        BiConsumer<String, Map<String, Integer>> resultParserDataBaseHandler = (pageUrl, resultStat)->{
            Page currentPage = pageService.checkPage(pageUrl);
            if(currentPage.getId() == null){
                pageService.uploadPage(currentPage);
                currentPage = pageService.checkPage(pageUrl);
            }
            for(Map.Entry<String, Integer>  stat : resultStat.entrySet()) {
                Word currentWord = wordService.checkWord(stat.getKey());
                if (currentWord.getId() == null) {
                    wordService.uploadWord(currentWord);
                    currentWord = wordService.checkWord(stat.getKey());
                }
                List<Statistics> statisticsList = statisticService.checkStatistic(currentPage, currentWord);
                if(statisticsList.isEmpty()){
                    statisticsList = new ArrayList<Statistics>(Arrays.asList(new Statistics()));
                }
                for(Statistics statistic : statisticsList){
                    statistic.setPage(currentPage);
                    statistic.setWord(currentWord);
                    statistic.setCountWord(stat.getValue());
                };
                statisticService.uploadStatistic(statisticsList);
            };
        };
        WaitThread waitThread = new WaitThread();
        waitThread.start();
        resultParser.forEach(resultParserDataBaseHandler);
        waitThread.interrupt();
        return resultParser;
    }
    public void setFileName(String... fileName) {
        this.fileUrls = Arrays.asList(fileName);
        manipulatorHTMLFiles.restartCreations();
    }

    @Override
    public List<StatisticsDTO> downloadStatisticFromBD(String url) {
        return statisticService.downloadStatistics(url);
    }
}
