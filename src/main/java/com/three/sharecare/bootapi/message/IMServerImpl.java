package com.three.sharecare.bootapi.message;

import com.three.sharecare.bootapi.message.listeneradapter.ListenerAdapter;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.StanzaTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class IMServerImpl implements IMOpenfireServer {


    private XMPPConnectionManager4Android xmppConnectionManager4Android = new XMPPConnectionManager4Android();

    private ListenerAdapter listenerAdapter;

    public IMServerImpl() {

    }

    @Override
    public void loginImServer(String userName, String password, String serviceName) throws LoginServiceException {
        xmppConnectionManager4Android.login(userName, password, serviceName);
    }

    @Override
    public void sendText(Message message) throws Exception {
        xmppConnectionManager4Android.sendText(message);
    }

    @Override
    public void sendImage(String receiverJID, String imagePath, String description) throws Exception {
        xmppConnectionManager4Android.sendFile(receiverJID, imagePath, description);
    }

    @Override
    public void sendVoice(String receiverJID, String voicePath, String description) throws Exception {
        xmppConnectionManager4Android.sendFile(receiverJID, voicePath, description);
    }

    public void addListener(ListenerAdapter listenerAdapter) {
        this.listenerAdapter = listenerAdapter;
    }

    @Override
    public XMPPTCPConnection getXmpptcpConnection() {
        return xmppConnectionManager4Android.getXmpptcpConnection();
    }

    class XMPPConnectionManager4Android {

        @Value(value = "${openfire_ip}")
        private String ip;
        @Value(value = "${openfire_port}")
        private Integer port;

        private Logger logger = LoggerFactory.getLogger(XMPPConnectionManager4Android.class);

        /**
         * 连接openfire的connection
         */
        private XMPPTCPConnection xmpptcpConnection;

        /**
         * 文件传输manager
         */
        private FileTransferManager fileTransferManager;

        /**
         * Handles the sending of a file to another user.
         */
        private OutgoingFileTransfer transfer;

        /**
         * 判断是否登录
         */
        private boolean isLogined;


        /**
         * 初始化配置信息以及添加监听
         *
         * @param userName
         * @param password
         * @param serviceName
         */
        private void initConfiguration(String userName, String password, String serviceName) {
            XMPPTCPConnectionConfiguration.Builder builder = XMPPTCPConnectionConfiguration.builder();
            builder.setUsernameAndPassword(userName, password)
                    .setServiceName(serviceName)
                    .setHost(ip)
                    .setPort(port)
                    .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
            XMPPTCPConnectionConfiguration configuration = builder.build();
            xmpptcpConnection = new XMPPTCPConnection(configuration);
            fileTransferManager = FileTransferManager.getInstanceFor(xmpptcpConnection);
            xmpptcpConnection.addConnectionListener(listenerAdapter);
            xmpptcpConnection.addAsyncStanzaListener(listenerAdapter, new StanzaTypeFilter(Message.class));
            fileTransferManager.addFileTransferListener(listenerAdapter);
        }

        private void login(String userName, String password, String serviceName) throws LoginServiceException {
            try {
                initConfiguration(userName, password, serviceName);
                xmpptcpConnection.connect();
                xmpptcpConnection.login();
                isLogined = true;
            } catch (SmackException | IOException | XMPPException e) {
                logger.error("Login openfire failed! the reason is : {}", e.getMessage());
                throw new LoginServiceException(e);
            }
        }

        /**
         * 发送文件
         *
         * @param receiverJID     接收者JID
         * @param filePath        本地文件路径
         * @param fileDescription 文件描述
         * @throws Exception
         */
        private void sendFile(String receiverJID, String filePath, String fileDescription) throws Exception {
            validateLogin();

            // Create the outgoing file transfer
            OutgoingFileTransfer transfer = fileTransferManager.createOutgoingFileTransfer(receiverJID);
            // Send the file
            try {
                transfer.sendFile(new File(filePath), fileDescription);
            } catch (SmackException e) {
                logger.error("{} send file to {} failed!", xmpptcpConnection.getUser(), receiverJID);
                throw e;
            }

//			//监听发送的进度
//			while(!transfer.isDone()) {
//				if(transfer.getStatus().equals(Status.error)) {
//					logger.error("ERROR!!! {}", transfer.getError());
//					//错误处理......
//
//				} else {
//					//做其他处理
//					System.out.println(transfer.getStatus());
//					System.out.println(transfer.getProgress());
//				}
//			}
        }

        /**
         * 发送文本消息
         *
         * @param message
         */
        private void sendText(Message message) throws Exception {
            validateLogin();

            try {
                xmpptcpConnection.sendStanza(message);
            } catch (SmackException.NotConnectedException e) {
                logger.error("send text failed!", e);
                throw e;
            }
        }

        private void validateLogin() throws LoginServiceException {
            if (!isLogined || xmpptcpConnection == null || !xmpptcpConnection.isConnected() || !xmpptcpConnection.isAuthenticated()) {
                throw new LoginServiceException("No login in openfire!");
            }
        }

        public OutgoingFileTransfer getTransfer() {
            validateLogin();

            return transfer;
        }

        public XMPPTCPConnection getXmpptcpConnection() {
            return xmpptcpConnection;
        }
    }


}
