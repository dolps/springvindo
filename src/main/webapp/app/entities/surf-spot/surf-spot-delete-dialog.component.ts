import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISurfSpot } from 'app/shared/model/surf-spot.model';
import { SurfSpotService } from './surf-spot.service';

@Component({
    selector: 'jhi-surf-spot-delete-dialog',
    templateUrl: './surf-spot-delete-dialog.component.html'
})
export class SurfSpotDeleteDialogComponent {
    surfSpot: ISurfSpot;

    constructor(private surfSpotService: SurfSpotService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.surfSpotService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'surfSpotListModification',
                content: 'Deleted an surfSpot'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-surf-spot-delete-popup',
    template: ''
})
export class SurfSpotDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ surfSpot }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SurfSpotDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.surfSpot = surfSpot;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
