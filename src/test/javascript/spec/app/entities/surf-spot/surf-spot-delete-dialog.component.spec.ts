/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { VindoTestModule } from '../../../test.module';
import { SurfSpotDeleteDialogComponent } from 'app/entities/surf-spot/surf-spot-delete-dialog.component';
import { SurfSpotService } from 'app/entities/surf-spot/surf-spot.service';

describe('Component Tests', () => {
    describe('SurfSpot Management Delete Component', () => {
        let comp: SurfSpotDeleteDialogComponent;
        let fixture: ComponentFixture<SurfSpotDeleteDialogComponent>;
        let service: SurfSpotService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VindoTestModule],
                declarations: [SurfSpotDeleteDialogComponent]
            })
                .overrideTemplate(SurfSpotDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SurfSpotDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SurfSpotService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
