import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VindoSharedModule } from 'app/shared';
import {
    MeasurementComponent,
    MeasurementDetailComponent,
    MeasurementUpdateComponent,
    MeasurementDeletePopupComponent,
    MeasurementDeleteDialogComponent,
    measurementRoute,
    measurementPopupRoute
} from './';

const ENTITY_STATES = [...measurementRoute, ...measurementPopupRoute];

@NgModule({
    imports: [VindoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MeasurementComponent,
        MeasurementDetailComponent,
        MeasurementUpdateComponent,
        MeasurementDeleteDialogComponent,
        MeasurementDeletePopupComponent
    ],
    entryComponents: [MeasurementComponent, MeasurementUpdateComponent, MeasurementDeleteDialogComponent, MeasurementDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VindoMeasurementModule {}
