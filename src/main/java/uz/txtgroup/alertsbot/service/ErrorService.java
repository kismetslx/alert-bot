package uz.txtgroup.alertsbot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.txtgroup.alertsbot.dto.ErrorEventRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class ErrorService {

    private final TelegramClient telegramClient;

    public void processError(ErrorEventRequest request) {
        log.info("Received error event from application: {}", request.application());
        if (request == null || request.message() == null) {
            throw new IllegalArgumentException("Error event payload is invalid");
        }
        telegramClient.sendErrorAlert(request);
    }
}