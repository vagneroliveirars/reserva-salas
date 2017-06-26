import { Injectable } from '@angular/core';

/**
 * Serviço de diálogo de confirmação
 */
@Injectable()
export class DialogService {

    confirm(message?: string) {
        return new Promise(resolve => {
            return resolve(window.confirm(message || 'Confirmar?'));
        });
    }

}