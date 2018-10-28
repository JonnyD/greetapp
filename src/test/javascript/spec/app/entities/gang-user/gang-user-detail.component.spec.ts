/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GreetappTestModule } from '../../../test.module';
import { GangUserDetailComponent } from 'app/entities/gang-user/gang-user-detail.component';
import { GangUser } from 'app/shared/model/gang-user.model';

describe('Component Tests', () => {
    describe('GangUser Management Detail Component', () => {
        let comp: GangUserDetailComponent;
        let fixture: ComponentFixture<GangUserDetailComponent>;
        const route = ({ data: of({ gangUser: new GangUser(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GreetappTestModule],
                declarations: [GangUserDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(GangUserDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GangUserDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.gangUser).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
