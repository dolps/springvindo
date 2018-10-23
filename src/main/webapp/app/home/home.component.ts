import {Component, OnInit} from '@angular/core';
import {NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import {LoginModalService, Principal, Account} from 'app/core';
import {SurfSpotService} from '../entities/surf-spot';
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {ISurfSpot} from "../shared/model/surf-spot.model";

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    surfSpots: ISurfSpot[];
    surfLocations: any;
    lat = 59.664890;
    lng = 10.630013;

    constructor(private principal: Principal,
                private loginModalService: LoginModalService,
                private eventManager: JhiEventManager,
                private jhiAlertService: JhiAlertService,
                private surfSpotService: SurfSpotService) {
    }

    ngOnInit() {
        this.loadSurfSpots();

        this.principal.identity().then(account => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();

        this.surfLocations = [];

        const verket = {lat: 59.612221, lng: 10.413914};
        const haldenBrygge = {lat: 59.888479, lng: 10.634014};

        this.surfLocations[0] = verket;
        this.surfLocations[1] = haldenBrygge;
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', message => {
            this.principal.identity().then(account => {
                this.account = account;
            });
        });
    }

    markerClicked(m: any, i: number) {
        m.icon = 'https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png';
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    loadSurfSpots() {
        this.surfSpotService.query().subscribe(
            (res: HttpResponse<ISurfSpot[]>) => {
                this.surfSpots = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
