package com.baggujo.dao;

import com.baggujo.dto.*;
import com.baggujo.dto.enums.ItemStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Mapper
public interface ItemDAO {
    long insertItem(ItemInsertDTO itemInsertDTO) throws SQLException;

    ItemDetailDTO getItemDetailById(long id);
    List<CategoryDTO> getCategories();
    List<ItemPreviewDTO> getItemPreviews(int lastItemId, int offset, ItemStatus itemStatus, int itemCategoryId, boolean exceptTraded, String keyword);
    int updateItemStatus(long itemId, ItemStatus itemStatus);
    ItemStatus getItemStatusById(long itemId);
    int updateItemStatusByRequestId(long requestId, ItemStatus itemStatus);
    List<FavoriteItemPreviewDTO> getFavoriteItemPreviews(long lastFavoriteNo, long memberId, int offset, ItemStatus itemStatus) throws SQLException;
    List<ItemPreviewDTO> getMyItems(long memberId, long lastItemId, ItemStatus itemStatus, int offset) throws SQLException;
    int deleteItem(long memberId, long itemId) throws SQLException;
    int updateItem(@Param("id") long id, @Param("item") ItemInsertDTO itemInsertDTO) throws SQLException;
    ItemNotiDTO getItemNoti(long itemId);
}
