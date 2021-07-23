interface Base {
    id: number;
    abbreviation: string;
    city: string;
    conference: string;
    division: string;
    name: string;
}

export interface APITeam extends Base {
    full_name: string;
}

interface Team extends Base {
    fullName: string;
}

class Team {
    constructor({ id, abbreviation, city, conference, division, full_name, name }: APITeam) {
        this.id = id;
        this.abbreviation = abbreviation;
        this.city = city;
        this.conference = conference;
        this.division = division;
        this.fullName = full_name;
        this.name = name;
    }
}

export default Team;