package com.simbirsoft.kondratyev.ruslan.parser.repository;

import com.simbirsoft.kondratyev.ruslan.parser.models.Statistics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * Репозиторий базы данных статистики, см. описание класса {@link Statistics}
 * @author Ruslan Kondratyev
 * @version 1.0
 */
@Repository
public interface StatisticRepository extends JpaRepository<Statistics, Long> {
    /**
     * Метод поиска статистики для страницы, содержащей параметр url
     * @param url запрашиваемый url-адрес, характеризующий страницу
     * @return возвращает список найденных статистик, либо null
     */
    List<Statistics> findByPage_Url(String url);

    /**
     * метод поиска статистических данных по определенной странице и слову
     * @param urlPage запрашиваемый url-адрес, характеризующий страницу
     * @param word запрашиваемое слово, характеризующее слово страницы
     * @return возвращает список статистик, либо null
     */
    List<Statistics> findByPage_UrlAndWord_Word(String urlPage, String word);
}
