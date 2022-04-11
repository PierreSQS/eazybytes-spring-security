package com.eazybytes.springsecuritysection6.repository;

import com.eazybytes.springsecuritysection6.model.Notice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends CrudRepository<Notice, Long> {
	
	List<Notice> findAll();

}
