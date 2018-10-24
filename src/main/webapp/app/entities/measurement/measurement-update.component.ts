import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IMeasurement } from 'app/shared/model/measurement.model';
import { MeasurementService } from './measurement.service';
import { ISurfSpot } from 'app/shared/model/surf-spot.model';
import { SurfSpotService } from 'app/entities/surf-spot';

@Component({
    selector: 'jhi-measurement-update',
    templateUrl: './measurement-update.component.html'
})
export class MeasurementUpdateComponent implements OnInit {
    private _measurement: IMeasurement;
    isSaving: boolean;

    surfspots: ISurfSpot[];
    time: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private measurementService: MeasurementService,
        private surfSpotService: SurfSpotService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ measurement }) => {
            this.measurement = measurement;
        });
        this.surfSpotService.query().subscribe(
            (res: HttpResponse<ISurfSpot[]>) => {
                this.surfspots = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.measurement.time = moment(this.time, DATE_TIME_FORMAT);
        if (this.measurement.id !== undefined) {
            this.subscribeToSaveResponse(this.measurementService.update(this.measurement));
        } else {
            this.subscribeToSaveResponse(this.measurementService.create(this.measurement));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IMeasurement>>) {
        result.subscribe((res: HttpResponse<IMeasurement>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackSurfSpotById(index: number, item: ISurfSpot) {
        return item.id;
    }
    get measurement() {
        return this._measurement;
    }

    set measurement(measurement: IMeasurement) {
        this._measurement = measurement;
        this.time = moment(measurement.time).format(DATE_TIME_FORMAT);
    }
}
