import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SimpleTaskManagementTestModule } from '../../../test.module';
import { TaskExecuteRecordUpdateComponent } from 'app/entities/task-execute-record/task-execute-record-update.component';
import { TaskExecuteRecordService } from 'app/entities/task-execute-record/task-execute-record.service';
import { TaskExecuteRecord } from 'app/shared/model/task-execute-record.model';

describe('Component Tests', () => {
  describe('TaskExecuteRecord Management Update Component', () => {
    let comp: TaskExecuteRecordUpdateComponent;
    let fixture: ComponentFixture<TaskExecuteRecordUpdateComponent>;
    let service: TaskExecuteRecordService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SimpleTaskManagementTestModule],
        declarations: [TaskExecuteRecordUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TaskExecuteRecordUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaskExecuteRecordUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TaskExecuteRecordService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TaskExecuteRecord(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new TaskExecuteRecord();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
