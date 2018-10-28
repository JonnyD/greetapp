import { element, by, promise, ElementFinder } from 'protractor';

export class ActivityComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-activity div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ActivityUpdatePage {
    pageTitle = element(by.id('jhi-activity-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    typeInput = element(by.id('field_type'));
    activityComponentInput = element(by.id('field_activityComponent'));
    objectIdInput = element(by.id('field_objectId'));
    messageInput = element(by.id('field_message'));
    userSelect = element(by.id('field_user'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setTypeInput(type): promise.Promise<void> {
        return this.typeInput.sendKeys(type);
    }

    getTypeInput() {
        return this.typeInput.getAttribute('value');
    }

    setActivityComponentInput(activityComponent): promise.Promise<void> {
        return this.activityComponentInput.sendKeys(activityComponent);
    }

    getActivityComponentInput() {
        return this.activityComponentInput.getAttribute('value');
    }

    setObjectIdInput(objectId): promise.Promise<void> {
        return this.objectIdInput.sendKeys(objectId);
    }

    getObjectIdInput() {
        return this.objectIdInput.getAttribute('value');
    }

    setMessageInput(message): promise.Promise<void> {
        return this.messageInput.sendKeys(message);
    }

    getMessageInput() {
        return this.messageInput.getAttribute('value');
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
