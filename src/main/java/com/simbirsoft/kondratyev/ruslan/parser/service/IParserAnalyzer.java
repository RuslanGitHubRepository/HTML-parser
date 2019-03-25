package com.simbirsoft.kondratyev.ruslan.parser.service;

import java.io.File;
import java.util.List;
import java.util.Map;
/**
 *Сервисный слой парсинга и анализа. Необходим для выполнения проведения процесса парсинга и получение результатов
 * разбора полученных данных.
 * @author Ruslan Kondratyev
 * @version 1.0
 */
public interface IParserAnalyzer {
    /**
     * Метод {@literal parser} проводит парсинг файла HTML-загруженного по заданному URL-адресу
     * @param htmlFile карта отображений URL-адреса страницы и полученный в результате загрузки файл, содержащий
     * HTML-код.
     * @return Возвращает результат парсинга HTML-файла в виде карты отображающей URL-адресс страницы и список
     * найденых в ней слов.
     */
    Map<String, List<String>> parser( Map<String, File> htmlFile);

    /**
     * Метод {@literal creatStatistics} Обеспечивает получение статистических данных согласно входным данным,
     * полученным в результате парсинга. Смотри метод {@link IParserAnalyzer#parser(Map)}
     * @param stringList Результаты проведенного парсинга с помощью метода {@link IParserAnalyzer#parser(Map)}
     * @return Полученные статистические данные в виде карты, отображающей URL-адрес страницы и карту отображения слова
     * на его количество вхождений.
     */
    Map<String,Map<String, Integer>> creatStatistics(Map<String, List<String>> stringList);
}
