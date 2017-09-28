package com.model.base.websocket;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import com.model.base.common.SpringContextFactory;
import com.model.base.domain.User;
import com.model.base.service.IUserService;

@ServerEndpoint(value = "/websocket")
public class WebSocketServer {
	//静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
     
    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
//    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();
    private static Map<String, WebSocketServer> webSocketMap = new HashMap<String, WebSocketServer>();
     
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    
    @Autowired
    IUserService userService=(IUserService)SpringContextFactory.getBean("userService");
     
    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     * @throws InterruptedException 
     */
    @OnOpen
    public void onOpen(Session session){
        this.session = session;
//        webSocketSet.add(this);     //加入set中
        webSocketMap.put(session.getId(), this);//加入map中
        addOnlineCount();           //在线数加1
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
        //线程开启
        //Thread thread=new Thread(new ServerThread(session));
        //thread.start();
       
    }
     
    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session){
//        webSocketSet.remove(this);  //从set中删除
    	webSocketMap.remove(session.getId());//从map中删除
        subOnlineCount();           //在线数减1    
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }
     
    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);
         
        //群发消息
//        for(WebSocketServer item: webSocketSet){             
//            try {
//                item.sendMessage(message);
//            } catch (IOException e) {
//                e.printStackTrace();
//                continue;
//            }
//        }
        for(String in : webSocketMap.keySet()){
        	try {
        		WebSocketServer webSocketServer = webSocketMap.get(in);
        		if(webSocketServer != null){
        			webSocketServer.sendMessage(message);       			
        		}
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }
     
    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }
     
    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException{
        this.session.getBasicRemote().sendText(message);
    }
 
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }
 
    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }
     
    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
    
    //多线程
    private class ServerThread implements Runnable{
    	private Session session;
		public ServerThread(Session session) {
			super();
			this.session=session;
		}
		
		@Override
		public void run() {
//			synchronized (session) {
				while (true) {
					User user = userService.selectUserById(10);
				    WebSocketServer webSocketServer = webSocketMap.get(session.getId());
		        	if(webSocketServer==null){
		        		break;
		        	}
		        	try {
						webSocketServer.sendMessage("sessionid="+session.getId()+","+user.getUserName());
						Thread.sleep(5000);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
		        	
				}
//			}
		}
    	
    }
}
