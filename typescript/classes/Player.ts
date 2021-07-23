import Team, { APITeam } from "./Team";

interface Player {
    id: number;
    firstName: string;
    lastName: string;
    name: string;
    position: string;
    feet: number;
    inches: number;
    pounds: number;
    team: Team;
}

export interface APIPlayer {
    id: number;
    first_name: string;
    last_name: string;
    position: string;
    height_feet: number;
    height_inches: number;
    weight_pounds: number;
    team: APITeam;
}

class Player {
    constructor({ id, first_name, last_name, position, height_feet, height_inches, weight_pounds, team }: APIPlayer) {

        this.id = id;
        this.firstName = first_name;
        this.lastName = last_name;
        this.name = first_name + " " + last_name
        this.position = position;
        this.feet = height_feet;
        this.inches = height_inches;
        this.pounds = weight_pounds;
        this.team = new Team(team)
    }
}

export default Player