package com.rjgonzalez.websocket.chat.controller;

import org.springframework.stereotype.Controller;

import com.rjgonzalez.websocket.chat.dto.WebSocketChatMessage;

@Controller
public class WebSocketChatController {

	@MessageMapping("/chat.sendMessage")
	@SendTo("/topic/javainuse")
	public WebSocketChatMessage sendMessage(@Payload WebSocketChatMessage webSocketChatMessage) {
		return webSocketChatMessage;
	}

	@MessageMapping("/chat.newUser")
	@SendTo("/topic/javainuse")
	public WebSocketChatMessage newUser(@Payload WebSocketChatMessage webSocketChatMessage,
			SimpMessageHeaderAccessor headerAccessor) {
		headerAccessor.getSessionAttributes().put("username", webSocketChatMessage.getSender());
		return webSocketChatMessage;
	}
}
