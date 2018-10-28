/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GreetappTestModule } from '../../../test.module';
import { GreetInvitationComponent } from 'app/entities/greet-invitation/greet-invitation.component';
import { GreetInvitationService } from 'app/entities/greet-invitation/greet-invitation.service';
import { GreetInvitation } from 'app/shared/model/greet-invitation.model';

describe('Component Tests', () => {
    describe('GreetInvitation Management Component', () => {
        let comp: GreetInvitationComponent;
        let fixture: ComponentFixture<GreetInvitationComponent>;
        let service: GreetInvitationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GreetappTestModule],
                declarations: [GreetInvitationComponent],
                providers: []
            })
                .overrideTemplate(GreetInvitationComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(GreetInvitationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GreetInvitationService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new GreetInvitation(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.greetInvitations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
