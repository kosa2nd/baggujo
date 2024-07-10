package com.baggujo.dao;

import com.baggujo.dto.*;
import com.baggujo.dto.enums.ItemStatus;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface ItemDAO {
    long insertItem(ItemInsertDTO itemInsertDTO) throws SQLException;

    ItemDetailDTO getItemDetailById(long id);
    List<CategoryDTO> getCategories();
    List<ItemPreviewDTO> getItemPreviews(int lastItemId, int offset, ItemStatus itemStatus, int itemCategoryId, boolean exceptTraded, String keyword);
    int updateItemStatus(long itemId, ItemStatus itemStatus);
    ItemStatus getItemStatusById(long itemId);
    int updateItemStatusByRequestId(long requestId, ItemStatus itemStatus);
    List<ItemPreviewDTO> getFavoriteItemPreviews(long lastFavoriteId, long memberId, int offset, ItemStatus itemStatus);
}
