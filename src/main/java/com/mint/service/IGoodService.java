package com.mint.service;

import com.mint.common.DataResponse;
import com.mint.common.ServerResponse;
import com.mint.pojo.Good;

import java.util.List;

public interface IGoodService {
    DataResponse<List<Good>> getGood(int page, int limit, Integer isused, String title, Integer issaled);

    ServerResponse deleteGood(String gid, String uid);
}
