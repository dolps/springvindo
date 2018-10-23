/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { VindoTestModule } from '../../../test.module';
import { SurfSpotComponent } from 'app/entities/surf-spot/surf-spot.component';
import { SurfSpotService } from 'app/entities/surf-spot/surf-spot.service';
import { SurfSpot } from 'app/shared/model/surf-spot.model';

describe('Component Tests', () => {
    describe('SurfSpot Management Component', () => {
        let comp: SurfSpotComponent;
        let fixture: ComponentFixture<SurfSpotComponent>;
        let service: SurfSpotService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VindoTestModule],
                declarations: [SurfSpotComponent],
                providers: []
            })
                .overrideTemplate(SurfSpotComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SurfSpotComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SurfSpotService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new SurfSpot(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.surfSpots[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
