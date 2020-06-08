import { Moment } from 'moment';
import { TaskStatus } from 'app/shared/model/enumerations/task-status.model';

export interface ITask {
  id?: number;
  userId?: number;
  taskName?: string;
  taskStatus?: TaskStatus;
  description?: string;
  createTime?: Moment;
  planStartTime?: Moment;
  planEndTime?: Moment;
}

export class Task implements ITask {
  constructor(
    public id?: number,
    public userId?: number,
    public taskName?: string,
    public taskStatus?: TaskStatus,
    public description?: string,
    public createTime?: Moment,
    public planStartTime?: Moment,
    public planEndTime?: Moment
  ) {}
}
