import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SimpleTaskManagementSharedModule } from 'app/shared/shared.module';
import { TaskExecuteRecordComponent } from './task-execute-record.component';
import { TaskExecuteRecordDetailComponent } from './task-execute-record-detail.component';
import { TaskExecuteRecordUpdateComponent } from './task-execute-record-update.component';
import { TaskExecuteRecordDeleteDialogComponent } from './task-execute-record-delete-dialog.component';
import { taskExecuteRecordRoute } from './task-execute-record.route';

@NgModule({
  imports: [SimpleTaskManagementSharedModule, RouterModule.forChild(taskExecuteRecordRoute)],
  declarations: [
    TaskExecuteRecordComponent,
    TaskExecuteRecordDetailComponent,
    TaskExecuteRecordUpdateComponent,
    TaskExecuteRecordDeleteDialogComponent,
  ],
  entryComponents: [TaskExecuteRecordDeleteDialogComponent],
})
export class SimpleTaskManagementTaskExecuteRecordModule {}
