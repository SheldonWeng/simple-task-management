import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'task',
        loadChildren: () => import('./task/task.module').then(m => m.SimpleTaskManagementTaskModule),
      },
      {
        path: 'task-execute-record',
        loadChildren: () =>
          import('./task-execute-record/task-execute-record.module').then(m => m.SimpleTaskManagementTaskExecuteRecordModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class SimpleTaskManagementEntityModule {}
