package com.rjgonzalez.websocket.chat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.rjgonzalez.websocket.chat.dto.WebSocketChatMessage;

@Component
public class WebSocketChatEventListener {

	@Autowired
	private SimpMessageSendingOperations messagingTemplate;

	@EventListener
	public void handleWebSocketConnectListener(SessionConnectedEvent event) {
		System.out.println("Received a new web socket connection");
	}

	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		String username = (String) headerAccessor.getSessionAttributes().get("username");
		if (username != null) {
			WebSocketChatMessage chatMessage = new WebSocketChatMessage();
			chatMessage.setType("Leave");
			chatMessage.setSender(username);
			messagingTemplate.convertAndSend("/topic/public", chatMessage);
		}
	}
}
