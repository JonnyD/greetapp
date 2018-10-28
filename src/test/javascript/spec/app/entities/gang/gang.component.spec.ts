/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GreetappTestModule } from '../../../test.module';
import { GangComponent } from 'app/entities/gang/gang.component';
import { GangService } from 'app/entities/gang/gang.service';
import { Gang } from 'app/shared/model/gang.model';

describe('Component Tests', () => {
    describe('Gang Management Component', () => {
        let comp: GangComponent;
        let fixture: ComponentFixture<GangComponent>;
        let service: GangService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GreetappTestModule],
                declarations: [GangComponent],
                providers: []
            })
                .overrideTemplate(GangComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(GangComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GangService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Gang(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.gangs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
