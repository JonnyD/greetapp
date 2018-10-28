import { IUser } from 'app/core/user/user.model';

export interface IActivity {
    id?: number;
    type?: string;
    activityComponent?: string;
    objectId?: number;
    message?: string;
    user?: IUser;
}

export class Activity implements IActivity {
    constructor(
        public id?: number,
        public type?: string,
        public activityComponent?: string,
        public objectId?: number,
        public message?: string,
        public user?: IUser
    ) {}
}
