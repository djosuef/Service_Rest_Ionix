package com.ionix.demo.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ionix.demo.entity.User;
import com.ionix.demo.repository.UserRepository;
import com.ionix.demo.utilities.EmailValidator;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Object getUsers() {
        return userRepository.findAll();
    }

    public Object saveUser(User user) {
        //System.out.println("El email tiene un formato correcto: " + EmailValidator.isValidEmail(user.getEmail()));
       if(EmailValidator.isValidEmail(user.getEmail())){
           return userRepository.save(user);
       }else{
           return "El email no tiene un formato correcto";
       }
    }

    public Object getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    @Transactional
    public void deleteById(Long id) throws NotFoundException {
        try{
            if(userRepository.existsById(id)){
                userRepository.deleteById(id);
            }
        }catch(EmptyResultDataAccessException ex){
            throw new NotFoundException();
        }
    }

    public JSONObject findDataUsers(String cadenaCifrada) throws JSONException {

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://my.api.mockaroo.com/test-tecnico/search/" + cadenaCifrada;

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-Key", "f2f719e0");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        long startTime = System.currentTimeMillis();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
       // System.out.println("Tiempo de respuesta: " + elapsedTime + " milisegundos");

        JSONObject data = dataUserAdapter(new JSONObject(response.getBody()), elapsedTime);

        return data;

    }

    private JSONObject dataUserAdapter(JSONObject data, Long elapsedTime) throws JSONException {
        JSONObject dataUser = new JSONObject();
        dataUser.put("responseCode", data.get("responseCode"));
        dataUser.put("description", data.get("description"));
        dataUser.put("elapsedTime", elapsedTime);

        JSONObject results = new JSONObject();

        JSONArray prueba = data.getJSONObject("result").getJSONArray("items");

        System.out.println("PRUEBA: " + prueba.toString());

        int numberItems =  data.getJSONObject("result").getJSONArray("items").length();

        results.put("registerCount:", numberItems);

        dataUser.put("result", results);

        return dataUser;
    }
}
