package com.simbirsoft.kondratyev.ruslan.parser.repository;

import com.simbirsoft.kondratyev.ruslan.parser.models.Statistics;
import com.simbirsoft.kondratyev.ruslan.parser.models.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий базы данных слов, см. описание класса {@link Word}
 * @author Ruslan Kondratyev
 * @version 1.0
 */

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {
    /**
     * Возвращает слово, содержащее параметр word
     * @param word Слово, согласно которому происходит поиск
     * @return Найденное слово, либо null
     */
    Optional<Word> findByWord(String word);
}
