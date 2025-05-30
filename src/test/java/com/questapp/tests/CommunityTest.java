package com.questapp.tests;

import com.questapp.base.BaseTest;
import com.questapp.pages.CommunityPage;
import com.questapp.pages.LoginPage;
import org.testng.annotations.Test;

public class CommunityTest extends BaseTest {

    @Test
    public void testLoginAndCommunityActions() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToLoginPage();
        loginPage.login("teacher1@gmail.com", "123456");

        CommunityPage community = new CommunityPage(driver);
        community.navigateToCommunityPage();
        community.createCommunityPost("Automated test post");
        community.editCommunityPost("Edited test post");
        community.deleteCommunityPost();
    }
}
