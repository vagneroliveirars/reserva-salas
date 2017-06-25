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
    mensagem: {};
    classesCss: {}; 
    private currentTimeout: any;

    constructor(
        private reservaService: ReservaService,
        private localService: LocalService,
        private route: ActivatedRoute,
        private location: Location
    ) {}
    
    ngOnInit(): void {
        this.reserva = new Reserva(null, 
            new Local(null, null), new Sala(null, null), null, null, '', false, null, '');

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

        promise.then(() => {
            this.goBack();

            this.mostrarMensagem({
                tipo: 'success',
                texto: 'Reserva Salva!'
            });                                       
        }).catch(error => {
            this.mostrarMensagem({
                tipo: 'danger',
                texto: error._body || error
            });
        });
    }

    goBack(): void {
        this.location.back();
    }

    findSalasByLocalId(idLocal: number) {        
        this.localService.findSalasByLocalId(idLocal)
        .then((salas: Sala[]) => {
            this.salas = salas;            
        });
    }

    onChangeLocal(local) {
        console.log(local);
        
        if (local) {
            this.reserva.sala = new Sala(null, null);            
            this.findSalasByLocalId(local);
        }
    }

    /**
     * Mostra a mensagem e esconde depois de 5 segundos
     */
    private mostrarMensagem(mensagem: {tipo: string, texto: string}): void {
        this.mensagem = mensagem;
        this.montarClasses(mensagem.tipo);
                
        if (this.currentTimeout) {
            clearTimeout(this.currentTimeout);
        }

        this.currentTimeout = setTimeout(() => {
            this.mensagem = undefined;
        }, 5000);
    }

    /**
     * Monta a classe dinamicamente conforme o tipo passado como argumento
     */
    private montarClasses(tipo: string): void {
        this.classesCss = {
            'alert': true
        };
   
        this.classesCss['alert-' + tipo] = true; // alert-success
    }
    
}