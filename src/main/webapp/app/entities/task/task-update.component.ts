import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITask, Task } from 'app/shared/model/task.model';
import { TaskService } from './task.service';

@Component({
  selector: 'jhi-task-update',
  templateUrl: './task-update.component.html',
})
export class TaskUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    userId: [null, [Validators.required]],
    taskName: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(30)]],
    taskStatus: [null, [Validators.required]],
    description: [],
    createTime: [null, [Validators.required]],
    planStartTime: [],
    planEndTime: [],
  });

  constructor(protected taskService: TaskService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ task }) => {
      if (!task.id) {
        const today = moment().startOf('day');
        task.createTime = today;
        task.planStartTime = today;
        task.planEndTime = today;
      }

      this.updateForm(task);
    });
  }

  updateForm(task: ITask): void {
    this.editForm.patchValue({
      id: task.id,
      userId: task.userId,
      taskName: task.taskName,
      taskStatus: task.taskStatus,
      description: task.description,
      createTime: task.createTime ? task.createTime.format(DATE_TIME_FORMAT) : null,
      planStartTime: task.planStartTime ? task.planStartTime.format(DATE_TIME_FORMAT) : null,
      planEndTime: task.planEndTime ? task.planEndTime.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const task = this.createFromForm();
    if (task.id !== undefined) {
      this.subscribeToSaveResponse(this.taskService.update(task));
    } else {
      this.subscribeToSaveResponse(this.taskService.create(task));
    }
  }

  private createFromForm(): ITask {
    return {
      ...new Task(),
      id: this.editForm.get(['id'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      taskName: this.editForm.get(['taskName'])!.value,
      taskStatus: this.editForm.get(['taskStatus'])!.value,
      description: this.editForm.get(['description'])!.value,
      createTime: this.editForm.get(['createTime'])!.value ? moment(this.editForm.get(['createTime'])!.value, DATE_TIME_FORMAT) : undefined,
      planStartTime: this.editForm.get(['planStartTime'])!.value
        ? moment(this.editForm.get(['planStartTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      planEndTime: this.editForm.get(['planEndTime'])!.value
        ? moment(this.editForm.get(['planEndTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITask>>): void {
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
}
