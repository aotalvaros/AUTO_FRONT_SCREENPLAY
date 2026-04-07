package com.cyberguard.automation.questions;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class UserStatusBadge implements Question<String> {

    private final String username;

    public UserStatusBadge(String username) {
        this.username = username;
    }

    public static UserStatusBadge forUser(String username) {
        return new UserStatusBadge(username);
    }

    @Override
    @Step("{0} obtiene el estado del usuario '#username'")
    public String answeredBy(Actor actor) {
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();
        List<WebElement> rows = driver.findElements(By.cssSelector(".data-table tbody tr"));
        for (WebElement row : rows) {
            if (row.getText().contains(username)) {
                List<WebElement> activeBadges = row.findElements(By.cssSelector(".badge.badge-active"));
                if (!activeBadges.isEmpty()) {
                    return "ACTIVO";
                }
                List<WebElement> inactiveBadges = row.findElements(By.cssSelector(".badge.badge-inactive"));
                if (!inactiveBadges.isEmpty()) {
                    return "INACTIVO";
                }
            }
        }
        return "DESCONOCIDO";
    }
}
