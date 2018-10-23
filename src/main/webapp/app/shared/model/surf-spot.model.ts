export interface ISurfSpot {
    id?: number;
    name?: string;
}

export class SurfSpot implements ISurfSpot {
    constructor(public id?: number, public name?: string) {}
}
