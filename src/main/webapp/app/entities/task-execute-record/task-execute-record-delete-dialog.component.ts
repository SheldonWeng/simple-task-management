import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITaskExecuteRecord } from 'app/shared/model/task-execute-record.model';
import { TaskExecuteRecordService } from './task-execute-record.service';

@Component({
  templateUrl: './task-execute-record-delete-dialog.component.html',
})
export class TaskExecuteRecordDeleteDialogComponent {
  taskExecuteRecord?: ITaskExecuteRecord;

  constructor(
    protected taskExecuteRecordService: TaskExecuteRecordService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.taskExecuteRecordService.delete(id).subscribe(() => {
      this.eventManager.broadcast('taskExecuteRecordListModification');
      this.activeModal.close();
    });
  }
}
