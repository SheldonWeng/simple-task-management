import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SimpleTaskManagementTestModule } from '../../../test.module';
import { TaskExecuteRecordDetailComponent } from 'app/entities/task-execute-record/task-execute-record-detail.component';
import { TaskExecuteRecord } from 'app/shared/model/task-execute-record.model';

describe('Component Tests', () => {
  describe('TaskExecuteRecord Management Detail Component', () => {
    let comp: TaskExecuteRecordDetailComponent;
    let fixture: ComponentFixture<TaskExecuteRecordDetailComponent>;
    const route = ({ data: of({ taskExecuteRecord: new TaskExecuteRecord(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SimpleTaskManagementTestModule],
        declarations: [TaskExecuteRecordDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TaskExecuteRecordDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaskExecuteRecordDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load taskExecuteRecord on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.taskExecuteRecord).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
