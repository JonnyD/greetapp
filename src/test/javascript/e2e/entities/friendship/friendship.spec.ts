import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { FriendshipComponentsPage, FriendshipUpdatePage } from './friendship.page-object';

describe('Friendship e2e test', () => {
    let navBarPage: NavBarPage;
    let friendshipUpdatePage: FriendshipUpdatePage;
    let friendshipComponentsPage: FriendshipComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Friendships', () => {
        navBarPage.goToEntity('friendship');
        friendshipComponentsPage = new FriendshipComponentsPage();
        expect(friendshipComponentsPage.getTitle()).toMatch(/greetappApp.friendship.home.title/);
    });

    it('should load create Friendship page', () => {
        friendshipComponentsPage.clickOnCreateButton();
        friendshipUpdatePage = new FriendshipUpdatePage();
        expect(friendshipUpdatePage.getPageTitle()).toMatch(/greetappApp.friendship.home.createOrEditLabel/);
        friendshipUpdatePage.cancel();
    });

    it('should create and save Friendships', () => {
        friendshipComponentsPage.clickOnCreateButton();
        friendshipUpdatePage.setStatusInput('status');
        expect(friendshipUpdatePage.getStatusInput()).toMatch('status');
        friendshipUpdatePage.userSelectLastOption();
        friendshipUpdatePage.save();
        expect(friendshipUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
