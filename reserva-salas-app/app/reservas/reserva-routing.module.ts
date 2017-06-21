import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ReservasListaComponent } from './reservas-lista.component';
import { ReservaDetalheComponent } from './reserva-detalhe.component';

const reservaRoutes: Routes = [
    {
        path: 'reserva',
        component: ReservasListaComponent
    },
    {
        path: 'reserva/save',
        component: ReservaDetalheComponent
    },
    {
        path: 'reserva/save/:id',
        component: ReservaDetalheComponent
    }
];

@NgModule( {
    imports: [
        RouterModule.forChild( reservaRoutes )
    ],
    exports: [
        RouterModule
    ]
})
export class ReservaRoutingModule {}