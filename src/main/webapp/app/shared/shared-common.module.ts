import {NgModule} from '@angular/core';

import {
    VindoSharedLibsModule, FindLanguageFromKeyPipe,
    JhiAlertComponent, JhiAlertErrorComponent, TodayWindChartComponent
} from './';

@NgModule({
    imports: [VindoSharedLibsModule],
    declarations: [FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent],
    exports: [VindoSharedLibsModule, FindLanguageFromKeyPipe, JhiAlertComponent, JhiAlertErrorComponent]
})
export class VindoSharedCommonModule {
}
