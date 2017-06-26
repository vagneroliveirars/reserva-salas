import { Sala } from './../salas/sala.model';
import { Local } from './../locais/local.model';

/**
 * Modelo que representa uma Reserva
 */
export class Reserva {
    
    constructor(
            public id: number,         
            public local: Local,
            public sala: Sala,
            public dataHoraInicio: Date,
            public dataHoraFim: Date,
            public responsavel: string,
            public cafe: boolean,
            public quantidadePessoas: number,
            public descricao: string
    ) {}
    
}