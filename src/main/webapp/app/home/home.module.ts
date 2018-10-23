import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {RouterModule} from '@angular/router';

import {VindoSharedModule} from 'app/shared';
import {HOME_ROUTE, HomeComponent} from './';
import {AgmCoreModule} from '@agm/core';
import {TodayWindChartComponent} from '../shared';

@NgModule({
    imports: [
        VindoSharedModule,
        RouterModule.forChild([HOME_ROUTE]),
        AgmCoreModule.forRoot({
            apiKey: 'AIzaSyCCkjCvmWKUF7RUPN22GFn40a18DjE469I'
        })
    ],
    declarations: [HomeComponent, TodayWindChartComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VindoHomeModule {
}
