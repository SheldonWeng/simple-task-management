import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITaskExecuteRecord } from 'app/shared/model/task-execute-record.model';
import { TaskExecuteRecordService } from './task-execute-record.service';
import { TaskExecuteRecordDeleteDialogComponent } from './task-execute-record-delete-dialog.component';

@Component({
  selector: 'jhi-task-execute-record',
  templateUrl: './task-execute-record.component.html',
})
export class TaskExecuteRecordComponent implements OnInit, OnDestroy {
  taskExecuteRecords?: ITaskExecuteRecord[];
  eventSubscriber?: Subscription;

  constructor(
    protected taskExecuteRecordService: TaskExecuteRecordService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.taskExecuteRecordService
      .query()
      .subscribe((res: HttpResponse<ITaskExecuteRecord[]>) => (this.taskExecuteRecords = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTaskExecuteRecords();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITaskExecuteRecord): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTaskExecuteRecords(): void {
    this.eventSubscriber = this.eventManager.subscribe('taskExecuteRecordListModification', () => this.loadAll());
  }

  delete(taskExecuteRecord: ITaskExecuteRecord): void {
    const modalRef = this.modalService.open(TaskExecuteRecordDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.taskExecuteRecord = taskExecuteRecord;
  }
}
