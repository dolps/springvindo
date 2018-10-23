import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VindoSharedModule } from 'app/shared';
import {
    SurfSpotComponent,
    SurfSpotDetailComponent,
    SurfSpotUpdateComponent,
    SurfSpotDeletePopupComponent,
    SurfSpotDeleteDialogComponent,
    surfSpotRoute,
    surfSpotPopupRoute
} from './';

const ENTITY_STATES = [...surfSpotRoute, ...surfSpotPopupRoute];

@NgModule({
    imports: [VindoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SurfSpotComponent,
        SurfSpotDetailComponent,
        SurfSpotUpdateComponent,
        SurfSpotDeleteDialogComponent,
        SurfSpotDeletePopupComponent
    ],
    entryComponents: [SurfSpotComponent, SurfSpotUpdateComponent, SurfSpotDeleteDialogComponent, SurfSpotDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VindoSurfSpotModule {}
