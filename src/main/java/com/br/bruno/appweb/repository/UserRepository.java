package com.br.bruno.appweb.repository;

import com.br.bruno.appweb.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}