package com.eazybytes.springsecuritysection6.controller;

import java.util.List;

import com.eazybytes.springsecuritysection6.model.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eazybytes.springsecuritysection6.repository.NoticeRepository;

@RestController
public class NoticesController {
	
	@Autowired
	private NoticeRepository noticeRepository;
	
	@GetMapping("/notices")
	public List<Notice> getNotices() {
		return noticeRepository.findAllActiveNotices();
	}

}
