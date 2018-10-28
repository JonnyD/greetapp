import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { GreetComponentsPage, GreetUpdatePage } from './greet.page-object';

describe('Greet e2e test', () => {
    let navBarPage: NavBarPage;
    let greetUpdatePage: GreetUpdatePage;
    let greetComponentsPage: GreetComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Greets', () => {
        navBarPage.goToEntity('greet');
        greetComponentsPage = new GreetComponentsPage();
        expect(greetComponentsPage.getTitle()).toMatch(/greetappApp.greet.home.title/);
    });

    it('should load create Greet page', () => {
        greetComponentsPage.clickOnCreateButton();
        greetUpdatePage = new GreetUpdatePage();
        expect(greetUpdatePage.getPageTitle()).toMatch(/greetappApp.greet.home.createOrEditLabel/);
        greetUpdatePage.cancel();
    });

    it('should create and save Greets', () => {
        greetComponentsPage.clickOnCreateButton();
        greetUpdatePage.setTitleInput('title');
        expect(greetUpdatePage.getTitleInput()).toMatch('title');
        greetUpdatePage.setDescriptionInput('description');
        expect(greetUpdatePage.getDescriptionInput()).toMatch('description');
        greetUpdatePage.setLongitudeInput('5');
        expect(greetUpdatePage.getLongitudeInput()).toMatch('5');
        greetUpdatePage.setLatitudeInput('5');
        expect(greetUpdatePage.getLatitudeInput()).toMatch('5');
        greetUpdatePage.setPrivacyInput('privacy');
        expect(greetUpdatePage.getPrivacyInput()).toMatch('privacy');
        greetUpdatePage.userSelectLastOption();
        greetUpdatePage.gangSelectLastOption();
        greetUpdatePage.save();
        expect(greetUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
