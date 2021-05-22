package ufps.microservicios.servicioclub.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ufps.microservicios.servicioclub.dao.ClubDao;
import ufps.microservicios.servicioclub.models.Club;
import ufps.microservicios.servicioclub.services.IUploadService;

@RestController
@CrossOrigin
@RequestMapping("/club")
public class ClubController {

	@Autowired
	ClubDao club;
	
	@Autowired
	private IUploadService upload;
	
	
	@GetMapping("/listar")
	public ResponseEntity<?> list(){
		
		try {
			
			List<Club> lista=(List<Club>) this.club.findAll();
			return new ResponseEntity<List<Club>>(lista,HttpStatus.OK); 
			
		}catch (DataAccessException e) {
			
			Map <String,Object> map=new HashMap<String,Object>();
			map.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String,Object>>(map,HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
	}
	
	@PutMapping("/actualizar/{id}")
	public ResponseEntity<?> update(@PathVariable int id, @RequestBody @Valid Club club, BindingResult result){
		
		Map <String,Object> map=new HashMap<>();
		if (result.hasErrors()) {
			List<FieldError> list=result.getFieldErrors();
			map.put("Mensaje", "Por favor llene todos los campos");
			
			for(FieldError e:list) {
				map.put(e.getField(), e.getDefaultMessage());
			}
			return new ResponseEntity<Map<String,Object>>(map,HttpStatus.BAD_REQUEST);
		}
		
		Club clubUp=this.club.findById(id).orElse(null);
		
		if(clubUp==null) {
			map.put("mensaje", "el cliente con el ID:"+id+"No existe en la bd");
			return new ResponseEntity<Map<String,Object>>(map,HttpStatus.NOT_FOUND);
		}
		
		clubUp.setCiudad(club.getCiudad());
		clubUp.setDireccion(club.getDireccion());
		clubUp.setEstadio(club.getEstadio());
		clubUp.setFoto(club.getFoto());
		clubUp.setLogo(club.getLogo());
		clubUp.setNombre(club.getNombre());
		clubUp.setPais(club.getPais());
		clubUp.setTelefono(club.getTelefono());
		
		
		try {
			Club updated=this.club.save(clubUp);
			map.put("cliente", updated);
		} catch (DataAccessException e) {
			map.put("mensaje", "Ha ocurrido un error");
			map.put("error", e.getMostSpecificCause());
			return new ResponseEntity<Map<String,Object>>(map,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		map.put("mensaje", "Club actualizado correctament");
		
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
		
	}
	
	
	@PostMapping("/guardar")
	public ResponseEntity<?> save( @ModelAttribute @Valid Club club, BindingResult result,@RequestParam(required = false) MultipartFile foto1,@RequestParam(required=false) MultipartFile escudo){
		
		
		Map <String,Object> map=new HashMap<>();
		if (result.hasErrors()) {
			
			
			List<String> list=result.getFieldErrors().stream().map(error -> error.getField()+":"+error.getDefaultMessage()).collect(Collectors.toList());
			
			
			map.put("Mensaje", "Por favor llene todos los campos");
			for(String e:list) {
				String [] valores=e.split(":");
				map.put(valores[0], valores[1]);
			}
	
			
			return new ResponseEntity<Map<String,Object>>(map,HttpStatus.BAD_REQUEST);
		}
		
		try {
			
			String uniqueFile;
			
			try {
				uniqueFile=this.upload.copy(foto1);
				club.setFoto(uniqueFile);
				
				uniqueFile=this.upload.copy(escudo);
				club.setLogo(uniqueFile);
				
			} catch (Exception e) {
				map.put("mensaje","no se pudo subir los archivos multimedia");
				map.put("error", e.getMessage());
				return new ResponseEntity<Map<String,Object>>(map,HttpStatus.INTERNAL_SERVER_ERROR);
				
			}
			
			
			Club club1=this.club.save(club);
			map.put("club", club1);
			map.put("mensaje", "el cliente ha sido agregado con exito");
			
			return new ResponseEntity<Map<String,Object>>(map,HttpStatus.CREATED);
		} catch (DataAccessException e) {
		
			map.put("mensaje","error al realizar el Insert en la bd");
			map.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(map,HttpStatus.INTERNAL_SERVER_ERROR);
			
		}	
	}
	
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> delete(@PathVariable int id) {
		Map <String,Object> mensajes=new HashMap<String,Object>();
		try {
			this.club.deleteById(id);
		} catch (DataAccessException e) {
			mensajes.put("Mensaje", "Error al eliminar el cliente de la bases de datos");
			mensajes.put("error", "No existe el usuario en la bd");
			return new ResponseEntity<Map<String,Object>>(mensajes,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		mensajes.put("Mensaje", "Cliente eliminado de la base de datos");
		return new ResponseEntity<Map<String,Object>>(mensajes,HttpStatus.OK);
		
	}
	
	
	@GetMapping("/listar/{id}")
	public ResponseEntity<?> listById(@PathVariable int id){
		Club club=null;
		Map <String, Object> map=new HashMap<>();
		System.out.println("no encontro el equipo");
		try {
			
			
			club=this.club.findById(id).orElse(null);
			
			
			if(club==null) {
				map.put("mensaje", "El club no existe en la bd");
				return new ResponseEntity<Map<String,Object>>(map,HttpStatus.NOT_FOUND);
			}
			
		}catch ( InternalError | Exception e) {
			map.put("error", e.getMessage());
				 	return new ResponseEntity<Map<String,Object>>(map,HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
		
		return new ResponseEntity<Club>(club,HttpStatus.OK);
	}
	
	
	
}
