package uz.txtgroup.alertsbot.dto;

public record ErrorEventRequest(
        String application,
        String url,
        String type,
        String message,
        String requestPayload,
        String responsePayload
) {}
