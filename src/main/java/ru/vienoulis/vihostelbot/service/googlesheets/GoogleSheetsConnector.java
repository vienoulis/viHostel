package ru.vienoulis.vihostelbot.service.googlesheets;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GoogleSheetsConnector {


    @Value("${spring.application.name}")
    private String applicationName;
    @Value("${vienoulis.google.sheets.credentials.path}")
    private String credentialsFilePath;
    @Value("${vienoulis.google.sheets.tokens.path}")
    private String tokensPath;

    private final NetHttpTransport netHttpTransport;
    private final JsonFactory jsonFactory;
    private final List<String> googleDataSheetScopes;
    private Sheets service;

    public Sheets getService() {
        if (Objects.isNull(service)) {
            if (!connect()) {
                throw new RuntimeException("connection false");
            }
        }
        return service;
    }

    public boolean connect() {
        log.info("connect.enter;");
        try {
            service = new Sheets.Builder(netHttpTransport, jsonFactory, createCredential())
                    .setApplicationName(applicationName)
                    .build();
            log.info("connect.exit; connected;");
            return true;
        } catch (IOException e) {
            log.warn("connect.warn; cause by; ", e);
            return false;
        }
    }

    private Credential createCredential() throws IOException {
        InputStream in = new FileInputStream(credentialsFilePath);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                netHttpTransport, jsonFactory, clientSecrets, googleDataSheetScopes)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(tokensPath)))
                .setAccessType("offline")
                .build();
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }
}
