/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { VindoTestModule } from '../../../test.module';
import { SurfSpotUpdateComponent } from 'app/entities/surf-spot/surf-spot-update.component';
import { SurfSpotService } from 'app/entities/surf-spot/surf-spot.service';
import { SurfSpot } from 'app/shared/model/surf-spot.model';

describe('Component Tests', () => {
    describe('SurfSpot Management Update Component', () => {
        let comp: SurfSpotUpdateComponent;
        let fixture: ComponentFixture<SurfSpotUpdateComponent>;
        let service: SurfSpotService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VindoTestModule],
                declarations: [SurfSpotUpdateComponent]
            })
                .overrideTemplate(SurfSpotUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SurfSpotUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SurfSpotService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SurfSpot(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.surfSpot = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SurfSpot();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.surfSpot = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
