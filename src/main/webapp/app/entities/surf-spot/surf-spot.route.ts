import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { SurfSpot } from 'app/shared/model/surf-spot.model';
import { SurfSpotService } from './surf-spot.service';
import { SurfSpotComponent } from './surf-spot.component';
import { SurfSpotDetailComponent } from './surf-spot-detail.component';
import { SurfSpotUpdateComponent } from './surf-spot-update.component';
import { SurfSpotDeletePopupComponent } from './surf-spot-delete-dialog.component';
import { ISurfSpot } from 'app/shared/model/surf-spot.model';

@Injectable({ providedIn: 'root' })
export class SurfSpotResolve implements Resolve<ISurfSpot> {
    constructor(private service: SurfSpotService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((surfSpot: HttpResponse<SurfSpot>) => surfSpot.body));
        }
        return of(new SurfSpot());
    }
}

export const surfSpotRoute: Routes = [
    {
        path: 'surf-spot',
        component: SurfSpotComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vindoApp.surfSpot.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'surf-spot/:id/view',
        component: SurfSpotDetailComponent,
        resolve: {
            surfSpot: SurfSpotResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vindoApp.surfSpot.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'surf-spot/new',
        component: SurfSpotUpdateComponent,
        resolve: {
            surfSpot: SurfSpotResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vindoApp.surfSpot.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'surf-spot/:id/edit',
        component: SurfSpotUpdateComponent,
        resolve: {
            surfSpot: SurfSpotResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vindoApp.surfSpot.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const surfSpotPopupRoute: Routes = [
    {
        path: 'surf-spot/:id/delete',
        component: SurfSpotDeletePopupComponent,
        resolve: {
            surfSpot: SurfSpotResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'vindoApp.surfSpot.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
