package com.mint.service.impl;

import com.mint.common.ServerResponse;
import com.mint.dao.SectionMapper;
import com.mint.pojo.Section;
import com.mint.service.ISectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("iSectionService")
public class SectionServiceImpl implements ISectionService {

    @Autowired
    private SectionMapper sectionMapper;

    @Override
    public ServerResponse<String> getSnameBySid(String sid) {
        Section section = sectionMapper.selectByPrimaryKey(sid);
        return ServerResponse.createBySuccess(section.getSname());
    }
}
