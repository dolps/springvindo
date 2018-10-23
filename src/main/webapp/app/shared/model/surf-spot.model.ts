export interface ISurfSpot {
    id?: number;
    name?: string;
    latitude?: number;
    longitude?: number;
}

export class SurfSpot implements ISurfSpot {
    constructor(public id?: number, public name?: string, public latitude?: number, public longitude?: number) {}
}
