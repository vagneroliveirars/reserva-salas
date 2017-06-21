import { Reserva } from './reservas/reserva.model';
import { InMemoryDbService} from 'angular-in-memory-web-api';

export class InMemoryDataService implements InMemoryDbService {

    createDb(): {} {
        let reservas: Reserva[] = [
            {id: 1, 
                local: {id: 1, descricao: 'Porto Alegre', salas: []},
                sala: {id: 1, descricao: 'Sala Azul'},
                dataHoraInicio: new Date(),
                dataHoraFim: new Date(),
                responsavel: 'Fulano de tal',
                cafe: true, 
                quantidadePessoas: 10, 
                descricao: 'Reserva 1'}
        ];

        let carros: any[] = [
            {id: 1, descricao: 'Camaro'},
            {id: 2, descricao: 'Mustang'}
        ];

        return {
            'reservas': reservas,
            'carros': carros
        };
    }

}