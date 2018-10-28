import { IGang } from 'app/shared/model//gang.model';
import { IUser } from 'app/core/user/user.model';

export interface IGangUser {
    id?: number;
    role?: string;
    gang?: IGang;
    user?: IUser;
}

export class GangUser implements IGangUser {
    constructor(public id?: number, public role?: string, public gang?: IGang, public user?: IUser) {}
}
