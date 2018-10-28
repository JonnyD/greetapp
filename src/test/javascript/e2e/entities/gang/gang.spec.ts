import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { GangComponentsPage, GangUpdatePage } from './gang.page-object';

describe('Gang e2e test', () => {
    let navBarPage: NavBarPage;
    let gangUpdatePage: GangUpdatePage;
    let gangComponentsPage: GangComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Gangs', () => {
        navBarPage.goToEntity('gang');
        gangComponentsPage = new GangComponentsPage();
        expect(gangComponentsPage.getTitle()).toMatch(/greetappApp.gang.home.title/);
    });

    it('should load create Gang page', () => {
        gangComponentsPage.clickOnCreateButton();
        gangUpdatePage = new GangUpdatePage();
        expect(gangUpdatePage.getPageTitle()).toMatch(/greetappApp.gang.home.createOrEditLabel/);
        gangUpdatePage.cancel();
    });

    it('should create and save Gangs', () => {
        gangComponentsPage.clickOnCreateButton();
        gangUpdatePage.setNameInput('name');
        expect(gangUpdatePage.getNameInput()).toMatch('name');
        gangUpdatePage.setDescriptionInput('description');
        expect(gangUpdatePage.getDescriptionInput()).toMatch('description');
        gangUpdatePage.setLongitudeInput('5');
        expect(gangUpdatePage.getLongitudeInput()).toMatch('5');
        gangUpdatePage.setLatitudeInput('5');
        expect(gangUpdatePage.getLatitudeInput()).toMatch('5');
        gangUpdatePage.setMembershipApprovalInput('membershipApproval');
        expect(gangUpdatePage.getMembershipApprovalInput()).toMatch('membershipApproval');
        gangUpdatePage.setPrivacyInput('privacy');
        expect(gangUpdatePage.getPrivacyInput()).toMatch('privacy');
        gangUpdatePage.save();
        expect(gangUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
