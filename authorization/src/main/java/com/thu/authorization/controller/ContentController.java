package com.thu.authorization.controller;

import com.thu.authorization.domain.ServiceStatus;
import com.thu.authorization.domain.request.ContentCreationRequest;
import com.thu.authorization.domain.request.ContentUpdateRequest;
import com.thu.authorization.domain.response.AllContentResponse;
import com.thu.authorization.domain.response.ContentResponse;
import com.thu.authorization.domain.response.MessageResponse;
import com.thu.authorization.entity.Content;
import com.thu.authorization.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("product")
public class ContentController {

    private ContentService contentService;

    @Autowired
    public void setContentService(ContentService contentService) {
        this.contentService = contentService;
    }

    @GetMapping("/test")
    public Object getAuthUserDetail(){
        System.out.println("heheehehe");
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("getAll")
    @PreAuthorize("hasAuthority('read')")
    public AllContentResponse getAllContent(){
        List<Content> contents = contentService.getAllContent();

        return AllContentResponse.builder()
                .serviceStatus(
                        ServiceStatus.builder()
                                .success(true)
                                .build()
                )
                .contents(contents)
                .build();
    }

    @GetMapping("get/{id}")
    @PreAuthorize("hasAuthority('read')")
    public ContentResponse getContentById(@PathVariable Integer id){
        Content content = contentService.getContentById(id);

        return ContentResponse.builder()
                .serviceStatus(
                        ServiceStatus.builder()
                                .success(true)
                                .build()
                )
                .content(content)
                .build();
    }

    @PostMapping("create")
    @PreAuthorize("hasAuthority('write')")
    public MessageResponse createContent(@RequestBody ContentCreationRequest request){
        contentService.createContent(request);

        return MessageResponse.builder()
                .serviceStatus(
                        ServiceStatus.builder()
                                .success(true)
                                .build()
                )
                .message("New content created")
                .build();
    }

    @PutMapping("update")
    @PreAuthorize("hasAuthority('update')")
    public MessageResponse updateContent(@RequestBody ContentUpdateRequest request){
        contentService.updateContent(request);

        return MessageResponse.builder()
                .serviceStatus(
                        ServiceStatus.builder()
                                .success(true)
                                .build()
                )
                .message("Content updated")
                .build();
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAuthority('delete')")
    public MessageResponse deleteContent(@PathVariable Integer id){
        contentService.deleteContent(id);

        return MessageResponse.builder()
                .serviceStatus(
                        ServiceStatus.builder()
                                .success(true)
                                .build()
                )
                .message("Content deleted")
                .build();
    }


}
