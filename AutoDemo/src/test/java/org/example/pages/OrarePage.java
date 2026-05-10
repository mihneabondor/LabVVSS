package org.example.pages;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.pages.ListOfWebElementFacades;
import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

@DefaultUrl("https://app.busify.ro/orare")
public class OrarePage extends PageObject {
    private By searchInput = By.cssSelector("input[placeholder='Caută o linie']");
    private By routeList = By.cssSelector(".orare-body-container");
    private By routeCells = By.cssSelector(".orare-cell");
    private By activeFilterButton = By.cssSelector(".orare-filter-button.active-selection");

    public void openOrarePage() {
        open();
    }

    public boolean isPageLoaded() {
        return $(routeList).isDisplayed();
    }

    public void clickFilterButton(String filterName) {
        // finds the button by its visible text, e.g. "Autobuze", "Troleibuze", "Tramvaie"
        By filter = By.xpath(
                "//div[contains(@class,'orare-filter-button') " +
                        "and contains(translate(text(),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'" +
                        filterName.toUpperCase() + "')]"
        );
        $(filter).click();
    }

    public String getActiveFilterText() {
        return $(activeFilterButton).getText().trim();
    }

    public ListOfWebElementFacades getVisibleRoutes() {
        return findAll(routeCells);
    }

    public boolean allVisibleRoutesAreOfType(String type) {
        ListOfWebElementFacades cells = getVisibleRoutes();
        if (cells.isEmpty()) return false;
        return cells.stream().allMatch(cell ->
                !cell.findElements(
                        By.cssSelector(".orare-cell-badge." + type.toLowerCase())
                ).isEmpty()
        );
    }

    public void clickRouteCell(String routeName) {
        By cell = By.xpath(
                "//div[contains(@class,'orare-cell')]" +
                        "//div[contains(@class,'orare-cell-badge')]" +
                        "//div[text()='" + routeName + "' or b[text()='" + routeName + "']]"
        );
        $(cell).click();
    }

    public void typeInSearchField(String routeName) {
        $(searchInput).clear();
        $(searchInput).type(routeName);
        try {
            Thread.sleep(500); // wait for debounce
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void searchForRoute(String routeName) {
        $(searchInput).clear();
        $(searchInput).type(routeName);

        // wait for the 150ms debounce to fire before submitting
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        $(searchInput).sendKeys(org.openqa.selenium.Keys.ENTER);
    }

    public boolean isOnRouteDetailPage(String routeName) {
        return getDriver().getCurrentUrl().contains("/orare/" + routeName);
    }

    public String getAlertText() {
        return getDriver().switchTo().alert().getText();
    }

    public void dismissAlert() {
        getDriver().switchTo().alert().accept();
    }
}