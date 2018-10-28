export interface IGang {
    id?: number;
    name?: string;
    description?: string;
    longitude?: number;
    latitude?: number;
    membershipApproval?: string;
    privacy?: string;
}

export class Gang implements IGang {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public longitude?: number,
        public latitude?: number,
        public membershipApproval?: string,
        public privacy?: string
    ) {}
}
