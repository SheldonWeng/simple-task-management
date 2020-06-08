import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SimpleTaskManagementTestModule } from '../../../test.module';
import { TaskExecuteRecordComponent } from 'app/entities/task-execute-record/task-execute-record.component';
import { TaskExecuteRecordService } from 'app/entities/task-execute-record/task-execute-record.service';
import { TaskExecuteRecord } from 'app/shared/model/task-execute-record.model';

describe('Component Tests', () => {
  describe('TaskExecuteRecord Management Component', () => {
    let comp: TaskExecuteRecordComponent;
    let fixture: ComponentFixture<TaskExecuteRecordComponent>;
    let service: TaskExecuteRecordService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SimpleTaskManagementTestModule],
        declarations: [TaskExecuteRecordComponent],
      })
        .overrideTemplate(TaskExecuteRecordComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaskExecuteRecordComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TaskExecuteRecordService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TaskExecuteRecord(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.taskExecuteRecords && comp.taskExecuteRecords[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
