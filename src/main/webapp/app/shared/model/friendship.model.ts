import { IUser } from 'app/core/user/user.model';

export interface IFriendship {
    id?: number;
    status?: string;
    user?: IUser;
}

export class Friendship implements IFriendship {
    constructor(public id?: number, public status?: string, public user?: IUser) {}
}
