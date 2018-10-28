import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { GangUserComponentsPage, GangUserUpdatePage } from './gang-user.page-object';

describe('GangUser e2e test', () => {
    let navBarPage: NavBarPage;
    let gangUserUpdatePage: GangUserUpdatePage;
    let gangUserComponentsPage: GangUserComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load GangUsers', () => {
        navBarPage.goToEntity('gang-user');
        gangUserComponentsPage = new GangUserComponentsPage();
        expect(gangUserComponentsPage.getTitle()).toMatch(/greetappApp.gangUser.home.title/);
    });

    it('should load create GangUser page', () => {
        gangUserComponentsPage.clickOnCreateButton();
        gangUserUpdatePage = new GangUserUpdatePage();
        expect(gangUserUpdatePage.getPageTitle()).toMatch(/greetappApp.gangUser.home.createOrEditLabel/);
        gangUserUpdatePage.cancel();
    });

    it('should create and save GangUsers', () => {
        gangUserComponentsPage.clickOnCreateButton();
        gangUserUpdatePage.setRoleInput('role');
        expect(gangUserUpdatePage.getRoleInput()).toMatch('role');
        gangUserUpdatePage.gangSelectLastOption();
        gangUserUpdatePage.userSelectLastOption();
        gangUserUpdatePage.save();
        expect(gangUserUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
