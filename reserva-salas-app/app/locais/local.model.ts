import { Sala } from './../salas/sala.model';
export class Local {
    
    constructor(
            public id: number,                        
            public descricao: string,
            public salas: Sala[]
    ) {}
    
}