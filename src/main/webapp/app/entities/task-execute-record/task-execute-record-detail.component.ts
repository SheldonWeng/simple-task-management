import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITaskExecuteRecord } from 'app/shared/model/task-execute-record.model';

@Component({
  selector: 'jhi-task-execute-record-detail',
  templateUrl: './task-execute-record-detail.component.html',
})
export class TaskExecuteRecordDetailComponent implements OnInit {
  taskExecuteRecord: ITaskExecuteRecord | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taskExecuteRecord }) => (this.taskExecuteRecord = taskExecuteRecord));
  }

  previousState(): void {
    window.history.back();
  }
}
