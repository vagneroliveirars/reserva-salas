import { LocalService } from './../locais/local.service';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { ReservaDetalheComponent } from './reserva-detalhe.component';
import { ReservasListaComponent } from './reservas-lista.component';
import { ReservaRoutingModule } from './reserva-routing.module';
import { ReservaService } from './reserva.service';

@NgModule( {
    imports: [
        CommonModule,
        ReservaRoutingModule,
        FormsModule
    ],
    declarations: [
        ReservaDetalheComponent,
        ReservasListaComponent
    ],
    exports: [
        /* Exporta os componentes para se tornarem vísíveis em outros módulos, 
         * como por exemplo no App Module onde o componente
         * ReservasListaComponent é utilizado
         */                
        ReservasListaComponent
    ],
    providers: [
        ReservaService,
        LocalService
    ]
})

export class ReservasModule {}