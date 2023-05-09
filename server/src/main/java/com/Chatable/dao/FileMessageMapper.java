package com.Chatable.dao;

import com.Chatable.domain.FileMessage;
import com.Chatable.domain.FileMessageExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FileMessageMapper {
    long countByExample(FileMessageExample example);

    int deleteByExample(FileMessageExample example);

    int deleteByPrimaryKey(String id);

    int insert(FileMessage record);

    int insertSelective(FileMessage record);

    List<FileMessage> selectByExample(FileMessageExample example);

    FileMessage selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") FileMessage record, @Param("example") FileMessageExample example);

    int updateByExample(@Param("record") FileMessage record, @Param("example") FileMessageExample example);

    int updateByPrimaryKeySelective(FileMessage record);

    int updateByPrimaryKey(FileMessage record);
}