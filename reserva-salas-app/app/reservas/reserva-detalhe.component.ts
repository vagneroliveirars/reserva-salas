import { ReservasModule } from './reservas.module';
import { LocalService } from './../locais/local.service';
import { Sala } from './../salas/sala.model';
import { Local } from './../locais/local.model';
import { Reserva } from './reserva.model';
import { ReservaService } from './reserva.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Location } from '@angular/common';

@Component({
    moduleId: module.id,
    selector: 'reserva-detalhe',
    templateUrl: 'reserva-detalhe.component.html'
})
export class ReservaDetalheComponent implements OnInit {

    reserva: Reserva;
    locais: Local[];
    salas: Sala[];
    private isNew: boolean = true;

    constructor(
        private reservaService: ReservaService,
        private localService: LocalService,
        private route: ActivatedRoute,
        private location: Location
    ) {}
    
    ngOnInit(): void {
        this.reserva = new Reserva(null, null, null, new Date(), new Date(), '', false, null, '');

        this.locais = [];

        this.salas = [];

        this.localService.findAll()
            .then((locais: Local[]) => {
                this.locais = locais;                
            });
      
        // extrai o parâmetro da rota
        this.route.params.forEach((params: Params) => {
            let id: number = +params['id'];

            if (id) {
                this.isNew = false;

                this.reservaService.find(id)
                .then((reserva: Reserva) => {
                    this.reserva = reserva;
                    if (this.reserva && this.reserva.local && this.reserva.local.id) {                        
                        this.findSalasByLocalId(this.reserva.local.id);                        
                    }                                  
                });             
            }                        
        });
             
    }

    getFormGroupClass(isValid: boolean, isPristine: boolean): {} {
        return {
            'form-group': true,
            'has-danger': !isValid && !isPristine,
            'has-success': isValid && !isPristine
        };
    }

    getFormControlClass(isValid: boolean, isPristine: boolean): {} {
        return {
            'form-control': true,
            'form-control-danger': !isValid && !isPristine,
            'form-control-success': isValid && !isPristine
        };
    }

    onSubmit(): void {
        let promise;

        if (this.isNew) {
            console.log('Cadastrar reserva');
            promise = this.reservaService.create(this.reserva);
        } else {
            console.log('Alterar reserva');
            promise = this.reservaService.update(this.reserva);
        }

        promise.then(reserva => this.goBack());
    }

    goBack(): void {
        this.location.back();
    }

    findSalasByLocalId(idLocal: number) {
        console.log("local mudou");
        this.localService.findSalasByLocalId(idLocal)
        .then((salas: Sala[]) => {
            this.salas = salas;
            console.log(this.salas);
        });
    }

    onChangeLocal(local) {
        if (local) {
            this.reserva.sala = null;
            this.findSalasByLocalId(local.id);
        }
    }
    
}