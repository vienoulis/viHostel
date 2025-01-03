package ru.vienoulis.vihostelbot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ConfigProvider {

    private final Environment environment;

    public String getSheetsRange() {
        var result = environment.getProperty("vienoulis.google.sheets.range");
        log.info("getSheetsRange: {}", result);
        return result;
    }

    public String getBotUsername() {
        var result = environment.getProperty("vienoulis.telegramm.botUsername");
        log.info("getBotUsername: {}", result);
        return result;
    }

    public String getToken() {
        var result = environment.getProperty("vienoulis.telegramm.token");
        log.info("getToken: {}", result);
        return result;
    }

    public String getSheetsTokensPath() {
        var result = environment.getProperty("vienoulis.google.sheets.tokens.path");
        log.info("getSheetsTokensPath: {}", result);
        return result;
    }

    public String getCredentialPath() {
        var result = environment.getProperty("vienoulis.google.sheets.credentials.path");
        log.info("getCredentialPath: {}", result);
        return result;
    }

    public String getSpreadsheetId() {
        var result = environment.getProperty("vienoulis.google.sheets.spreadsheetId");
        log.info("getSpreadsheetId: {}", result);
        return result;
    }

    public String getAppName() {
        var result = environment.getProperty("spring.application.name");
        log.info("getAppName: {}", result);
        return result;
    }
}
