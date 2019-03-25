package com.simbirsoft.kondratyev.ruslan.parser.service;

import com.simbirsoft.kondratyev.ruslan.parser.dto.StatisticsDTO;
import com.simbirsoft.kondratyev.ruslan.parser.models.Statistics;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Интерфейс анализа файлов, составленных из загруженных ранее интернет страниц по заданным URL-адресам.
 * @author Ruslan Kondratyev
 * @version 1.0
 */
public interface IAnalyzerDocument {
    /**
     * Метод {@literal obtainHtmlFiles} создает и заполняет локальные HTML-файлы. Поведение метода определяется
     * методами {@link ICreatAndSaveHTMLFile#creatLocalHtmlFiles(List)} и {@link ICreatAndSaveHTMLFile#saveAndFillingCreatedFiles()}
     */
    void obtainHtmlFiles();

    /**
     * Метод {@literal ReadAndParseHtmlFile} производит чтение и парсинг сохраненных HTML-файлов.
     * @return Возвращает карту отображений URL-страницы на список разультата парсинга файла, содержащего HTML-код
     * страницы, загружаемой по URL-адресу
     */
    Map<String, List<String>> ReadAndParseHtmlFile();

    /**
     * Метод {@literal analyzeDataHtmlFile} проводит анализ результатов парсинга методом {@link IAnalyzerDocument#ReadAndParseHtmlFile}
     * @param lex Результат работы парсинга входных данных. Сохраняет полученные данные в базу данных.
     * @return Полученная статистика анализа результатов парсинга входной информации. Выводит карту отображения
     * URL-заданной страницы на карту отображения найденных на странице слов и количества вхождений.
     */
    Map<String, Map<String,Integer>> analyzeDataHtmlFile(Map<String, List<String>> lex);

    /**
     * Метод {@literal setFileName} задает список входящих URL-адресов анализируемых интернет страниц.
     * @param fileName массив входящих URL адрессов
     */
    void setFileName(String... fileName);

    /**
     * Метод {@literal downloadStatisticFromBD} загружает статистику по заданному URL-адресу старницы. Если в базе
     * данных данные отсутствуют, выводит пустой список.
     * @param Url URL-адрес страницы, статистику которой необходимо получить
     * @return возвращает список загруженной статистики из базы данных по заданному URL-адресу.
     */
    List<StatisticsDTO> downloadStatisticFromBD(String Url);
}
