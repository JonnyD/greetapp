import { element, by, promise, ElementFinder } from 'protractor';

export class GreetComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-greet div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class GreetUpdatePage {
    pageTitle = element(by.id('jhi-greet-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    titleInput = element(by.id('field_title'));
    descriptionInput = element(by.id('field_description'));
    longitudeInput = element(by.id('field_longitude'));
    latitudeInput = element(by.id('field_latitude'));
    privacyInput = element(by.id('field_privacy'));
    userSelect = element(by.id('field_user'));
    gangSelect = element(by.id('field_gang'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setTitleInput(title): promise.Promise<void> {
        return this.titleInput.sendKeys(title);
    }

    getTitleInput() {
        return this.titleInput.getAttribute('value');
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

    setPrivacyInput(privacy): promise.Promise<void> {
        return this.privacyInput.sendKeys(privacy);
    }

    getPrivacyInput() {
        return this.privacyInput.getAttribute('value');
    }

    userSelectLastOption(): promise.Promise<void> {
        return this.userSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    userSelectOption(option): promise.Promise<void> {
        return this.userSelect.sendKeys(option);
    }

    getUserSelect(): ElementFinder {
        return this.userSelect;
    }

    getUserSelectedOption() {
        return this.userSelect.element(by.css('option:checked')).getText();
    }

    gangSelectLastOption(): promise.Promise<void> {
        return this.gangSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    gangSelectOption(option): promise.Promise<void> {
        return this.gangSelect.sendKeys(option);
    }

    getGangSelect(): ElementFinder {
        return this.gangSelect;
    }

    getGangSelectedOption() {
        return this.gangSelect.element(by.css('option:checked')).getText();
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
