import { Collection } from "@discordjs/collection"
import fetch from "node-fetch"
export default class Manager {
    protected readonly endpoint = "https://www.balldontlie.io/api/v1/"
    public cache = new Collection()

    async request(endpoint: string) {
        return (await fetch(endpoint)).json()
    }
}