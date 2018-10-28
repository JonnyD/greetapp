/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GreetappTestModule } from '../../../test.module';
import { FriendshipDetailComponent } from 'app/entities/friendship/friendship-detail.component';
import { Friendship } from 'app/shared/model/friendship.model';

describe('Component Tests', () => {
    describe('Friendship Management Detail Component', () => {
        let comp: FriendshipDetailComponent;
        let fixture: ComponentFixture<FriendshipDetailComponent>;
        const route = ({ data: of({ friendship: new Friendship(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GreetappTestModule],
                declarations: [FriendshipDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(FriendshipDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FriendshipDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.friendship).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
