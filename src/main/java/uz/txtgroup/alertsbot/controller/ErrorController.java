package uz.txtgroup.alertsbot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.txtgroup.alertsbot.dto.ErrorEventRequest;
import uz.txtgroup.alertsbot.service.ErrorService;

@RestController
@RequestMapping("/api/errors")
@RequiredArgsConstructor
public class ErrorController {
    private final ErrorService errorService;
    @Value("${app.token}")
    private String validToken;

    @PostMapping
    public ResponseEntity<Void> receiveError(@RequestHeader(value = "X-Client-token", required = false) String token, @RequestBody ErrorEventRequest request) {
        if (validToken == null || !validToken.equals(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        errorService.processError(request);
        return ResponseEntity.ok().build();
    }
}
