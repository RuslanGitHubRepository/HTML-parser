package com.simbirsoft.kondratyev.ruslan.parser.service.impl;

import com.simbirsoft.kondratyev.ruslan.parser.models.Word;
import com.simbirsoft.kondratyev.ruslan.parser.repository.PageRepository;
import com.simbirsoft.kondratyev.ruslan.parser.repository.WordRepository;
import com.simbirsoft.kondratyev.ruslan.parser.service.IWordService;
import com.sun.jna.platform.win32.WinDef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class WordServiceImpl implements IWordService {
    @Autowired
    WordRepository wordRepository;
    @Override
    public void uploadWord(Word word) {
        wordRepository.saveAndFlush(word);
    }
    @Override
    public Word checkWord(String word) {
        Optional<Word> result = wordRepository.findByWord(word);
        if(result.isPresent()){
            return result.get();
        }
        else {
            Word emptyWord = new Word();
            emptyWord.setWord(word);
            return emptyWord;
        }
    }
}