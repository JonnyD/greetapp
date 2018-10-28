/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GreetappTestModule } from '../../../test.module';
import { GangUpdateComponent } from 'app/entities/gang/gang-update.component';
import { GangService } from 'app/entities/gang/gang.service';
import { Gang } from 'app/shared/model/gang.model';

describe('Component Tests', () => {
    describe('Gang Management Update Component', () => {
        let comp: GangUpdateComponent;
        let fixture: ComponentFixture<GangUpdateComponent>;
        let service: GangService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GreetappTestModule],
                declarations: [GangUpdateComponent]
            })
                .overrideTemplate(GangUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(GangUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GangService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Gang(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.gang = entity;
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
                    const entity = new Gang();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.gang = entity;
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
