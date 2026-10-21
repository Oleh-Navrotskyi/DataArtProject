package com.ai_project.dataart.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.awt.Desktop;
import java.net.URI;

@Configuration
@Slf4j
public class BrowserLauncher {

    @EventListener(ApplicationReadyEvent.class)
    public void launchBrowser(ApplicationReadyEvent event) {
        // Затримка в 1 секунду, щоб переконатися, що Tomcat вже приймає запити
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        String url = "http://localhost:8080/login.html"; // Або index.html, якщо налаштовано редирект

        // Перевіряємо, чи підтримується Desktop API на поточній ОС (Windows/Mac/Linux з UI)
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(url));
                log.info("Автоматично відкрито браузер за адресою: {}", url);
            } catch (Exception e) {
                log.warn("Не вдалося автоматично відкрити браузер: {}", e.getMessage());
            }
        } else {
            // Для серверних Linux-середовищ (де немає UI) можна спробувати виконати системну команду
            Runtime runtime = Runtime.getRuntime();
            try {
                String os = System.getProperty("os.name").toLowerCase();
                if (os.contains("mac")) {
                    runtime.exec("open " + url);
                    log.info("Відкрито браузер (macOS)");
                } else if (os.contains("nix") || os.contains("nux")) {
                    runtime.exec("xdg-open " + url);
                    log.info("Відкрито браузер (Linux)");
                } else {
                    log.warn("Desktop API не підтримується. Перейдіть вручну за посиланням: {}", url);
                }
            } catch (Exception e) {
                log.error("Помилка виконання системної команди для відкриття браузера: {}", e.getMessage());
            }
        }
    }
}