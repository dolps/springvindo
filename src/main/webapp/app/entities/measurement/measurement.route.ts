import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Measurement } from 'app/shared/model/measurement.model';
import { MeasurementService } from './measurement.service';
import { MeasurementComponent } from './measurement.component';
import { MeasurementDetailComponent } from './measurement-detail.component';
import { MeasurementUpdateComponent } from './measurement-update.component';
import { MeasurementDeletePopupComponent } from './measurement-delete-dialog.component';
import { IMeasurement } from 'app/shared/model/measurement.model';

@Injectable({ providedIn: 'root' })
export class MeasurementResolve implements Resolve<IMeasurement> {
    constructor(private service: MeasurementService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((measurement: HttpResponse<Measurement>) => measurement.body));
        }
        return of(new Measurement());
    }
}

export const measurementRoute: Routes = [
    {
        path: 'measurement',
        component: MeasurementComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'vindoApp.measurement.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'measurement/:id/view',
        component: MeasurementDetailComponent,
        resolve: {
            measurement: MeasurementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vindoApp.measurement.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'measurement/new',
        component: MeasurementUpdateComponent,
        resolve: {
            measurement: MeasurementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vindoApp.measurement.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'measurement/:id/edit',
        component: MeasurementUpdateComponent,
        resolve: {
            measurement: MeasurementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vindoApp.measurement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const measurementPopupRoute: Routes = [
    {
        path: 'measurement/:id/delete',
        component: MeasurementDeletePopupComponent,
        resolve: {
            measurement: MeasurementResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vindoApp.measurement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
