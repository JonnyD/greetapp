/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GreetappTestModule } from '../../../test.module';
import { RSVPDetailComponent } from 'app/entities/rsvp/rsvp-detail.component';
import { RSVP } from 'app/shared/model/rsvp.model';

describe('Component Tests', () => {
    describe('RSVP Management Detail Component', () => {
        let comp: RSVPDetailComponent;
        let fixture: ComponentFixture<RSVPDetailComponent>;
        const route = ({ data: of({ rSVP: new RSVP(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GreetappTestModule],
                declarations: [RSVPDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RSVPDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RSVPDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.rSVP).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
