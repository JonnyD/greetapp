/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GreetappTestModule } from '../../../test.module';
import { GreetDetailComponent } from 'app/entities/greet/greet-detail.component';
import { Greet } from 'app/shared/model/greet.model';

describe('Component Tests', () => {
    describe('Greet Management Detail Component', () => {
        let comp: GreetDetailComponent;
        let fixture: ComponentFixture<GreetDetailComponent>;
        const route = ({ data: of({ greet: new Greet(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GreetappTestModule],
                declarations: [GreetDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(GreetDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GreetDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.greet).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
