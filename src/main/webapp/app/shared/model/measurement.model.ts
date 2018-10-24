import { Moment } from 'moment';

export interface IMeasurement {
    id?: number;
    stationId?: number;
    windAvg?: number;
    windStDev?: number;
    windMax?: number;
    windMin?: number;
    directionAverage?: number;
    time?: Moment;
}

export class Measurement implements IMeasurement {
    constructor(
        public id?: number,
        public stationId?: number,
        public windAvg?: number,
        public windStDev?: number,
        public windMax?: number,
        public windMin?: number,
        public directionAverage?: number,
        public time?: Moment
    ) {}
}
