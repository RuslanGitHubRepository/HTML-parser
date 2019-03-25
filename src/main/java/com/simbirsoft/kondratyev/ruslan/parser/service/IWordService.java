package com.simbirsoft.kondratyev.ruslan.parser.service;

import com.simbirsoft.kondratyev.ruslan.parser.models.Word;

/**
 *Сервисный слой класса {@literal Word (слово)}.Обеспечивает функционал работы с базой данных @{mariaDB}
 * @author Ruslan Kondratyev
 * @version 1.0
 */
public interface IWordService {
    /**
     * {@literal uploadWord} Метод загружает слова в базу данных
     * @param word текущее слово, требующее загрузки в БД
     */
    void uploadWord(Word word);

    /**
     * {@literal checkWord} Метод проверяет наличие конкретного слова в базе данных
     * @param word искомое слово, требующее проверки в базе данных
     * @return найденное слово. Вернет пустой класс Word с введенным в него искомым словом)
     */
    Word checkWord(String word);
}
