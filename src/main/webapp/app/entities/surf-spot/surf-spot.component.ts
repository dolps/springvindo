import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISurfSpot } from 'app/shared/model/surf-spot.model';
import { Principal } from 'app/core';
import { SurfSpotService } from './surf-spot.service';

@Component({
    selector: 'jhi-surf-spot',
    templateUrl: './surf-spot.component.html'
})
export class SurfSpotComponent implements OnInit, OnDestroy {
    surfSpots: ISurfSpot[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private surfSpotService: SurfSpotService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.surfSpotService.query().subscribe(
            (res: HttpResponse<ISurfSpot[]>) => {
                this.surfSpots = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSurfSpots();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISurfSpot) {
        return item.id;
    }

    registerChangeInSurfSpots() {
        this.eventSubscriber = this.eventManager.subscribe('surfSpotListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
