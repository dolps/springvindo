import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISurfSpot } from 'app/shared/model/surf-spot.model';

type EntityResponseType = HttpResponse<ISurfSpot>;
type EntityArrayResponseType = HttpResponse<ISurfSpot[]>;

@Injectable({ providedIn: 'root' })
export class SurfSpotService {
    private resourceUrl = SERVER_API_URL + 'api/surf-spots';

    constructor(private http: HttpClient) {}

    create(surfSpot: ISurfSpot): Observable<EntityResponseType> {
        return this.http.post<ISurfSpot>(this.resourceUrl, surfSpot, { observe: 'response' });
    }

    update(surfSpot: ISurfSpot): Observable<EntityResponseType> {
        return this.http.put<ISurfSpot>(this.resourceUrl, surfSpot, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISurfSpot>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISurfSpot[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
