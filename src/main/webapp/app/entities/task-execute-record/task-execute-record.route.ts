import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITaskExecuteRecord, TaskExecuteRecord } from 'app/shared/model/task-execute-record.model';
import { TaskExecuteRecordService } from './task-execute-record.service';
import { TaskExecuteRecordComponent } from './task-execute-record.component';
import { TaskExecuteRecordDetailComponent } from './task-execute-record-detail.component';
import { TaskExecuteRecordUpdateComponent } from './task-execute-record-update.component';

@Injectable({ providedIn: 'root' })
export class TaskExecuteRecordResolve implements Resolve<ITaskExecuteRecord> {
  constructor(private service: TaskExecuteRecordService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITaskExecuteRecord> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((taskExecuteRecord: HttpResponse<TaskExecuteRecord>) => {
          if (taskExecuteRecord.body) {
            return of(taskExecuteRecord.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TaskExecuteRecord());
  }
}

export const taskExecuteRecordRoute: Routes = [
  {
    path: '',
    component: TaskExecuteRecordComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'simpleTaskManagementApp.taskExecuteRecord.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TaskExecuteRecordDetailComponent,
    resolve: {
      taskExecuteRecord: TaskExecuteRecordResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'simpleTaskManagementApp.taskExecuteRecord.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TaskExecuteRecordUpdateComponent,
    resolve: {
      taskExecuteRecord: TaskExecuteRecordResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'simpleTaskManagementApp.taskExecuteRecord.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TaskExecuteRecordUpdateComponent,
    resolve: {
      taskExecuteRecord: TaskExecuteRecordResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'simpleTaskManagementApp.taskExecuteRecord.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
