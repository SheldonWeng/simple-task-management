import { Moment } from 'moment';

export interface ITaskExecuteRecord {
  id?: number;
  userId?: number;
  startTime?: Moment;
  endTime?: Moment;
  taskId?: number;
}

export class TaskExecuteRecord implements ITaskExecuteRecord {
  constructor(public id?: number, public userId?: number, public startTime?: Moment, public endTime?: Moment, public taskId?: number) {}
}
