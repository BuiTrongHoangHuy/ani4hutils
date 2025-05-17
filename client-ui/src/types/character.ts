import { Actor } from "./actor";

export interface Character {
    id: string;
    name: string;
    role: string;
    image?: {
        url: string;
    };
    actors: Actor[];
}