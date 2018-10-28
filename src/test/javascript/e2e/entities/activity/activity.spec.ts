import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { ActivityComponentsPage, ActivityUpdatePage } from './activity.page-object';

describe('Activity e2e test', () => {
    let navBarPage: NavBarPage;
    let activityUpdatePage: ActivityUpdatePage;
    let activityComponentsPage: ActivityComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Activities', () => {
        navBarPage.goToEntity('activity');
        activityComponentsPage = new ActivityComponentsPage();
        expect(activityComponentsPage.getTitle()).toMatch(/greetappApp.activity.home.title/);
    });

    it('should load create Activity page', () => {
        activityComponentsPage.clickOnCreateButton();
        activityUpdatePage = new ActivityUpdatePage();
        expect(activityUpdatePage.getPageTitle()).toMatch(/greetappApp.activity.home.createOrEditLabel/);
        activityUpdatePage.cancel();
    });

    it('should create and save Activities', () => {
        activityComponentsPage.clickOnCreateButton();
        activityUpdatePage.setTypeInput('type');
        expect(activityUpdatePage.getTypeInput()).toMatch('type');
        activityUpdatePage.setActivityComponentInput('activityComponent');
        expect(activityUpdatePage.getActivityComponentInput()).toMatch('activityComponent');
        activityUpdatePage.setObjectIdInput('5');
        expect(activityUpdatePage.getObjectIdInput()).toMatch('5');
        activityUpdatePage.setMessageInput('message');
        expect(activityUpdatePage.getMessageInput()).toMatch('message');
        activityUpdatePage.userSelectLastOption();
        activityUpdatePage.save();
        expect(activityUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
