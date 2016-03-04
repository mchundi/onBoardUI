package com.mediamelon;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class LaunchUI {

	public static void main(String args[]) throws Exception{
		
		
		WebDriver driver = new FirefoxDriver();
		Login l = new Login();
		UserProfileCheck upc = new UserProfileCheck();
		UserActions ua = new UserActions();
		List<String> accountData = new ArrayList<String>();
		accountData.add("SeleniumTest");
		accountData.add("seleniumtest.org");
		accountData.add("SeleniumTestAdmin");
		accountData.add("admin@selenium.org");
		accountData.add("Demo");
		accountData.add("All");
		
		
		try{
			String url = "http://185.94.236.55:9000/signin";
			String uname = "admin@mediamelon.com";
			String password = "password";
			
			
			l.launchLoginPage(driver,url);
			driver = l.userLogin(driver, uname, password);
			upc.verifyUserProfile(driver);
			
			ua.addAccount(driver,accountData);
			
			l.closeBrowser(driver);
			
		}catch (Exception e){
			e.printStackTrace();
			l.closeBrowser(driver);
		}
		
		
	}
}
