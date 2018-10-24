import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMeasurement } from 'app/shared/model/measurement.model';

type EntityResponseType = HttpResponse<IMeasurement>;
type EntityArrayResponseType = HttpResponse<IMeasurement[]>;

@Injectable({ providedIn: 'root' })
export class MeasurementService {
    private resourceUrl = SERVER_API_URL + 'api/measurements';

    constructor(private http: HttpClient) {}

    create(measurement: IMeasurement): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(measurement);
        return this.http
            .post<IMeasurement>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(measurement: IMeasurement): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(measurement);
        return this.http
            .put<IMeasurement>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IMeasurement>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IMeasurement[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(measurement: IMeasurement): IMeasurement {
        const copy: IMeasurement = Object.assign({}, measurement, {
            time: measurement.time != null && measurement.time.isValid() ? measurement.time.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.time = res.body.time != null ? moment(res.body.time) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((measurement: IMeasurement) => {
            measurement.time = measurement.time != null ? moment(measurement.time) : null;
        });
        return res;
    }
}
