package com.simbirsoft.kondratyev.ruslan.parser.repository;

import com.simbirsoft.kondratyev.ruslan.parser.models.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * Репозиторий базы данных страниц, см. описание класса {@link Page}
 * @author Ruslan Kondratyev
 * @version 1.0
 */
@Repository
public interface PageRepository extends JpaRepository<Page, Long> {
    /**
     * Метод возвращает страницу, содержащую параметр url
     * @param url URL-запрос поиска страницы в БД
     * @return найденная страница, либо null
     */
    Optional<Page> findByUrl(String url);
}