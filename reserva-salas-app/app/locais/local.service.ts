import { Sala } from './../salas/sala.model';
import { Injectable } from '@angular/core';
import { Http, Headers, Response } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { Local } from './local.model';

@Injectable()
export class LocalService {

    private locaisUrl: string = 'http://localhost:8080/reserva-salas/locais';
    private headers: Headers = new Headers({'Content-Type': 'application/json'});

    constructor(private http: Http) {}
    
    findAll(): Promise<Local[]> {        
        return this.http.get(this.locaisUrl)
            .toPromise()
            .then(this.extractData)
            .catch(this.handleError);
    }

    findSalasByLocalId(idLocal: number): Promise<Sala[]> {
        const url = `${this.locaisUrl}/${idLocal}/salas`;    // url/locais/:id/salas

        console.log("URL >>> " + url);

        return this.http.get(url)
            .toPromise()
            .then(this.extractData)
            .catch(this.handleError);      
    }
    
    private handleError(error: any): Promise<any> {
        console.log('Error: ', error);
        return Promise.reject(error.message || error);
    }

    private extractData(response: Response) {
          let body = response.json();
          return body || {};
    }

}