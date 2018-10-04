import {Component, OnInit} from '@angular/core';
import {NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager} from 'ng-jhipster';

import {LoginModalService, Principal, Account} from 'app/core';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    surfLocations: any;
    lat = 59.664890;
    lng = 10.630013;

    constructor(private principal: Principal, private loginModalService: LoginModalService, private eventManager: JhiEventManager) {
    }

    ngOnInit() {
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
    mouseOver(m: any, i:number){
        m.icon = "https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png";
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }
}
