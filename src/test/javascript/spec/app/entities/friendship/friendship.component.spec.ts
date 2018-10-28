/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GreetappTestModule } from '../../../test.module';
import { FriendshipComponent } from 'app/entities/friendship/friendship.component';
import { FriendshipService } from 'app/entities/friendship/friendship.service';
import { Friendship } from 'app/shared/model/friendship.model';

describe('Component Tests', () => {
    describe('Friendship Management Component', () => {
        let comp: FriendshipComponent;
        let fixture: ComponentFixture<FriendshipComponent>;
        let service: FriendshipService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GreetappTestModule],
                declarations: [FriendshipComponent],
                providers: []
            })
                .overrideTemplate(FriendshipComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FriendshipComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FriendshipService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Friendship(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.friendships[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
