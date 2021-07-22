import { Collection } from "@discordjs/collection"

export default class Manager {
    protected readonly endpoint = "https://www.balldontlie.io/api/v1/"
    public cache = new Collection()
}