package uz.txtgroup.alertsbot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uz.txtgroup.alertsbot.dto.ErrorEventRequest;


@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramClient {

    private final TelegramBot bot;
    @Value("${telegram.chat-id}")
    private String chatId;

    public void sendErrorAlert(ErrorEventRequest request){
        StringBuilder sb = new StringBuilder();
        sb.append("🔴 <b>").append(escapeHtml(request.application())).append(" Xatoligi!</b>\n\n");
        if (request.url() != null && !request.url().isBlank()) {
            sb.append("🔗 <b>URL:</b> <code>").append(escapeHtml(request.url())).append("</code>\n");
        }
        sb.append("💬 <b>Xabar:</b> <code>").append(escapeHtml(request.message())).append("</code>\n");
        if (request.type() != null) {
            sb.append("📌 <b>Type:</b> <code>").append(escapeHtml(request.type())).append("</code>\n");
        }
        if (request.requestPayload() != null && !request.requestPayload().isBlank()) {
            sb.append("\n📥 <b>Request Payload:</b>\n");
            sb.append("<pre><code class=\"json\">").append(escapeHtml(request.requestPayload())).append("</code></pre>\n");
        }
        if (request.responsePayload() != null && !request.responsePayload().isBlank()) {
            sb.append("\n📤 <b>Response Payload:</b>\n");
            sb.append("<pre><code class=\"json\">").append(escapeHtml(request.responsePayload())).append("</code></pre>\n");
        }
        sb.append("\n⏰ <b>Vaqti:</b> ").append(java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        SendMessage req = new SendMessage(chatId, sb.toString())
                .parseMode(com.pengrad.telegrambot.model.request.ParseMode.HTML);

        SendResponse response = bot.execute(req);
        if (!response.isOk()) {
            log.error("Failed to send telegram alert: {}", response.description());
        }
    }

    private String escapeHtml(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }
}