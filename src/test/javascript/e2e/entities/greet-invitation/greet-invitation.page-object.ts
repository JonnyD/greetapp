import { element, by, promise, ElementFinder } from 'protractor';

export class GreetInvitationComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-greet-invitation div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class GreetInvitationUpdatePage {
    pageTitle = element(by.id('jhi-greet-invitation-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    greetInvitationResponseInput = element(by.id('field_greetInvitationResponse'));
    greetSelect = element(by.id('field_greet'));
    userSelect = element(by.id('field_user'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setGreetInvitationResponseInput(greetInvitationResponse): promise.Promise<void> {
        return this.greetInvitationResponseInput.sendKeys(greetInvitationResponse);
    }

    getGreetInvitationResponseInput() {
        return this.greetInvitationResponseInput.getAttribute('value');
    }

    greetSelectLastOption(): promise.Promise<void> {
        return this.greetSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    greetSelectOption(option): promise.Promise<void> {
        return this.greetSelect.sendKeys(option);
    }

    getGreetSelect(): ElementFinder {
        return this.greetSelect;
    }

    getGreetSelectedOption() {
        return this.greetSelect.element(by.css('option:checked')).getText();
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
