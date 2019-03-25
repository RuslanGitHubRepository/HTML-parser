package com.simbirsoft.kondratyev.ruslan.parser.service.impl;

import com.simbirsoft.kondratyev.ruslan.parser.service.ICreatAndSaveHTMLFile;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;



public class ManipulatorHTMLFiles implements ICreatAndSaveHTMLFile {
    private Map<String, File> files = new HashMap<>();

    @Override
    public void creatLocalHtmlFiles(List<String> urls) {
        Consumer<String> creatFiles = url->{
            String relativePath = getFileName(url);
            File file = creatSingleFile(relativePath);
            files.put(url, file);
        };
        urls.forEach(creatFiles);
    }

    @Override
    public void saveAndFillingCreatedFiles() {
        BiConsumer<String, File> saveFiles = (url, file)-> {
            try {
                if (file.createNewFile()) {
                    System.out.println(file.getCanonicalPath() + ", file successfully created");
                }
                URL urlPage = new URL(url);
                ReadableByteChannel readableByteChannel = Channels.newChannel(urlPage.openStream());
                FileOutputStream fileOutputStream = new FileOutputStream(file.getPath());
                FileChannel fileChannel = fileOutputStream.getChannel();
                fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            } catch (IOException exc) {
                throw new RuntimeException(exc.getMessage());
            }
        };
        files.forEach(saveFiles);
    }

    @Override
    public Map<String, File> getHtmlFiles() {
        return files;
    }

    @Override
    public void restartCreations() {
        files.clear();
    }

}
