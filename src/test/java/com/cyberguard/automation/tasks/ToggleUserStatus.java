package com.cyberguard.automation.tasks;

import com.cyberguard.automation.ui.UserManagementTargets;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;

public class ToggleUserStatus implements Task {

    private final String username;

    public ToggleUserStatus(String username) {
        this.username = username;
    }

    public static ToggleUserStatus forUser(String username) {
        return instrumented(ToggleUserStatus.class, username);
    }

    @Override
    @Step("{0} cambia el estado del usuario #username")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(UserManagementTargets.TABLE_CONTAINER, isVisible()).forNoMoreThan(10).seconds()
        );

        WebDriver driver = BrowseTheWeb.as(actor).getDriver();

        List<WebElement> rows = driver.findElements(By.cssSelector(".data-table tbody tr"));
        for (WebElement row : rows) {
            if (row.getText().contains(username)) {
                // Intentar primero el botón desactivar, luego activar
                List<WebElement> deactivateBtns = row.findElements(By.cssSelector(".btn-sm.btn-deactivate"));
                List<WebElement> activateBtns = row.findElements(By.cssSelector(".btn-sm.btn-activate"));

                if (!deactivateBtns.isEmpty()) {
                    deactivateBtns.get(0).click();
                } else if (!activateBtns.isEmpty()) {
                    activateBtns.get(0).click();
                }
                break;
            }
        }

        // Aceptar el diálogo de confirmación nativo del navegador si aparece
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (org.openqa.selenium.NoAlertPresentException ignored) {
            // No hay diálogo nativo — la app puede usar un diálogo personalizado
        }

        // Breve pausa para que Angular procese la respuesta y dispare loadUsers()
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
    }
}
