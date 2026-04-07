package com.cyberguard.automation.questions;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class UserInTable implements Question<Boolean> {

    private final String username;

    public UserInTable(String username) {
        this.username = username;
    }

    public static UserInTable withUsername(String username) {
        return new UserInTable(username);
    }

    @Override
    @Step("{0} verifica si el usuario '#username' aparece en la tabla")
    public Boolean answeredBy(Actor actor) {
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();
        List<WebElement> rows = driver.findElements(By.cssSelector(".data-table tbody tr"));
        for (WebElement row : rows) {
            if (row.getText().contains(username)) {
                return true;
            }
        }
        return false;
    }
}
