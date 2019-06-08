package com.mint.service;

import com.mint.common.ServerResponse;

public interface ISectionService {
    ServerResponse<String> getSnameBySid(String sid);
}
