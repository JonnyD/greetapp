import { IGreet } from 'app/shared/model//greet.model';
import { IUser } from 'app/core/user/user.model';

export interface IRSVP {
    id?: number;
    rsvpResponse?: string;
    greet?: IGreet;
    user?: IUser;
}

export class RSVP implements IRSVP {
    constructor(public id?: number, public rsvpResponse?: string, public greet?: IGreet, public user?: IUser) {}
}
