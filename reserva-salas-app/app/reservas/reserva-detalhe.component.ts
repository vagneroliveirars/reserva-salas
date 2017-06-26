import { ReservasModule } from './reservas.module';
import { LocalService } from './../locais/local.service';
import { Sala } from './../salas/sala.model';
import { Local } from './../locais/local.model';
import { Reserva } from './reserva.model';
import { ReservaService } from './reserva.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Location } from '@angular/common';

/**
 * Componente de detalhes de reserva
 */
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
    
    /**
     * Carrega os dados na inicialição
     */
    ngOnInit(): void {
        this.reserva = new Reserva(null, 
            new Local(null, null), new Sala(null, null), null, null, '', false, null, '');

        this.locais = [];

        this.salas = [];

        // Busca todos os locais
        this.localService.findAll()
            .then((locais: Local[]) => {
                this.locais = locais;                
            });
      
        // Extrai o parâmetro id da rota
        this.route.params.forEach((params: Params) => {
            let id: number = +params['id'];

            if (id) {
                // Reserva existente
                this.isNew = false;

                // Busca a reserva pelo id
                this.reservaService.find(id)
                .then((reserva: Reserva) => {
                    this.reserva = reserva;
                    if (this.reserva && this.reserva.local && this.reserva.local.id) { 
                        // Busca as salas do local da reserva                       
                        this.findSalasByLocalId(this.reserva.local.id);                        
                    }                                  
                });             
            }                        
        });
             
    }

    /**
     * Retorna uma classe CSS dinamicamente 
     * conforme a validade do formulário
     * 
     * @param isValid 
     * @param isPristine 
     */
    getFormGroupClass(isValid: boolean, isPristine: boolean): {} {
        return {
            'form-group': true,
            'has-danger': !isValid && !isPristine,
            'has-success': isValid && !isPristine
        };
    }

    /**
     * Retorna uma classe CSS dinamicamente para o 
     * componente conforme a validade do formulário
     * 
     * @param isValid 
     * @param isPristine 
     */
    getFormControlClass(isValid: boolean, isPristine: boolean): {} {
        return {
            'form-control': true,
            'form-control-danger': !isValid && !isPristine,
            'form-control-success': isValid && !isPristine
        };
    }

    /**
     * Submete os dados do formulário
     */
    onSubmit(): void {
        let promise;        
        
        if (this.isNew) {
            // Cadastra uma nova reserva            
            promise = this.reservaService.create(this.reserva);
        } else {
            // Altera uma reserva
            promise = this.reservaService.update(this.reserva);
        }

        promise.then(() => {
            // Volta a listagem de reservas
            this.goBack();

            // Mostra mensagem de sucesso
            this.mostrarMensagem({
                tipo: 'success',
                texto: 'Reserva Salva!'
            });                                       
        }).catch(error => {
            // Mostra mensagem de erro
            this.mostrarMensagem({
                tipo: 'danger',
                texto: error._body || error
            });
        });
    }

    /**
     * Volta a página anterior
     */
    goBack(): void {
        this.location.back();
    }

    /**
     * Busca as salas de um determinado local pelo id
     * 
     * @param idLocal 
     */
    findSalasByLocalId(idLocal: number) {        
        this.localService.findSalasByLocalId(idLocal)
        .then((salas: Sala[]) => {
            this.salas = salas;            
        });
    }

    /**
     * Ao alterar o local, limpa a sala da reserva e
     * busca as salas do local selecionado
     * 
     * @param local 
     */
    onChangeLocal(local) {        
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