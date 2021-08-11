import Manager from "./Manager";
import Team, { APITeam } from "../classes/Team";
import { numbers as range1_100, NoTypes } from "../utils";

/**
 * @class
 */
 export default class TeamManager extends Manager<Team["id"], Team> {
     async fetch() {
         
     }
 }