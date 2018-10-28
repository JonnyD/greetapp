import { element, by, promise, ElementFinder } from 'protractor';

export class GangUserComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-gang-user div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class GangUserUpdatePage {
    pageTitle = element(by.id('jhi-gang-user-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    roleInput = element(by.id('field_role'));
    gangSelect = element(by.id('field_gang'));
    userSelect = element(by.id('field_user'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setRoleInput(role): promise.Promise<void> {
        return this.roleInput.sendKeys(role);
    }

    getRoleInput() {
        return this.roleInput.getAttribute('value');
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
