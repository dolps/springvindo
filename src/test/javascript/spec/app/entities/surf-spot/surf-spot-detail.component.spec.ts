/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VindoTestModule } from '../../../test.module';
import { SurfSpotDetailComponent } from 'app/entities/surf-spot/surf-spot-detail.component';
import { SurfSpot } from 'app/shared/model/surf-spot.model';

describe('Component Tests', () => {
    describe('SurfSpot Management Detail Component', () => {
        let comp: SurfSpotDetailComponent;
        let fixture: ComponentFixture<SurfSpotDetailComponent>;
        const route = ({ data: of({ surfSpot: new SurfSpot(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VindoTestModule],
                declarations: [SurfSpotDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SurfSpotDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SurfSpotDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.surfSpot).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
