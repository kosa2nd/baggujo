package com.baggujo.service;

import com.baggujo.dao.ItemDAO;
import com.baggujo.dao.ItemImageDAO;
import com.baggujo.dto.*;
import com.baggujo.dto.enums.ItemStatus;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ItemService {

    @Autowired
    private ItemDAO itemDAO;
    @Autowired
    private ItemImageDAO itemImageDAO;
    @Value("${com.baggujo.upload.path}")
    private String uploadPath;

    @Transactional
    public long insertItem(ItemInsertDTO itemInsertDTO) throws SQLException, IOException {
        itemDAO.insertItem(itemInsertDTO);
        long id = itemInsertDTO.getId();
        processItemImages(itemInsertDTO.getFiles(), id);
        return id;
    }

    @Transactional
    public void updateItem(long id, ItemInsertDTO itemInsertDTO) throws SQLException, IOException {
        itemDAO.updateItem(id, itemInsertDTO);
        if (itemInsertDTO.getFiles() != null && itemInsertDTO.getFiles().length > 0) {
            boolean hasNonEmptyFile = Arrays.stream(itemInsertDTO.getFiles())
                    .anyMatch(file -> !file.isEmpty());
            if (hasNonEmptyFile) {
                itemImageDAO.deleteItemImages(id);
                processItemImages(itemInsertDTO.getFiles(), id);
            }
        }
    }

    @Transactional
    public void updateItemWithoutImages(long id, ItemInsertDTO itemInsertDTO) throws SQLException {
        itemDAO.updateItem(id, itemInsertDTO);
    }

    private void processItemImages(MultipartFile[] multipartFiles, long itemId) throws IOException, SQLException {
        if (multipartFiles != null && multipartFiles.length > 0) {
            List<ItemImageInsertDTO> itemInsertDTOList = new ArrayList<>();
            String folderPath = makeFolder();

            for (int i = 0; i < multipartFiles.length; i++) {
                if (!multipartFiles[i].getContentType().startsWith("image") || multipartFiles[i].isEmpty()) {
                    continue;
                }

                String originalName = multipartFiles[i].getOriginalFilename();
                String uuid = UUID.randomUUID().toString();
                String saveName = folderPath + File.separator + uuid + "_" + originalName;
                Path savePath = Paths.get(uploadPath + File.separator + saveName);

                multipartFiles[i].transferTo(savePath);
                String thumbnailSaveName = folderPath + File.separator + "s_" + uuid + "_" + originalName;
                File thumbnailFile = new File(uploadPath + File.separator + thumbnailSaveName);
                Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile, 300, 300);
                itemInsertDTOList.add(new ItemImageInsertDTO(saveName, thumbnailSaveName, i + 1, itemId));
            }

            if (!itemInsertDTOList.isEmpty()) {
                itemImageDAO.insertItemImages(Map.of("list", itemInsertDTOList));
            }
        }
    }

    private String makeFolder() throws FileNotFoundException {
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderPath = str.replace("/", File.separator);

        File uploadPathFolder = new File(uploadPath, folderPath);
        if (!uploadPathFolder.exists() && !uploadPathFolder.mkdirs()) {
            throw new FileNotFoundException("폴더 생성 실패");
        }

        return folderPath;
    }

    public ItemDetailDTO getItemDetailById(long id) throws SQLException {
        return itemDAO.getItemDetailById(id);
    }

    public List<CategoryDTO> getCategories() {
        return itemDAO.getCategories();
    }

    public List<ItemPreviewDTO> getItemPreviews(int lastItemId, int offset, ItemStatus itemStatus, int itemCategoryId, boolean noTraded, String keyword) throws SQLException {
        return itemDAO.getItemPreviews(lastItemId, offset, itemStatus, itemCategoryId, noTraded, keyword);
    }

    public List<FavoriteItemPreviewDTO> getFavoriteItemPreviews(long lastFavoriteNo, long memberId, int offset, ItemStatus itemStatus) throws SQLException {
        return itemDAO.getFavoriteItemPreviews(lastFavoriteNo, memberId, offset, itemStatus);
    }

    public List<ItemPreviewDTO> getMyItems(long memberId, long lastItemId, ItemStatus itemStatus, int offset) throws SQLException {
        return itemDAO.getMyItems(memberId, lastItemId, itemStatus, offset);
    }
}
