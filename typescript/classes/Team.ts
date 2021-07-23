export interface APITeam {
    id: number;
    abbreviation: string;
    city: string;
    conference: string;
    division: string;
    full_name: string;
    name: string;
}

interface Team {
    id: number;
    abbreviation: string;
    city: string;
    conference: string;
    division: string;
    fullName: string;
    name: string;
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