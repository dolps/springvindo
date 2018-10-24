import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IMeasurement } from 'app/shared/model/measurement.model';
import { MeasurementService } from './measurement.service';

@Component({
    selector: 'jhi-measurement-update',
    templateUrl: './measurement-update.component.html'
})
export class MeasurementUpdateComponent implements OnInit {
    private _measurement: IMeasurement;
    isSaving: boolean;
    time: string;

    constructor(private measurementService: MeasurementService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ measurement }) => {
            this.measurement = measurement;
        });
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
    get measurement() {
        return this._measurement;
    }

    set measurement(measurement: IMeasurement) {
        this._measurement = measurement;
        this.time = moment(measurement.time).format(DATE_TIME_FORMAT);
    }
}
