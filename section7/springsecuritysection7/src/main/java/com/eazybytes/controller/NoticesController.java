package com.eazybytes.controller;

import com.eazybytes.model.Notice;
import com.eazybytes.repository.NoticeRepository;
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
