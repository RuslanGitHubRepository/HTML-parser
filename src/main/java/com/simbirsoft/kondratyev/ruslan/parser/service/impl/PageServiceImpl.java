package com.simbirsoft.kondratyev.ruslan.parser.service.impl;

import com.simbirsoft.kondratyev.ruslan.parser.models.Page;
import com.simbirsoft.kondratyev.ruslan.parser.repository.PageRepository;
import com.simbirsoft.kondratyev.ruslan.parser.service.IPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PageServiceImpl implements IPageService {
    @Autowired
    PageRepository pageRepository;
    @Override
    public void uploadPage(Page page) {
        pageRepository.saveAndFlush(page);
    }
    @Override
    public Page checkPage(String url) {
        Optional<Page> result = pageRepository.findByUrl(url);
        if(result.isPresent()){
            return result.get();
        }
        else{
            Page emptyPage = new Page();
            emptyPage.setUrl(url);
            return emptyPage;
        }
    }
}