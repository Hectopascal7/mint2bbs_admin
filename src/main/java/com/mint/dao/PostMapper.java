package com.mint.dao;

import com.mint.common.DataResponse;
import com.mint.pojo.Post;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PostMapper {
    int deleteByPrimaryKey(String tid);

    int insert(Post record);

    int insertSelective(Post record);

    Post selectByPrimaryKey(String tid);

    int updateByPrimaryKeySelective(Post record);

    int updateByPrimaryKeyWithBLOBs(Post record);

    int updateByPrimaryKey(Post record);

    List<Post> getAllPost(@Param("start") int start, @Param("limit") int limit);

    List<Post> getAllNoticePost(@Param("start") int start, @Param("limit") int limit);

    Integer getNoticePostCount();

    Integer getAllPostCount();

    Integer updatePostIsBest(@Param("tb_name") String tb_name, @Param("tid") String tid, @Param("isbest") int isbest);

    Integer updatePostIsSticky(@Param("tb_name") String tb_name, @Param("tid") String tid, @Param("issticky") int issticky);

    List<Post> getPostWithSection(@Param("start") int start, @Param("limit") int limit, @Param("tb_name") String tb_name, @Param("title") String title, @Param("isbest") Integer isbest, @Param("issticky") Integer issticky);

    Integer getPostCountWithSection(@Param("tb_name") String tb_name, @Param("title") String title, @Param("isbest") Integer isbest, @Param("issticky") Integer issticky);

    List<Post> getPostWithoutSection(@Param("start") int start, @Param("limit") int limit, @Param("title") String title, @Param("isbest") Integer isbest, @Param("issticky") Integer issticky);

    Integer getPostCountWithoutSection(@Param("title") String title, @Param("isbest") Integer isbest, @Param("issticky") Integer issticky);

    Integer deletePostByTbnameAndTid(@Param("tb_name") String tb_name, @Param("tid") String tid);
}