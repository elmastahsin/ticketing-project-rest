package com.company.service;


import com.company.dto.UserDTO;

import javax.ws.rs.core.Response;

public interface KeycloakService {

    Response userCreate(UserDTO dto);

    void delete(String username);


}
