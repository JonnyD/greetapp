/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GreetappTestModule } from '../../../test.module';
import { GangUserUpdateComponent } from 'app/entities/gang-user/gang-user-update.component';
import { GangUserService } from 'app/entities/gang-user/gang-user.service';
import { GangUser } from 'app/shared/model/gang-user.model';

describe('Component Tests', () => {
    describe('GangUser Management Update Component', () => {
        let comp: GangUserUpdateComponent;
        let fixture: ComponentFixture<GangUserUpdateComponent>;
        let service: GangUserService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GreetappTestModule],
                declarations: [GangUserUpdateComponent]
            })
                .overrideTemplate(GangUserUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(GangUserUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GangUserService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new GangUser(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.gangUser = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new GangUser();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.gangUser = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
