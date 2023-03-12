package com.thu.authorization.service;

import com.beaconfire.springsecuritycontent.dao.ContentDao;
import com.beaconfire.springsecuritycontent.domain.request.ContentCreationRequest;
import com.beaconfire.springsecuritycontent.domain.request.ContentUpdateRequest;
import com.beaconfire.springsecuritycontent.entity.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentService {

    private ContentDao contentDao;

    @Autowired
    public void setContentDao(ContentDao contentDao) {
        this.contentDao = contentDao;
    }

    public List<Content> getAllContent(){
        return contentDao.getAll();
    }

    public Content getContentById(Integer id){
        return contentDao.findById(id).get();
    }

    public void createContent(ContentCreationRequest request){
        contentDao.addNewContent(request.getId(), request.getContent());
    }

    public void updateContent(ContentUpdateRequest request){
        contentDao.updateContent(request.getId(), request.getContent());
    }

    public void deleteContent(Integer id){
        contentDao.delete(id);
    }
}
