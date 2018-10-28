import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { RSVPComponentsPage, RSVPUpdatePage } from './rsvp.page-object';

describe('RSVP e2e test', () => {
    let navBarPage: NavBarPage;
    let rSVPUpdatePage: RSVPUpdatePage;
    let rSVPComponentsPage: RSVPComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load RSVPS', () => {
        navBarPage.goToEntity('rsvp');
        rSVPComponentsPage = new RSVPComponentsPage();
        expect(rSVPComponentsPage.getTitle()).toMatch(/greetappApp.rSVP.home.title/);
    });

    it('should load create RSVP page', () => {
        rSVPComponentsPage.clickOnCreateButton();
        rSVPUpdatePage = new RSVPUpdatePage();
        expect(rSVPUpdatePage.getPageTitle()).toMatch(/greetappApp.rSVP.home.createOrEditLabel/);
        rSVPUpdatePage.cancel();
    });

    it('should create and save RSVPS', () => {
        rSVPComponentsPage.clickOnCreateButton();
        rSVPUpdatePage.setRsvpResponseInput('rsvpResponse');
        expect(rSVPUpdatePage.getRsvpResponseInput()).toMatch('rsvpResponse');
        rSVPUpdatePage.greetSelectLastOption();
        rSVPUpdatePage.userSelectLastOption();
        rSVPUpdatePage.save();
        expect(rSVPUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
