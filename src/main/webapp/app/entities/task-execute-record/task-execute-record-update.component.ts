import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITaskExecuteRecord, TaskExecuteRecord } from 'app/shared/model/task-execute-record.model';
import { TaskExecuteRecordService } from './task-execute-record.service';
import { ITask } from 'app/shared/model/task.model';
import { TaskService } from 'app/entities/task/task.service';

@Component({
  selector: 'jhi-task-execute-record-update',
  templateUrl: './task-execute-record-update.component.html',
})
export class TaskExecuteRecordUpdateComponent implements OnInit {
  isSaving = false;
  tasks: ITask[] = [];

  editForm = this.fb.group({
    id: [],
    userId: [null, [Validators.required]],
    startTime: [null, [Validators.required]],
    endTime: [],
    taskId: [null, Validators.required],
  });

  constructor(
    protected taskExecuteRecordService: TaskExecuteRecordService,
    protected taskService: TaskService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taskExecuteRecord }) => {
      if (!taskExecuteRecord.id) {
        const today = moment().startOf('day');
        taskExecuteRecord.startTime = today;
        taskExecuteRecord.endTime = today;
      }

      this.updateForm(taskExecuteRecord);

      this.taskService.query().subscribe((res: HttpResponse<ITask[]>) => (this.tasks = res.body || []));
    });
  }

  updateForm(taskExecuteRecord: ITaskExecuteRecord): void {
    this.editForm.patchValue({
      id: taskExecuteRecord.id,
      userId: taskExecuteRecord.userId,
      startTime: taskExecuteRecord.startTime ? taskExecuteRecord.startTime.format(DATE_TIME_FORMAT) : null,
      endTime: taskExecuteRecord.endTime ? taskExecuteRecord.endTime.format(DATE_TIME_FORMAT) : null,
      taskId: taskExecuteRecord.taskId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const taskExecuteRecord = this.createFromForm();
    if (taskExecuteRecord.id !== undefined) {
      this.subscribeToSaveResponse(this.taskExecuteRecordService.update(taskExecuteRecord));
    } else {
      this.subscribeToSaveResponse(this.taskExecuteRecordService.create(taskExecuteRecord));
    }
  }

  private createFromForm(): ITaskExecuteRecord {
    return {
      ...new TaskExecuteRecord(),
      id: this.editForm.get(['id'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      startTime: this.editForm.get(['startTime'])!.value ? moment(this.editForm.get(['startTime'])!.value, DATE_TIME_FORMAT) : undefined,
      endTime: this.editForm.get(['endTime'])!.value ? moment(this.editForm.get(['endTime'])!.value, DATE_TIME_FORMAT) : undefined,
      taskId: this.editForm.get(['taskId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITaskExecuteRecord>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ITask): any {
    return item.id;
  }
}
