package uz.txtgroup.alertsbot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.txtgroup.alertsbot.dto.ErrorEventRequest;
import uz.txtgroup.alertsbot.service.ErrorService;

@RestController
@RequestMapping("/api/errors")
@RequiredArgsConstructor
public class ErrorController {
    private final ErrorService errorService;

    @PostMapping
    public ResponseEntity<Void> receiveError(@RequestBody ErrorEventRequest request) {
        errorService.processError(request);
        return ResponseEntity.ok().build();
    }
}
