package com.mediamelon;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

class Login {

	void launchLoginPage(WebDriver driver, String url) throws Exception{
		//launch mediamelon onboarding portal
		System.out.println("Launching on boarding URL");
		driver.get(url);
		WebElement e = driver.findElement(By.cssSelector(".login-panel.panel.panel-default"));
		Assert.assertTrue(e.isDisplayed());
		
	}
	
	WebDriver userLogin(WebDriver driver,String username,String password){
		
		//Login to the portal
		System.out.println("Entering login credentials");
		driver.findElement(By.id("email")).sendKeys(username);
		driver.findElement(By.id("password")).sendKeys(password,Keys.ENTER);
		String pageTitle = driver.getTitle();
		
		Assert.assertEquals(pageTitle,"MediaMelon - Home");
		
		//validate homepage
		//String welcomeMessage = driver.findElement(By.xpath("//div[@class='row']/h4")).getText();
		//Assert.assertEquals(welcomeMessage,"Welcome to MediaMelon control plane!");
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='']")).isDisplayed());
		System.out.println("Logged in successfully");
		return driver;
		
	}
	
	
	void closeBrowser(WebDriver driver){
		
		//close browser
		System.out.println("\nEnding program!!");
		driver.close();
	}
}
