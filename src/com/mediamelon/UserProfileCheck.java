package com.mediamelon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


class UserProfileCheck{
	
	void verifyUserProfile(WebDriver driver)throws Exception{
		
		HashMap<String,String> userProfile = getProfileDetails(driver);
		verifyUserProfile(driver,userProfile);
		verifyMenuOptions(driver,userProfile.get("Role"));
		
	}
	
	
	HashMap<String,String> getProfileDetails(WebDriver driver) throws Exception{
		
		HashMap<String,String> userProfile = new HashMap<String,String>();

	      
		//print logged in user's profile
		System.out.println("Retrieving user details");
		String fullName = driver.findElement(By.xpath("//div[@class='row data']/div/div[1]/p[2]")).getText();
		String eMail = driver.findElement(By.xpath("//div[@class='row data']/div/div[2]/p[2]")).getText();
		String role = driver.findElement(By.xpath("//div[@class='row data']/div/div[3]/p[2]")).getText();
		String companyName = driver.findElement(By.xpath("//div[@class='row data']/div/div[4]/p[2]")).getText();
		String services = driver.findElement(By.xpath("//div[@class='row data']/div/div[5]/p[2]")).getText();

	    userProfile.put("Full Name", fullName);
	    userProfile.put("Email", eMail);
	    userProfile.put("Role", role);
	    userProfile.put("companyName", companyName);
	    userProfile.put("services",services);
	    
	    System.out.println("User Profile: "+userProfile);
	    System.out.println("User profile retrieved successfully");
	    return userProfile;
		
	}
	
	void verifyUserProfile(WebDriver driver,HashMap<String,String> profile){
		
		//Super Admin profile
		HashMap<String,String> superAdmin = new HashMap<String,String>();
		superAdmin.put("Full Name", "Super User");
		superAdmin.put("Email", "admin@mediamelon.com");
		superAdmin.put("Role", "admin");
		superAdmin.put("companyName", "MediaMelon Inc");
		superAdmin.put("services","master");
		
		
		String role = profile.get("Role");
		
		if (role.equalsIgnoreCase("admin") && profile.equals(superAdmin))
			System.out.println("Logged in User is Super User. Verifying menu options");
		else if (role.equalsIgnoreCase("admin") && !profile.containsValue(""))
			System.out.println("Logged in user is valid account admin");
		else if (!profile.containsValue(""))
			System.out.println("Logged in user is a valid normal user");
		else
			System.out.println("Logged in user is not authorized. Closing!!");
		
	}
	
	
	void verifyMenuOptions(WebDriver driver,String role){
		
		List<String> settings = new ArrayList<String>();
		List<String> user = new ArrayList<String>();
		
		//retrieve dropdown menu list
		System.out.println("retrieve dropdown menu list");
		List<WebElement> dropDownOptions = driver.findElements(By.className("dropdown-toggle"));
		
		//Click on the first menu to retrieve the settings menu
		dropDownOptions.get(0).click();
		Boolean settingsMenuDisplayed = driver.findElement(By.cssSelector(".dropdown-menu.dropdown-admin")).isDisplayed();
		//Display the Setting menu options
		if (settingsMenuDisplayed){
			System.out.println("\nSettings Menu Options:");
			settings = getMenuOptions(driver,".dropdown-menu.dropdown-admin");
			//System.out.println(settings);
		}
				
		//Click on the second menu to retrieve notification menu
		dropDownOptions.get(1).click();
		Boolean notificationMenuDisplayed = driver.findElement(By.cssSelector(".dropdown-menu.dropdown-alerts")).isDisplayed();
		if (notificationMenuDisplayed)
			System.out.println("\nNotification alerts are present");
		
		//Click on the third menu to retrieve user profile menu
		dropDownOptions.get(2).click();
		Boolean userProfileMenuDisplayed = driver.findElement(By.cssSelector(".dropdown-menu.dropdown-user.in")).isDisplayed();
		//display user profile menu options
		if (userProfileMenuDisplayed){
			System.out.println("\nUser Profile Menu Options:");
			user = getMenuOptions(driver,".dropdown-menu.dropdown-user.in");
			//System.out.println(user);
		}
		
		verifyValidMenuOptions(settings,user,role);
		
	}
	
	List<String> getMenuOptions(WebDriver driver,String locator){
		List<String> result= new ArrayList<String>();
		WebElement options = driver.findElement(By.cssSelector(locator));
		List<WebElement> links = options.findElements(By.tagName("a"));
		for(int j=0;j<links.size();j++){
			String data = links.get(j).getText();
			result.add(data);
			System.out.println(links.get(j).getText());
		}
		return result;
		
	}
	
	void verifyValidMenuOptions(List<String> settings, List<String> user, String role){
		List<String> superAdminSettings = new ArrayList<String>();
		superAdminSettings.add("Account Management");
		superAdminSettings.add("User Management");
		superAdminSettings.add("Manage API Clients");
		superAdminSettings.add("Downloads");
		
		List<String> userSettings = new ArrayList<String>();
		userSettings.add("User Profile");
		userSettings.add("Reset password");
		userSettings.add("Sign out");
		
		List<String> adminSettings = new ArrayList<String>();
		adminSettings.add("User Management");
		adminSettings.add("Manage API Clients");
		adminSettings.add("Downloads");
		
		List<String> noramlUserSettings = new ArrayList<String>();
		noramlUserSettings.add("Downloads");
		
		if(user.equals(userSettings))
			System.out.println("\nValid user menu options");
		else
			System.out.println("\nInvalid user Menu options");
		
		
		if(role.equalsIgnoreCase("super admin") && settings.containsAll(superAdminSettings))
				System.out.println("\nSettings Menu options are valid for the logged super admin");
		else if (role.equalsIgnoreCase("admin") && settings.containsAll(adminSettings))
			System.out.println("\nSettings Menu options are valid for the logged account admin");
		else if (role.equalsIgnoreCase("user") && settings.containsAll(noramlUserSettings))
			System.out.println("\nSettings Menu options are valid for the logged user");
		else
			System.out.println("\nSettings Menu options are NOT valid for the logged user");
	}
	
}