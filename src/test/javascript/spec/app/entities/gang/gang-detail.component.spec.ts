/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GreetappTestModule } from '../../../test.module';
import { GangDetailComponent } from 'app/entities/gang/gang-detail.component';
import { Gang } from 'app/shared/model/gang.model';

describe('Component Tests', () => {
    describe('Gang Management Detail Component', () => {
        let comp: GangDetailComponent;
        let fixture: ComponentFixture<GangDetailComponent>;
        const route = ({ data: of({ gang: new Gang(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GreetappTestModule],
                declarations: [GangDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(GangDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GangDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.gang).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
