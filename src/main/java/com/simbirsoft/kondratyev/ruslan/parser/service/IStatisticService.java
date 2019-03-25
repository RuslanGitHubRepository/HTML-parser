package com.simbirsoft.kondratyev.ruslan.parser.service;

import com.simbirsoft.kondratyev.ruslan.parser.dto.StatisticsDTO;
import com.simbirsoft.kondratyev.ruslan.parser.models.Page;
import com.simbirsoft.kondratyev.ruslan.parser.models.Statistics;
import com.simbirsoft.kondratyev.ruslan.parser.models.Word;

import java.util.List;

/**
 *Сервисный слой класса {@literal Statistics (статистика, т.е. слово и его количество вхождений в страницу, смотри {@link Statistics})}.Обеспечивает функционал работы с базой данных {@literal mariaDB}
 * @author Ruslan Kondratyev
 * @version 1.0
 */
public interface IStatisticService {
    /**
     * Метод {@literal downloadStatistics} обеспечивает загрузку статистических данных из базы данных
     * @param urlPage URL-адрес страницы, подвергшейся анализу
     * @return список статистических данных для запрашиваемой страницы
     */
    List<StatisticsDTO> downloadStatistics(String urlPage);

    /**
     * Метод {@literal uploadStatistic} Обеспечивает загрузку статистических данных в базу данных {@literal mariaBD}
     * @param statistic список статистических данных, требующих загрузки в базу данных, смотри {@link Statistics}
     */
    void uploadStatistic(List<Statistics> statistic);

    /**
     * Метод {@literal checkStatistic} обеспечивает проверку наличия необходимой статистики конкретного слова в конктретной
     * странице в базе данных. смотри описание классов {@link Page} и {@link Word}
     * @param page Запрашиваемя страница
     * @param word Запрашиваемое слово
     * @return Список статистических данных, полученных в результате запроса в базе данных
     */
    List<Statistics> checkStatistic(Page page, Word word);
}