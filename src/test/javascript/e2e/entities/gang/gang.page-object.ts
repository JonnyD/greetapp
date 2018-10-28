import { element, by, promise, ElementFinder } from 'protractor';

export class GangComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-gang div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class GangUpdatePage {
    pageTitle = element(by.id('jhi-gang-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nameInput = element(by.id('field_name'));
    descriptionInput = element(by.id('field_description'));
    longitudeInput = element(by.id('field_longitude'));
    latitudeInput = element(by.id('field_latitude'));
    membershipApprovalInput = element(by.id('field_membershipApproval'));
    privacyInput = element(by.id('field_privacy'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setNameInput(name): promise.Promise<void> {
        return this.nameInput.sendKeys(name);
    }

    getNameInput() {
        return this.nameInput.getAttribute('value');
    }

    setDescriptionInput(description): promise.Promise<void> {
        return this.descriptionInput.sendKeys(description);
    }

    getDescriptionInput() {
        return this.descriptionInput.getAttribute('value');
    }

    setLongitudeInput(longitude): promise.Promise<void> {
        return this.longitudeInput.sendKeys(longitude);
    }

    getLongitudeInput() {
        return this.longitudeInput.getAttribute('value');
    }

    setLatitudeInput(latitude): promise.Promise<void> {
        return this.latitudeInput.sendKeys(latitude);
    }

    getLatitudeInput() {
        return this.latitudeInput.getAttribute('value');
    }

    setMembershipApprovalInput(membershipApproval): promise.Promise<void> {
        return this.membershipApprovalInput.sendKeys(membershipApproval);
    }

    getMembershipApprovalInput() {
        return this.membershipApprovalInput.getAttribute('value');
    }

    setPrivacyInput(privacy): promise.Promise<void> {
        return this.privacyInput.sendKeys(privacy);
    }

    getPrivacyInput() {
        return this.privacyInput.getAttribute('value');
    }

    save(): promise.Promise<void> {
        return this.saveButton.click();
    }

    cancel(): promise.Promise<void> {
        return this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}
