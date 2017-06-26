import { Injectable } from '@angular/core';
import { Http, Headers, Response } from '@angular/http';

import { Observable } from 'rxjs';
import 'rxjs/add/operator/toPromise';

import { Reserva } from './reserva.model';
import { RESERVAS } from './reservas-mock';
import { ServiceInterface } from './../interfaces/service.interface';

/**
 * Serviço de reservas
 */
@Injectable()
export class ReservaService implements ServiceInterface<Reserva> {

    private reservasUrl: string = 'http://localhost:8080/reserva-salas/reservas';
    private headers: Headers = new Headers({'Content-Type': 'application/json'});

    constructor(private http: Http) {}

    /**
     * Busca todas as reservas
     */
    findAll(): Promise<Reserva[]> {
        /*
        / toPromise converte o Observable retornado pelo 
        / http.get em uma Promise
        */ 
        return this.http.get(this.reservasUrl)
            .toPromise()
            .then(this.extractData)
            .catch(this.handleError);
    }

    /**
     * Busca uma reserva pelo id
     * 
     * @param id 
     */
    find(id: number): Promise<Reserva> {
        const url = `${this.reservasUrl}/${id}`;    // api/reservas/:id

        return this.http.get(url)
            .toPromise()
            .then(this.extractData)
            .catch(this.handleError);      
    }

    /**
     * Cria uma reserva
     * 
     * @param reserva 
     */
    create(reserva: Reserva): Promise<Reserva> {
        return this.http
            .post(this.reservasUrl, JSON.stringify(reserva), {headers: this.headers})
            .toPromise()
            .then(() => reserva as Reserva)
            .catch(this.handleError);
    }

    /**
     * Atualiza uma reserva
     * 
     * @param reserva 
     */
    update(reserva: Reserva): Promise<Reserva> {    
        return this.http
            .put(this.reservasUrl, JSON.stringify(reserva), {headers: this.headers})
            .toPromise()
            .then(() => reserva as Reserva)            
            .catch(this.handleError);
    } 

    /**
     * Deleta uma reserva
     * 
     * @param reserva 
     */
    delete(reserva: Reserva): Promise<Reserva> {
        const url = `${this.reservasUrl}/${reserva.id}`;    // appi/reservas/:id

        return this.http
            .delete(url, {headers: this.headers})
            .toPromise()
            .then(() => reserva as Reserva)
            .catch(this.handleError);
    }        

    /**
     * Extrai os dados retornados pelo serviço
     * 
     * @param response 
     */
    private extractData(response: Response) {
          let body = response.json();           
          return body || {};
    }

    /**
     * Manipula o erro retornado pelo serviço
     * 
     * @param error 
     */
    private handleError(error: any): Promise<any> {
        console.log('Error: ', error);
        return Promise.reject(error.message || error);
    }

}