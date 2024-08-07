package com.baggujo.service;

import com.baggujo.dao.ChatDAO;
import com.baggujo.dao.MemberDAO;
import com.baggujo.dao.NotificationDAO;
import com.baggujo.dao.TradeDAO;
import com.baggujo.dto.*;
import com.baggujo.dto.enums.ChatType;
import com.baggujo.dto.enums.NotificationStatus;
import com.baggujo.dto.enums.TradeStatus;
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
import java.util.List;
import java.util.UUID;

@Service
public class ChatService {
    @Autowired
    ChatDAO chatDAO;
    @Autowired
    NotificationDAO notificationDAO;
    @Autowired
    TradeDAO tradeDAO;
    @Value("${com.baggujo.upload.path.chat}")
    private String uploadPath;

    // 채팅 생성
    @Transactional
    public void insertChat(ChatInsertDTO chatInsertDTO) throws SQLException {
        if (chatDAO.insertChat(chatInsertDTO) != 1) {
            throw new SQLException();
        }
        //기존의 채팅 알림 지움
        TradeInfoDTO tradeInfoDTO = tradeDAO.getTradeInfoByTradeId(chatInsertDTO.getTradeId());
        String myNickname = chatInsertDTO.getMemberId() == tradeInfoDTO.getRequestMemberId() ? tradeInfoDTO.getRequestNickname() : tradeInfoDTO.getResponseNickname();
        long otherMemberId = chatInsertDTO.getMemberId() == tradeInfoDTO.getRequestMemberId() ? tradeInfoDTO.getResponseMemberId() : tradeInfoDTO.getRequestMemberId();
        String myTitle = chatInsertDTO.getMemberId() == tradeInfoDTO.getRequestMemberId() ? tradeInfoDTO.getRequestTitle() : tradeInfoDTO.getResponseTitle();
        notificationDAO.updateNotificationByMemberIdAndTradeId(otherMemberId, chatInsertDTO.getTradeId(), NotificationStatus.DELETED);

        //새로운 채팅 알림을 생성
        String text = myTitle + "<br>" + myNickname + " : " + (chatInsertDTO.getChatType() == ChatType.TEXT ? chatInsertDTO.getText() : "(이미지)");
        notificationDAO.insertNotification(new NotificationInsertDTO(otherMemberId, text , "/trade/" + chatInsertDTO.getTradeId(), true, chatInsertDTO.getTradeId()));
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
