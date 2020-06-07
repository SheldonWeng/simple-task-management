import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SimpleTaskManagementSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [SimpleTaskManagementSharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent],
})
export class SimpleTaskManagementHomeModule {}
