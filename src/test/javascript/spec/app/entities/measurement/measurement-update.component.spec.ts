/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { VindoTestModule } from '../../../test.module';
import { MeasurementUpdateComponent } from 'app/entities/measurement/measurement-update.component';
import { MeasurementService } from 'app/entities/measurement/measurement.service';
import { Measurement } from 'app/shared/model/measurement.model';

describe('Component Tests', () => {
    describe('Measurement Management Update Component', () => {
        let comp: MeasurementUpdateComponent;
        let fixture: ComponentFixture<MeasurementUpdateComponent>;
        let service: MeasurementService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VindoTestModule],
                declarations: [MeasurementUpdateComponent]
            })
                .overrideTemplate(MeasurementUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MeasurementUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MeasurementService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Measurement(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.measurement = entity;
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
                    const entity = new Measurement();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.measurement = entity;
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
