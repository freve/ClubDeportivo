package ufps.microservicios.servicioclub.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ufps.microservicios.servicioclub.dao.ClubDao;
import ufps.microservicios.servicioclub.models.Club;

@RestController
@RequestMapping("/club")
public class ClubController {

	@Autowired
	ClubDao club;
	
	
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
	
	@PostMapping("/guardar")
	public ResponseEntity<?> guardar( @RequestBody @Valid Club club, BindingResult result){
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
		
		
		map.put("club", this.club.save(club));
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.BAD_REQUEST);
	}
	
	
	@GetMapping("/listar/{id}")
	public ResponseEntity<?> listById(@PathVariable int id){
		Club club=null;
		Map <String, Object> map=new HashMap<>();
		
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
		
		map.put("club", club);
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
	}
	
	
	
}
