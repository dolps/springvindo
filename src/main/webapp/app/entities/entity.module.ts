import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { VindoSurfSpotModule } from './surf-spot/surf-spot.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        VindoSurfSpotModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VindoEntityModule {}
