package com.lyh.linkhub.mapper;

import com.lyh.linkhub.pojo.LinkRecord;
import com.lyh.linkhub.pojo.LinkRecordPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LinkRecordMapper {
    int insertLinkRecord(LinkRecord record);

    int updateLinkRecord(LinkRecord record);

    int deleteLinkRecord(@Param("id") Long id);

    int deleteLinkRecordTags(@Param("linkRecordId") Long linkRecordId);

    int insertLinkRecordTag(@Param("linkRecordId") Long linkRecordId, @Param("tagId") Long tagId);

    List<LinkRecord> selectLinkRecordPage(@Param("offset") Integer offset,
                                          @Param("pageSize") Integer pageSize,
                                          @Param("filterTag") String filterTag,
                                          @Param("sortDirection") String sortDirection);

    long countLinkRecord(@Param("filterTag") String filterTag);

    LinkRecord selectLinkRecordByUrl(@Param("url") String url);

    LinkRecord selectLinkRecordById(@Param("id") Long id);
}
