import { Reserva } from './reserva.model';

export const RESERVAS: Reserva[] = [
    {id: 1, 
        local: {id: 1, descricao: 'Sao Paulo', salas: []},
        sala: {id: 1, descricao: 'Sala Azul'},
        dataHoraInicio: new Date(),
        dataHoraFim: new Date(),
        responsavel: 'Fulano de tal',
        cafe: true, 
        quantidadePessoas: 10, 
        descricao: 'Reserva 1'}   
];