import { Injectable } from '@angular/core';
import { Http, Headers, Response } from '@angular/http';

import { Observable } from 'rxjs';
import 'rxjs/add/operator/toPromise';

import { Reserva } from './reserva.model';
import { RESERVAS } from './reservas-mock';
import { ServiceInterface } from './../interfaces/service.interface';

@Injectable()
export class ReservaService implements ServiceInterface<Reserva> {

    private reservasUrl: string = 'app/reservas';
    private headers: Headers = new Headers({'Content-Type': 'application/json'});

    constructor(private http: Http) {}

    findAll(): Promise<Reserva[]> {
        /*
        / toPromise converte o Observable retornado pelo 
        / http.get (API mockada) em uma Promise
        */ 
        return this.http.get(this.reservasUrl)
            .toPromise()
            .then(response => response.json().data as Reserva[])
            .catch(this.handleError);
    }

    create(reserva: Reserva): Promise<Reserva> {
        return this.http
            .post(this.reservasUrl, JSON.stringify(reserva), {headers: this.headers})
            .toPromise()
            .then((response: Response) => response.json().data as Reserva)            
            .catch(this.handleError);
    }

    update(reserva: Reserva): Promise<Reserva> {
        // utiliza nova anotação do ECMAScript 6
        const url = `${this.reservasUrl}/${reserva.id}`;    // app/reservas/:id

        return this.http
            .put(url, JSON.stringify(reserva), {headers: this.headers})
            .toPromise()
            .then(() => reserva as Reserva)            
            .catch(this.handleError);
    } 

    delete(reserva: Reserva): Promise<Reserva> {
         // utiliza nova anotação do ECMAScript 6
        const url = `${this.reservasUrl}/${reserva.id}`;    // app/reservas/:id

        return this.http
            .delete(url, {headers: this.headers})
            .toPromise()
            .then(() => reserva as Reserva)            
            .catch(this.handleError);
    }

    private handleError(error: any): Promise<any> {
        console.log('Error: ', error);
        return Promise.reject(error.message || error);
    }

    find(id: number): Promise<Reserva> {
        return this.findAll()
            .then((reservas: Reserva[]) => reservas.find(reserva => reserva.id === id));
    }

    getReservasSlowly(): Promise<Reserva[]> {
        return new Promise((resolve, reject) => {
            setTimeout(resolve, 6000);
        }).then(() => this.findAll());
    }

    search(termo: string): Observable<Reserva[]> {
        return this.http
            .get(`${this.reservasUrl}/?nome=${termo}`)
            .map((res: Response) => res.json().data as Reserva[]); // Converte o Response retornado em um json como array de reservas
    }

}