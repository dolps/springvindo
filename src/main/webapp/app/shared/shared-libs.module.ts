import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {NgJhipsterModule} from 'ng-jhipster';
import {InfiniteScrollModule} from 'ngx-infinite-scroll';
import {CookieModule} from 'ngx-cookie';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {HighchartsChartModule, } from 'highcharts-angular';

@NgModule({
    imports: [
        NgbModule.forRoot(),
        NgJhipsterModule.forRoot({
            // set below to true to make alerts look like toast
            alertAsToast: false,
            i18nEnabled: true,
            defaultI18nLang: 'en'
        }),
        InfiniteScrollModule,
        CookieModule.forRoot(),
        FontAwesomeModule,
        HighchartsChartModule
    ],
    exports: [
        FormsModule,
        CommonModule,
        NgbModule,
        NgJhipsterModule,
        InfiniteScrollModule,
        FontAwesomeModule,
        HighchartsChartModule]
})
export class VindoSharedLibsModule {
}
