package com.eazybytes.springsecuritysection6.controller;

import com.eazybytes.springsecuritysection6.model.Notice;
import com.eazybytes.springsecuritysection6.repository.NoticeRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NoticesController {
	
	private final NoticeRepository noticeRepository;

	public NoticesController(NoticeRepository noticeRepository) {
		this.noticeRepository = noticeRepository;
	}

	@GetMapping("/notices")
	public List<Notice> getNotices() {
		return noticeRepository.findAllActiveNotices();
	}

}
