import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISurfSpot } from 'app/shared/model/surf-spot.model';

@Component({
    selector: 'jhi-surf-spot-detail',
    templateUrl: './surf-spot-detail.component.html'
})
export class SurfSpotDetailComponent implements OnInit {
    surfSpot: ISurfSpot;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ surfSpot }) => {
            this.surfSpot = surfSpot;
        });
    }

    previousState() {
        window.history.back();
    }
}
