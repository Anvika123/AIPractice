package com.questapp.tests;

import org.testng.annotations.Test;

import com.questapp.base.BaseTest;
import com.questapp.pages.CommunityPage;
import com.questapp.pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test
    public void testLoginAndCommunityFlow() throws InterruptedException {
        // Step 1: Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToLoginPage();
        loginPage.login("teacher1@gmail.com", "123456");

        // Step 2: Community actions
        CommunityPage communityPage = new CommunityPage(driver);
        communityPage.navigateToCommunityPage();

        communityPage.createCommunityPost("Automated test post");
        communityPage.editCommunityPost("Edited test post");
        communityPage.deleteCommunityPost();
    }
}
