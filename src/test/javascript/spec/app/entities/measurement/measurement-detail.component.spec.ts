/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VindoTestModule } from '../../../test.module';
import { MeasurementDetailComponent } from 'app/entities/measurement/measurement-detail.component';
import { Measurement } from 'app/shared/model/measurement.model';

describe('Component Tests', () => {
    describe('Measurement Management Detail Component', () => {
        let comp: MeasurementDetailComponent;
        let fixture: ComponentFixture<MeasurementDetailComponent>;
        const route = ({ data: of({ measurement: new Measurement(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VindoTestModule],
                declarations: [MeasurementDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MeasurementDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MeasurementDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.measurement).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
