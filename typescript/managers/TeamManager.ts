import Manager from "./Manager";
import Team, { APITeam } from "../classes/Team";
import { NoTypes } from "../utils";

/**
 * @class
 */
 export default class TeamManager extends Manager<Team["id"], Team> {
     async fetch<T extends all | id>(type: T, options: T extends id ? number : { page?: number, amount?: number }) {
         if ([0, "all", "every"].includes(type)) {

             const { data } = await this.request("teams");


         }
     }
 }

type all = 0 | "all" | "every"
type id = 1 | "id"