import { Sala } from './../salas/sala.model';
import { Injectable } from '@angular/core';
import { Http, Headers, Response } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { Local } from './local.model';

/**
 * Serviço de locais
 */
@Injectable()
export class LocalService {

    private locaisUrl: string = 'http://localhost:8080/reserva-salas/locais';
    private headers: Headers = new Headers({'Content-Type': 'application/json'});

    constructor(private http: Http) {}
    
    /**
     * Busca todos os locais
     */
    findAll(): Promise<Local[]> {        
        return this.http.get(this.locaisUrl)
            .toPromise()
            .then(this.extractData)
            .catch(this.handleError);
    }

    /**
     * Busca as salas de um determinado local
     * 
     * @param idLocal 
     */
    findSalasByLocalId(idLocal: number): Promise<Sala[]> {
        const url = `${this.locaisUrl}/${idLocal}/salas`;    // url/locais/:id/salas       

        return this.http.get(url)
            .toPromise()
            .then(this.extractData)
            .catch(this.handleError);      
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

    /**
     * Extrai os dados retornados pelo serviço
     * 
     * @param response 
     */
    private extractData(response: Response) {
          let body = response.json();
          return body || {};
    }

}