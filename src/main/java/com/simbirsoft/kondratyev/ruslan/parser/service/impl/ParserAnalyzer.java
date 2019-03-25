package com.simbirsoft.kondratyev.ruslan.parser.service.impl;

import com.simbirsoft.kondratyev.ruslan.parser.service.IParserAnalyzer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ParserAnalyzer implements IParserAnalyzer {
    @Override
    public Map<String, List<String>> parser( Map<String, File> htmlFiles) {
        Map<String, List<String>> parseResult = new HashMap<>();
        BiConsumer<String, File> saveFiles = (url, file)->{
            try {
                Document doc = Jsoup.parse(file, "UTF-8", url);
                Elements elements = doc.getElementsMatchingOwnText("[\\w\\d\\W]+");
                List<String> lexems = new ArrayList();
                Consumer<Element> creatLexems = element -> {
                    String name = element.tag().getName();
                    if(!name.equals("script") && !name.equals("noscript"))
                        lexems.add(element.ownText());
                };
                elements.forEach(creatLexems);
                parseResult.put(url, lexems);
            }catch(IOException exc){
                throw new RuntimeException(exc.getMessage());
            }
        };
        htmlFiles.forEach(saveFiles);
        return parseResult;
    }

    @Override
    public Map<String, Map<String, Integer>> creatStatistics(Map<String, List<String>> parserResult) {
        Map<String, Map<String, Integer>> resultStatistics = new HashMap<>();
        BiConsumer<String, List<String>> creatStatistics = (url, lexems)->{

            List<String> splitLexems = new ArrayList();
            lexems.forEach(string->splitLexems.addAll(Arrays.asList(string.split("[\\s\"\\«\\».,!?]+"))));
            Map<String,Integer> resultAnalizer = new HashMap();
            splitLexems.removeIf((String string)-> {return string.isEmpty(); });
            splitLexems.forEach(string-> {
                if(resultAnalizer.containsKey(string)){
                    Integer count = resultAnalizer.get(string);
                    resultAnalizer.put(string,++count);
                }
                else
                    resultAnalizer.put(string,1);
            });
            resultStatistics.put(url,resultAnalizer);
        };
        parserResult.forEach(creatStatistics);
        return resultStatistics;
    }
}
