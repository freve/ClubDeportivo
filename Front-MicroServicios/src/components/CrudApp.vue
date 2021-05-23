<template>
  <div style="margin: 0 auto; width: 90%">
    <Panel header="CLUB DEPORTIVO" >
      <Menubar :model="items" />
      <br />
      <DataTable :value="clubs" :paginator="true" class="p-datatable-customers" :filters="filters" :rows="10" :selection.sync="selectedClub" selectionMode="single" dataKey="id" >
        <template #header>
        <div class="table-header">
            <span class="p-input-icon-left">
                <i class="pi pi-search"/>
                <InputText v-model="filters['id']" placeholder="Id Search" />
            </span>
        </div>
      </template>
        <Column field="id" header="ID"></Column>        
        <Column field="nombre" header="Nombre"></Column>
        <Column field="ciudad" header="Ciudad"></Column>
        <Column field="direccion" header="Dirección"></Column>
        <Column field="estadio" header="Estadio"></Column>
        <Column field="pais" header="Pais"></Column>
        <Column field="telefono" header="Teléfono"></Column>
      </DataTable>
    </Panel>
    <Dialog header="Crear Club" :visible.sync="displayModal" :style="{width:'40vw'}" :modal="true">
      <span class="p-float-label">
        <InputText id="nombre" type="text" v-model="club.nombre" style="width: 100%" />
        <label for="nombre">Nombre</label>
      </span>
      <br />
      <span class="p-float-label">
        <InputText id="ciudad" type="text" v-model="club.ciudad" style="width: 100%" />
        <label for="nombre">Ciudad</label>
      </span>
      <br />
      <span class="p-float-label">
        <InputText id="direccion" type="text" v-model="club.direccion" style="width: 100%" />
        <label for="apellido">Dirección</label>
      </span>
      <br />
      <span class="p-float-label">
        <InputText id="estadio" type="text" v-model="club.estadio" style="width: 100%" />
        <label for="direccion">Estadio</label>
      </span>
      <br />      
      <span class="p-float-label">
        <InputText id="telefono" type="text" v-model="club.telefono" style="width: 100%" />
        <label for="telefono">Telefono</label>
      </span>
      <br />
      <span class="p-float-label">
        <InputText id="pais" type="text" v-model="club.pais" style="width: 100%" />
        <label for="pais">Pais</label>
      </span>
      <span class="p-float-label">
        Foto
        <label for="foto"></label>
        <InputText id="foto1" type="file" v-model="club.foto" style="width: 100%" />
        
      </span>
      <span class="p-float-label">
        Logo
        <InputText id="escudo" type="file" v-model="club.logo" style="width: 100%" />
        <label for="logo"></label>
      </span>      
      <template #footer>
        <Button label="Guardar" icon="pi pi-check" @click="save" />
        <Button label="Cancelar" icon="pi pi-times" @click="closeModal" class="p-button-secondary" />
      </template>
    </Dialog>
    <Dialog header="Equipos Club" :visible.sync="displayModal2" :style="{width:'70vw'}" :modal="true">
      <Panel header="">
      <Menubar :model="items2" />
      <br />
      <DataTable :value="equipos" :paginator="true" :rows="10" :selection.sync="selectedClub" selectionMode="single" dataKey="id" >
        <Column field="id" header="ID"></Column>        
        <Column field="ciudad" header="Ciudad"></Column>
        <Column field="club" header="Club"></Column>
        <Column field="escudo" header="Escudo"></Column>
        <Column field="nombre" header="Nombre"></Column>
        <Column field="telefono" header="Teléfono"></Column>
        <Column field="categoria.nombre" header="Categoria"></Column>
      </DataTable>
    </Panel>
    </Dialog>

    <Dialog header="Crear Equipo Club" :visible.sync="displayModal3" :style="{width:'50vw'}" :modal="true">
      <span class="p-float-label">
        <InputText id="nombre" type="text" v-model="equipo.nombre" style="width: 100%" />
        <label for="nombre">Nombre</label>
      </span>
      <br />
      <span class="p-float-label">
        <InputText id="ciudad" type="text" v-model="equipo.ciudad" style="width: 100%" />
        <label for="nombre">Ciudad</label>
      </span>
      <br />
      <span class="p-float-label">
        <InputText id="club" type="hidden" v-model="club.id" style="width: 100%" />
      </span>      
      <span class="p-float-label">
        <InputText id="direccion" type="text" v-model="equipo.direccion" style="width: 100%" />
        <label for="direccion">Direccion</label>
      </span>
      <br />            
      <span class="p-float-label">
        <InputText id="telefono" type="text" v-model="equipo.telefono" style="width: 100%" />
        <label for="telefono">Telefono</label>
      </span>
      <template #footer>
        <Button label="Guardar" icon="pi pi-check" @click="saveEquipo" />
        <Button label="Cancelar" icon="pi pi-times" @click="closeModal3" class="p-button-secondary" />
      </template>  
    </Dialog>

  </div>
</template>

<script>
import ClubService from "../service/ClubService";
import EquipoService from "../service/EquipoService";
export default {
  name: "CrudApp",
  data() {
    return {
      clubs: null,
      equipos: null,
      filters: {},
      club: {
        id: null,
        ciudad: null,
        direccion: null,
        estadio: null,
        foto: null,
        nombre: null,
        logo: null,
        pais: null,
        telefono: null
      },
      equipo:{
        id:null,
        ciudad:null,
        club:null,
        direccion:null,
        nombre: null,
        telefono:null
      },
      selectedClub:{
        id: null,
        ciudad: null,
        direccion: null,
        estadio: null,
        foto: null,
        nombre: null,
        logo: null,
        pais: null,
        telefono: null
      },
      items: [
        {
          label: "Nuevo",
          icon: "pi pi-fw pi-plus",
          command: () => {
            this.showSaveModal();
          }
        },
        {
          label: "Editar",
          icon: "pi pi-fw pi-pencil",
          command: () =>{
            this.showEditModal();
          }
        },
        {
          label: "Eliminar",
          icon: "pi pi-fw pi-trash"
        },
        {
          label: "Refrescar",
          icon: "pi pi-fw pi-refresh",
          command: () => {
            this.getAll();
          }
        },
        {
          label: "Ver Equipos",
          icon: "pi pi-fw pi-tablet",
          command: () => {
            this.showEquipoModal();
          }
        }
      ],
      items2: [
        {
          label: "Nuevo",
          icon: "pi pi-fw pi-plus",
          command: () => {
            this.showSaveModalEquipo();
          }
        },
        {
          label: "Editar",
          icon: "pi pi-fw pi-pencil",
          command: () =>{
            this.showEditModalEquipo();
          }
        },
        {
          label: "Eliminar",
          icon: "pi pi-fw pi-trash"
        }
      ],
      displayModal: false,
      displayModal2: false,
      displayModal3: false    
    };
  },
  clubService: null,
  equipoService: null,
  created() {
    this.clubService = new ClubService();
    this.equipoService = new EquipoService();
  },
  mounted() {
      this.getAll();
      // this.clubService.getAll().then(data => {        
      //   this.clubs = data.data;
      //   console.log(this.clubs);
      // });
  },
  methods: {
    showSaveModal() {
      this.displayModal = true;
    },
    showSaveModalEquipo() {
      this.displayModal3 = true;
    },
    showEditModal() {
      this.club = this.selectedClub;
      this.displayModal = true;
    },
    getAll() {
      this.clubService.getAll().then(data => {
        this.clubs = data.data;
      });
    },    
    getAllEquipos() {
      this.club = this.selectedClub;
      this.equipoService.getAll(this.club.id).then(data => {
        this.equipos = data.data;
      });
    },    
    delete(){

    },
    save() {
      this.clubService.save(this.club).then(data => {        
        if (data.status === 200) {
          this.displayModal = false;
          this.club = {
            ciudad: null,
            direccion: null,
            estadio: null,
            foto: null,
            nombre: null,
            logo: null,
            pais: null,
            telefono: null
          };
          this.getAll();
        }
        // console.log(data);
      });
    },
    showEquipoModal(){
      this.club = this.selectedClub;
      this.displayModal2 = true;      
      console.log(this.club);
      this.equipoService.getAll(this.club.id).then(data => {
        this.equipos = data.data;
        console.log(data.data.equipo);
      });
    },
    saveEquipo() {
      this.equipo.club= this.selectedClub.id;
      this.equipoService.save(this.equipo).then(data => {        
        if (data.status === 200) {
          this.displayModal3 = false;
          this.equipo = {
            ciudad: null,
            direccion: null,
            club: null,            
            nombre: null,
            telefono: null
          };
          this.getAllEquipos();
        }
        // console.log(data);
      });
    },
    closeModal() {
      this.displayModal = false;
    },
    closeModal3() {
      this.displayModal3 = false;
    }
  }
}
</script>

<style>
</style>