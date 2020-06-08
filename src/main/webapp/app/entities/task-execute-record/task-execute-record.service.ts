import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITaskExecuteRecord } from 'app/shared/model/task-execute-record.model';

type EntityResponseType = HttpResponse<ITaskExecuteRecord>;
type EntityArrayResponseType = HttpResponse<ITaskExecuteRecord[]>;

@Injectable({ providedIn: 'root' })
export class TaskExecuteRecordService {
  public resourceUrl = SERVER_API_URL + 'api/task-execute-records';

  constructor(protected http: HttpClient) {}

  create(taskExecuteRecord: ITaskExecuteRecord): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taskExecuteRecord);
    return this.http
      .post<ITaskExecuteRecord>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(taskExecuteRecord: ITaskExecuteRecord): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taskExecuteRecord);
    return this.http
      .put<ITaskExecuteRecord>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITaskExecuteRecord>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITaskExecuteRecord[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(taskExecuteRecord: ITaskExecuteRecord): ITaskExecuteRecord {
    const copy: ITaskExecuteRecord = Object.assign({}, taskExecuteRecord, {
      startTime: taskExecuteRecord.startTime && taskExecuteRecord.startTime.isValid() ? taskExecuteRecord.startTime.toJSON() : undefined,
      endTime: taskExecuteRecord.endTime && taskExecuteRecord.endTime.isValid() ? taskExecuteRecord.endTime.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startTime = res.body.startTime ? moment(res.body.startTime) : undefined;
      res.body.endTime = res.body.endTime ? moment(res.body.endTime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((taskExecuteRecord: ITaskExecuteRecord) => {
        taskExecuteRecord.startTime = taskExecuteRecord.startTime ? moment(taskExecuteRecord.startTime) : undefined;
        taskExecuteRecord.endTime = taskExecuteRecord.endTime ? moment(taskExecuteRecord.endTime) : undefined;
      });
    }
    return res;
  }
}
