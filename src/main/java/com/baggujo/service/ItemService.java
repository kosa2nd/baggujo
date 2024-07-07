package com.baggujo.service;

import com.baggujo.dao.ItemDAO;
import com.baggujo.dao.ItemImageDAO;
import com.baggujo.dto.*;
import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.util.ThumbnailatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public long insertItem(ItemInsertDTO itemInsertDTO) throws SQLException, FileNotFoundException {
        itemDAO.insertItem(itemInsertDTO);
        long id = itemInsertDTO.getId();

        if (itemInsertDTO.getFiles() != null) {
            List<ItemImageInsertDTO> itemInsertDTOList = new ArrayList<>();
            LocalDate today = LocalDate.now();
            String path = makeFolder();

            MultipartFile[] multipartFiles = itemInsertDTO.getFiles();
            for (int i = 0; i < multipartFiles.length; i++) {
                if (!multipartFiles[i].getContentType().startsWith("image") || multipartFiles[i].isEmpty()) {
                    continue;
                }

                String originalName = multipartFiles[i].getOriginalFilename();
                System.out.println(originalName);
                String folderPath = makeFolder();
                String uuid = UUID.randomUUID().toString();
                String saveName = folderPath +
                        File.separator + uuid + "_" + originalName;
                Path savePath = Paths.get(uploadPath + File.separator + saveName);
                System.out.println("=========================" + savePath);

                try {
                    multipartFiles[i].transferTo(savePath);
                    String thumbnailSaveName = folderPath + File.separator+ "s_"+uuid + "_" + originalName;
                    File thumbnailFile = new File(uploadPath + File.separator + thumbnailSaveName);
                    Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile, 100, 100);
                    itemInsertDTOList.add(new ItemImageInsertDTO(saveName, thumbnailSaveName, i + 1, id));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            if (!itemInsertDTOList.isEmpty()) {
                itemImageDAO.insertItemImages(Map.of("list", itemInsertDTOList));
            }
        }

        return id;
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
    public List<ItemPreviewDTO> getItemPreviews(int lastItemId, int offset) throws SQLException {
        return itemDAO.getItemPreviews(lastItemId, offset);
    }
}
