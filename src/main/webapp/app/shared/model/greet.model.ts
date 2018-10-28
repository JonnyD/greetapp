import { IUser } from 'app/core/user/user.model';
import { IGang } from 'app/shared/model//gang.model';

export interface IGreet {
    id?: number;
    title?: string;
    description?: string;
    longitude?: number;
    latitude?: number;
    privacy?: string;
    user?: IUser;
    gang?: IGang;
}

export class Greet implements IGreet {
    constructor(
        public id?: number,
        public title?: string,
        public description?: string,
        public longitude?: number,
        public latitude?: number,
        public privacy?: string,
        public user?: IUser,
        public gang?: IGang
    ) {}
}
