package com.simbirsoft.kondratyev.ruslan.parser.service;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Интерфейс создания и сохранения локального HTML-файла на жестком диске в соответствии в требуемым URL-адресом
 * страницы в сети интернет.
 * @author Ruslan Kondratyev
 * @version 1.0
 */
public interface ICreatAndSaveHTMLFile {
    /**
     * Метод по умолчанию. Формирует имя локально создаваемого файла. Имя файла определяеся запрашиваем URl-адресом страницы, в которой знак точка "." заменяются
     * знаками подчеркивания "_", а знаки "/" знаком доллара "$"
     * @param url URL-адрес страницы в сети интернет.
     * @return Возвращает сформированное имя будущего локального файла.
     * Может генерировать исключение {@link RuntimeException} в случае не корректно введенного URL-адреса.
     */
    default String getFileName(final String url){
        String cleanUrl = url.replaceFirst("http[s]?://","");
        Pattern pattern = Pattern.compile("[a-z0-9\\.\\/-]+");
        Matcher matcher = pattern.matcher(cleanUrl);
        if (!matcher.find()) {
            throw new RuntimeException("MalformedURL ("+ url + ") from ManipulatorHTMLFiles.creatFiles");
        }
        String filename = matcher.group().replace('.', '_').replace('/', '$') + ".html";
        /*--------------------------------------------------------*/
        // получаем разделитель пути в текущей операционной системе
        String fileSeparator = System.getProperty("file.separator");
        //создаем файл с указанием относительного пути к файлу
        return System.getProperty("user.dir") + fileSeparator + filename;
    };

    /**
     * Метод по умолчанию. Создает локальный файл с именем, формируемым методом {@link ICreatAndSaveHTMLFile#getHtmlFiles()}
     * @param relativePath Адрес файла, содержащего полный путь до места его нахождения.
     * @return Возвращает полученный файл. Файл был сохранен
     * Может генерировать ислючение {@link RuntimeException} в случае обнаружения файла по предлогаемому адресу
     * relativePath и неудачной попытке его удаления.
     */
    default File creatSingleFile(final String relativePath){
        File file = new File(relativePath);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println(relativePath + ", old version file successfully dropped");
            }
            else{
                throw new RuntimeException("relativePath " + " can't delete, something wrong");
            }
        }
        return file;
    }

    /**
     * Метод {@literal creatLocalHtmlFiles} создает список локальный файлов, определяемых списком URL-адресов.
     * @param fileUrls Список URL-адрсов страниц, определяющих имена создаваемых локальных файлов
     */
    void creatLocalHtmlFiles(final List<String> fileUrls);

    /**
     * Метод {@literal saveAndFillingCreatedFiles} Создает локальные файлы, имена которых определяются URl-адресами
     * запришиваемых интернет страниц. Поведение метода дополнительно может определяться функционалом методо
     * {@link ICreatAndSaveHTMLFile#getFileName(String)} и {@link ICreatAndSaveHTMLFile#creatSingleFile(String)}
     * Межет генерировать иключение {@link RuntimeException} в случае не корректного URL адресса.
     */
    void saveAndFillingCreatedFiles();

    /**
     * Метод {@link} Возвращает карту отображений URL-адресов страницы и соответствующих им локальных файлов
     * @return Отображение URL-адресов страницы и соответствующих им локальных файлов
     */
    Map<String, File> getHtmlFiles();

    /**
     * Метод сбрасывания и очистки состояний. Вызвается при запуске приложения.
     */
    void restartCreations();
}
