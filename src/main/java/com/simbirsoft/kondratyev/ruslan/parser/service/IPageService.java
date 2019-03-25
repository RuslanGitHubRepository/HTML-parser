package com.simbirsoft.kondratyev.ruslan.parser.service;

import com.simbirsoft.kondratyev.ruslan.parser.models.Page;
/**
 *Сервисный слой класс Page (смотри описание класса {@link Page}). Обеспечивает взаимодействие с базой данных,обеспечивая
 * загрузку {@link IPageService#uploadPage(Page)} текущей страницы в базу и проверку {@link IPageService#checkPage(String)}
 * на наличие текущей страницы.
 * @author Ruslan Kondratyev
 * @version 1.0
 */
public interface IPageService {
    /**
     * Метод {@literal uploadPage} Выполняет загрузку текущей страницы (см описание класcа {@link Page}) в базу данных.
     * @param page Страница, подлежащая загрузке в базу.
     */
    void uploadPage(Page page);

    /**
     * Метод {@literal checkPage} Выполняет проверку на наличие страницу с адресом url в базе данных
     * @param url URL-адресс запрашиваемой страницы
     * @return Возвращает запрашиваемую страницу. Вернет пустой класс Page с введенным в него url-адрессом
     */
    Page checkPage(String url);
}
