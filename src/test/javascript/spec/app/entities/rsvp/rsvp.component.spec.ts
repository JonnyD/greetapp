/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GreetappTestModule } from '../../../test.module';
import { RSVPComponent } from 'app/entities/rsvp/rsvp.component';
import { RSVPService } from 'app/entities/rsvp/rsvp.service';
import { RSVP } from 'app/shared/model/rsvp.model';

describe('Component Tests', () => {
    describe('RSVP Management Component', () => {
        let comp: RSVPComponent;
        let fixture: ComponentFixture<RSVPComponent>;
        let service: RSVPService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GreetappTestModule],
                declarations: [RSVPComponent],
                providers: []
            })
                .overrideTemplate(RSVPComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RSVPComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RSVPService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new RSVP(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.rSVPS[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
