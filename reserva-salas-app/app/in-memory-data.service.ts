import { Reserva } from './reservas/reserva.model';
import { InMemoryDbService} from 'angular-in-memory-web-api';
import { Local } from "./locais/local.model";

export class InMemoryDataService implements InMemoryDbService {

    createDb(): {} {
        let reservas: Reserva[] = [
            {id: 1, 
                local: {id: 1, descricao: 'Local 1', salas: []},
                sala: {id: 1, descricao: 'Sala Azul'},
                dataHoraInicio: new Date(),
                dataHoraFim: new Date(),
                responsavel: 'Fulano de tal',
                cafe: true, 
                quantidadePessoas: 10, 
                descricao: 'Reserva 1'}
        ];

        let carros: Local[] = [
            {id: 1, descricao: 'Porto Alegre', salas: []},
            {id: 2, descricao: 'Rio de Janeiro', salas: []}
        ];

        return {
            'motos': reservas,
            'carros': carros
        };
    }

}