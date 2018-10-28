import { IGreet } from 'app/shared/model//greet.model';
import { IUser } from 'app/core/user/user.model';

export interface IGreetInvitation {
    id?: number;
    greetInvitationResponse?: string;
    greet?: IGreet;
    user?: IUser;
}

export class GreetInvitation implements IGreetInvitation {
    constructor(public id?: number, public greetInvitationResponse?: string, public greet?: IGreet, public user?: IUser) {}
}
