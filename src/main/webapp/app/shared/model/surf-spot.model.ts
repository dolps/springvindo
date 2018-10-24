import { IMeasurement } from 'app/shared/model//measurement.model';

export interface ISurfSpot {
    id?: number;
    name?: string;
    latitude?: number;
    longitude?: number;
    stationId?: number;
    measurements?: IMeasurement[];
}

export class SurfSpot implements ISurfSpot {
    constructor(
        public id?: number,
        public name?: string,
        public latitude?: number,
        public longitude?: number,
        public stationId?: number,
        public measurements?: IMeasurement[]
    ) {}
}
