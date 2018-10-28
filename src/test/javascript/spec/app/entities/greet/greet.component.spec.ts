/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GreetappTestModule } from '../../../test.module';
import { GreetComponent } from 'app/entities/greet/greet.component';
import { GreetService } from 'app/entities/greet/greet.service';
import { Greet } from 'app/shared/model/greet.model';

describe('Component Tests', () => {
    describe('Greet Management Component', () => {
        let comp: GreetComponent;
        let fixture: ComponentFixture<GreetComponent>;
        let service: GreetService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GreetappTestModule],
                declarations: [GreetComponent],
                providers: []
            })
                .overrideTemplate(GreetComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(GreetComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GreetService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Greet(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.greets[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
