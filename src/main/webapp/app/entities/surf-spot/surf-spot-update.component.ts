import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ISurfSpot } from 'app/shared/model/surf-spot.model';
import { SurfSpotService } from './surf-spot.service';

@Component({
    selector: 'jhi-surf-spot-update',
    templateUrl: './surf-spot-update.component.html'
})
export class SurfSpotUpdateComponent implements OnInit {
    private _surfSpot: ISurfSpot;
    isSaving: boolean;

    constructor(private surfSpotService: SurfSpotService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ surfSpot }) => {
            this.surfSpot = surfSpot;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.surfSpot.id !== undefined) {
            this.subscribeToSaveResponse(this.surfSpotService.update(this.surfSpot));
        } else {
            this.subscribeToSaveResponse(this.surfSpotService.create(this.surfSpot));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISurfSpot>>) {
        result.subscribe((res: HttpResponse<ISurfSpot>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get surfSpot() {
        return this._surfSpot;
    }

    set surfSpot(surfSpot: ISurfSpot) {
        this._surfSpot = surfSpot;
    }
}
