import Ballkt from "./Main";

const client = new Ballkt()

client.players.fetch().then(r => { 
    console.log(r)
 
    console.log(client.players.cache.get(496))
})
