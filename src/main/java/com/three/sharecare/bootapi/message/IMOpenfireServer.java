package com.three.sharecare.bootapi.message;

import com.three.sharecare.bootapi.message.listeneradapter.ListenerAdapter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

public interface IMOpenfireServer {

    void loginImServer(String userName, String password, String serviceName) throws LoginServiceException;

    void sendText(Message message) throws Exception;

    void sendImage(String receiverJID, String imagePath, String description) throws Exception;

    void sendVoice(String receiverJID, String voicePath, String description) throws Exception;

    void addListener(ListenerAdapter listenerAdapter);

    XMPPTCPConnection getXmpptcpConnection();

}
