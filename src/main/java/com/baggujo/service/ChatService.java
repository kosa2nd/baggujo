package com.baggujo.service;

import com.baggujo.dao.ChatDAO;
import com.baggujo.dto.ChatInsertDTO;
import com.baggujo.dto.ItemImageInsertDTO;
import com.baggujo.dto.UploadedChatImageDTO;
import com.baggujo.dto.enums.ChatType;
import net.coobird.thumbnailator.Thumbnailator;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.DoubleStream;

@Service
public class ChatService {
    @Autowired
    ChatDAO chatDAO;
    @Value("${com.baggujo.upload.path.chat}")
    private String uploadPath;

    // 채팅 생성
    @Transactional
    public void insertChat(ChatInsertDTO chatInsertDTO) throws SQLException {
        if (chatDAO.insertChat(chatInsertDTO) != 1) {
            throw new SQLException();
        }
    }

    //채팅
    private String makeFolder() throws FileNotFoundException {
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderPath = str.replace("/", File.separator);

        File uploadPathFolder = new File(uploadPath, folderPath);
        if (!uploadPathFolder.exists() && !uploadPathFolder.mkdirs()) {
            throw new FileNotFoundException("폴더 생성 실패");
        }

        return folderPath;
    }

    //채팅 이미지 업로드
    public UploadedChatImageDTO getUploadedChatImageNames(MultipartFile image) throws FileNotFoundException {

        LocalDate today = LocalDate.now();
        String path = makeFolder();

        if (!image.getContentType().startsWith("image") || image.isEmpty()) {
            throw new FileNotFoundException("잘못된 파일입니다");
        }

        String originalName = image.getOriginalFilename();
        System.out.println("!!!!!!!!!!!!!!!!+" + originalName);
        String folderPath = makeFolder();
        String uuid = UUID.randomUUID().toString();
        String saveName = folderPath +
                File.separator + uuid + "_" + originalName;
        Path savePath = Paths.get(uploadPath + File.separator + saveName);

        try {
            image.transferTo(savePath);
            String thumbnailSaveName = folderPath + File.separator + "s_" + uuid + "_" + originalName;
            File thumbnailFile = new File(uploadPath + File.separator + thumbnailSaveName);
            Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile, 300, 300);
            System.out.println("!!!!!!!!!!!!!!!!!!!" + thumbnailSaveName);
            UploadedChatImageDTO uploadedChatImageDTO = new UploadedChatImageDTO(saveName, thumbnailSaveName);

            return uploadedChatImageDTO;
        } catch (Exception e) {
            throw new FileNotFoundException("잘못된 파일입니다");
        }
    }

    public List<ChatInsertDTO> getChat(long tradeId) throws SQLException{
        return chatDAO.getChat(tradeId);
    }

}
