package com.mediamelon;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

class UserActions {

	void addAccount(WebDriver driver,List<String> formData){
		//Verify if the user is logged in as Super User to create a new account. 
		//If not, display message and return

		//Verify if the user is logged in
		String pageTitle = driver.getTitle();
		Assert.assertEquals(pageTitle,"MediaMelon - Home");
		String fullName = driver.findElement(By.xpath("//div[@class='row data']/div/div[1]/p[2]")).getText();
		String role = driver.findElement(By.xpath("//div[@class='row data']/div/div[3]/p[2]")).getText();

		if(fullName.equalsIgnoreCase("Super User") && role.equalsIgnoreCase("admin")){

			System.out.println("List of Accounts is displayed");
			//expand the settings menu option and click on Account Management
			List<WebElement> dropDownOptions = driver.findElements(By.className("dropdown-toggle"));
			dropDownOptions.get(0).click();
			WebElement accountManagement = driver.findElement(By.cssSelector(".dropdown-menu.dropdown-admin"));
			Assert.assertTrue(accountManagement.isDisplayed());
			accountManagement.findElement(By.linkText("Account Management")).click();

			Assert.assertEquals(driver.getTitle(),"List of Accounts");

			WebElement accountsTable = driver.findElement(By.xpath("//div[@class='row well']/table"));
			Assert.assertTrue(accountsTable.isDisplayed());
			WebElement addAccountBtn = driver.findElement(By.cssSelector(".btn.btn-primary"));

			System.out.println("Clicking on Add a new account button");
			Assert.assertEquals(addAccountBtn.getText(), "Add a new Account");
			addAccountBtn.click();

			//Fill account add form
			WebElement accountForm = driver.findElement(By.tagName("form"));
			Assert.assertTrue(accountForm.isDisplayed());
			System.out.println(accountForm.getAttribute("action"));

			accountForm.findElement(By.id("companyName")).sendKeys(formData.get(0));
			accountForm.findElement(By.id("companyWebsite")).sendKeys(formData.get(1));
			accountForm.findElement(By.id("adminName")).sendKeys(formData.get(2));
			accountForm.findElement(By.id("adminEmail")).sendKeys(formData.get(3));

			Select licenseType = new Select(driver.findElement(By.id("licenseType")));
			licenseType.selectByVisibleText(formData.get(4));

			Select services = new Select(driver.findElement(By.id("services")));
			String requiredServices = formData.get(5);
			String multipleSel[] = formData.get(5).split(",");

			if(requiredServices.equalsIgnoreCase("All")){
				services.selectByIndex(0);
				services.selectByIndex(1);
				services.selectByIndex(2);
			}
			else if(multipleSel.length>1){
				for(int i=0;i<multipleSel.length;i++)
					services.selectByVisibleText(multipleSel[i]);
			}
			else
				services.selectByVisibleText(requiredServices);

			accountForm.findElement(By.id("companyName")).sendKeys(formData.get(5));
			//submit the form
			System.out.println("Submitting the form");
			accountForm.findElement(By.cssSelector("button[value='submit']")).click();
			System.out.println("clicked on submit button");

			accountsTable = driver.findElement(By.cssSelector("table.table-striped.table-hover.table-bordered"));
			Assert.assertTrue(accountsTable.isDisplayed());
			System.out.println("form submitted sucessfully");

			//Verify the presence of newly created account in the list of accounts
			System.out.println("Verifying if the newly created account is present in the list");
			String tableData = accountsTable.getText();
			System.out.println("Table Data: "+tableData);
			//findElement(By.xpath(".//*[@class='studyResultsId']/div/table/tbody/tr/td")).getText();

			//Retrieving the data from the Td of table in to a string
			if(tableData.contains(formData.get(5))) {
				System.out.println("contains newly created account");
			} else {
				System.out.println("does not contains newly created account");
			}
		}
		else
			System.out.println("User not authorized to create an account");

	}

	void addUser(WebDriver driver){

	}

	void addAppClient(WebDriver driver){

	}

	void downloads(WebDriver driver,String username,String password){

	}



}
