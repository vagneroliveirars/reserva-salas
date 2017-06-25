import { Component, OnInit } from '@angular/core';

import { Reserva } from './reserva.model';
import { ReservaService } from './reserva.service';
import { DialogService } from './../dialog.service';

@Component({
  moduleId: module.id,
  selector: 'reservas-lista',
  templateUrl: 'reservas-lista.component.html'
})
export class ReservasListaComponent implements OnInit {

    reservas: Reserva[] = [];
    mensagem: {};
    classesCss: {}; 
    private currentTimeout: any;

    constructor(
        private reservaService: ReservaService,
        private dialogService: DialogService) {}
    
    /**
     * Carrega a lista de reservas
     */
    ngOnInit(): void {
        // Chama o serviço que retorna a lista de reservas
        this.reservaService.findAll()
            .then((reservas: Reserva[]) => {
                this.reservas = reservas;           
            }).catch(err => {
                console.log(err);
                this.mostrarMensagem({
                    tipo: 'danger',
                    texto: 'Ocorreu um erro ao buscar a lista de reservas!'
                });
            });
    }    

    /**
     * Deleta uma reserva
     */
    onDelete(reserva: Reserva): void {
        // Chama o serviço para confirmar a remoção da reserva
        this.dialogService.confirm('Deseja deletar a reserva ' + reserva.descricao + '?')
            .then((canDelete: boolean) => {
                if (canDelete) {
                    // Se confirmar chama o serviço que deleta a reserva
                    this.reservaService
                        .delete(reserva)
                        .then(() => {
                            // Remove a reserva da lista de reservas
                            this.reservas = this.reservas.filter((c : Reserva) => c.id != reserva.id);

                            this.mostrarMensagem({
                                tipo: 'success',
                                texto: 'Reserva deletada!'
                            });                            
                        }).catch(error => {
                            console.log(error);
                            this.mostrarMensagem({
                                tipo: 'danger',
                                texto: 'Ocorreu um erro ao deletar a reserva!'
                            });
                        });
                }
            });
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
        /*
            Exemplo de mensagem de sucesso
            {
                'alert': true;
                'alert-success': true,
                'alert-danger': false,
                ...
            }
        */
        this.classesCss = {
            'alert': true
        };
   
        this.classesCss['alert-' + tipo] = true; // alert-success
    }

} 