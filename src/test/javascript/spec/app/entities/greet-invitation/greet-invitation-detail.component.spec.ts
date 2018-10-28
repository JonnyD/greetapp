/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GreetappTestModule } from '../../../test.module';
import { GreetInvitationDetailComponent } from 'app/entities/greet-invitation/greet-invitation-detail.component';
import { GreetInvitation } from 'app/shared/model/greet-invitation.model';

describe('Component Tests', () => {
    describe('GreetInvitation Management Detail Component', () => {
        let comp: GreetInvitationDetailComponent;
        let fixture: ComponentFixture<GreetInvitationDetailComponent>;
        const route = ({ data: of({ greetInvitation: new GreetInvitation(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GreetappTestModule],
                declarations: [GreetInvitationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(GreetInvitationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GreetInvitationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.greetInvitation).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
