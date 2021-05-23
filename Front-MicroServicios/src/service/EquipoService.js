import axios from 'axios';


export default class ClubService {

    url = "http://localhost:8002/equipo/";

    getAll(id) {
        return axios.get(this.url + "listar/"+id);
    }

    save(equipo) {
        console.log(equipo);
        return axios.post(this.url + "save/"+1, equipo)
    }
    deletee(id){
        return axios.delete(this.url + "eliminar/"+id) 
    }
}