package com.cyberguard.automation.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScreenplayHooks {

    /**
     * order = 0 garantiza que este hook corre PRIMERO en todos los escenarios.
     */
    @Before(order = 0)
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
    }

    /**
     * Crea una amenaza cr\u00edtica v\u00eda POST /api/threats y almacena el threatId
     * en la memoria del actor bajo la clave "criticalThreatId".
     * order = 1 garantiza que corre DESPU\u00c9S de setTheStage (order = 0).
     */
    @Before(value = "@requiere-datos-previos", order = 1)
    public void crearAmenazaCritica() {
        try {
            String uniqueIp = "10." + (int)(Math.random() * 255) + "." + (int)(Math.random() * 255) + ".1";
            // Los valores de type y severity deben ser en minúscula según la validación de la API
            String body = "{"
                    + "\"type\":\"malware\","
                    + "\"severity\":\"critical\","
                    + "\"sourceIp\":\"" + uniqueIp + "\","
                    + "\"description\":\"Amenaza critica creada por hook de prueba\""
                    + "}";

            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:3000/api/threats"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .timeout(Duration.ofSeconds(15))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String threatId = extractThreatId(response.body());
            if (threatId == null || threatId.isBlank()) {
                threatId = UUID.randomUUID().toString();
            }
            OnStage.theActorCalled("admin").remember("criticalThreatId", threatId);

        } catch (Exception e) {
            System.err.println("[Hook @requiere-datos-previos] No se pudo crear amenaza cr\u00edtica: " + e.getMessage());
            // Almacena un UUID real para que el escenario ejecute y falle por 404, no por NPE
            OnStage.theActorCalled("admin").remember("criticalThreatId", UUID.randomUUID().toString());
        }
    }

    @After
    public void drawTheCurtain() {
        OnStage.drawTheCurtain();
    }

    private String extractThreatId(String json) {
        for (String key : new String[]{"\"id\"", "\"threatId\"", "\"threat_id\""}) {
            Pattern pattern = Pattern.compile(key + "\\s*:\\s*\"([^\"]+)\"");
            Matcher matcher = pattern.matcher(json);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return null;
    }
}
