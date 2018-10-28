/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GreetappTestModule } from '../../../test.module';
import { GangUserComponent } from 'app/entities/gang-user/gang-user.component';
import { GangUserService } from 'app/entities/gang-user/gang-user.service';
import { GangUser } from 'app/shared/model/gang-user.model';

describe('Component Tests', () => {
    describe('GangUser Management Component', () => {
        let comp: GangUserComponent;
        let fixture: ComponentFixture<GangUserComponent>;
        let service: GangUserService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GreetappTestModule],
                declarations: [GangUserComponent],
                providers: []
            })
                .overrideTemplate(GangUserComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(GangUserComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GangUserService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new GangUser(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.gangUsers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
