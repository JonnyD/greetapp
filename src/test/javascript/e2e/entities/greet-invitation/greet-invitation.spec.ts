import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { GreetInvitationComponentsPage, GreetInvitationUpdatePage } from './greet-invitation.page-object';

describe('GreetInvitation e2e test', () => {
    let navBarPage: NavBarPage;
    let greetInvitationUpdatePage: GreetInvitationUpdatePage;
    let greetInvitationComponentsPage: GreetInvitationComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load GreetInvitations', () => {
        navBarPage.goToEntity('greet-invitation');
        greetInvitationComponentsPage = new GreetInvitationComponentsPage();
        expect(greetInvitationComponentsPage.getTitle()).toMatch(/greetappApp.greetInvitation.home.title/);
    });

    it('should load create GreetInvitation page', () => {
        greetInvitationComponentsPage.clickOnCreateButton();
        greetInvitationUpdatePage = new GreetInvitationUpdatePage();
        expect(greetInvitationUpdatePage.getPageTitle()).toMatch(/greetappApp.greetInvitation.home.createOrEditLabel/);
        greetInvitationUpdatePage.cancel();
    });

    it('should create and save GreetInvitations', () => {
        greetInvitationComponentsPage.clickOnCreateButton();
        greetInvitationUpdatePage.setGreetInvitationResponseInput('greetInvitationResponse');
        expect(greetInvitationUpdatePage.getGreetInvitationResponseInput()).toMatch('greetInvitationResponse');
        greetInvitationUpdatePage.greetSelectLastOption();
        greetInvitationUpdatePage.userSelectLastOption();
        greetInvitationUpdatePage.save();
        expect(greetInvitationUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
