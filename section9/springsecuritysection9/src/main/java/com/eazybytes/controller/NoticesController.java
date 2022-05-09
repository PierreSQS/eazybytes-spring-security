package com.eazybytes.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eazybytes.model.Notice;
import com.eazybytes.repository.NoticeRepository;

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
