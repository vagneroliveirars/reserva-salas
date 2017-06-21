import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const appRoutes: Routes = [
    {
        path: '',
        redirectTo: '/reserva',
        pathMatch: 'full'
    }
]

@NgModule( {
    imports: [
        RouterModule.forRoot( appRoutes )
    ]
})
export class AppRoutingModule {}