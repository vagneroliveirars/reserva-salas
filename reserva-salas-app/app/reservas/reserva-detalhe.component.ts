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
    private isNew: boolean = true;

    constructor(
        private reservaService: ReservaService,
        private localService: LocalService,
        private route: ActivatedRoute,
        private location: Location
    ) {}
    
    ngOnInit(): void {
        this.reserva = new Reserva(0, new Local(0, '', []), new Sala(0, ''), new Date(), new Date(), '', false, 0, '');

        this.locais = [];

        this.localService.findAll()
            .then((locais: Local[]) => {
                this.locais = locais;
                console.log(this.locais);
            });
      
        // extrai o parâmetro da rota
        this.route.params.forEach((params: Params) => {
            let id: number = +params['id'];

            if (id) {
                this.isNew = false;

                this.reservaService.find(id)
                .then((reserva: Reserva) => {
                    this.reserva = reserva;

                    console.log(this.reserva.local);
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
    
}