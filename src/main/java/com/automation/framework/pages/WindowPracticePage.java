package com.automation.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WindowPracticePage {
    private WebDriver driver;

    public WindowPracticePage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickHomeButton() {
        driver.findElement(By.xpath("//button[text()='Open Home Page']")).click();
    }

    public void clickMultipleWindowsButton() {
        driver.findElement(By.xpath("//button[text()='Multiple windows']")).click();
    }
}